package fr.quinquenaire.psaumes_chantes.presentation.psaumes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.quinquenaire.psaumes_chantes.data.player.PlayerManager
import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import fr.quinquenaire.psaumes_chantes.domain.usecase.EnregistrerAnnotationUseCase
import fr.quinquenaire.psaumes_chantes.domain.usecase.ObserverPsaumesUseCase
import fr.quinquenaire.psaumes_chantes.domain.usecase.RafraichirPsaumesUseCase
import fr.quinquenaire.psaumes_chantes.domain.usecase.SupprimerAnnotationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsaumesListViewModel @Inject constructor(
    private val observerPsaumes: ObserverPsaumesUseCase,
    private val rafraichirPsaumes: RafraichirPsaumesUseCase,
    private val enregistrerAnnotation: EnregistrerAnnotationUseCase,
    private val supprimerAnnotation: SupprimerAnnotationUseCase,
    private val playerManager: PlayerManager,
) : ViewModel() {

    private data class EtatUiComplementaire(
        val enChargement: Boolean = true,
        val erreur: String? = null,
        val fileNameSelectionne: String? = null,
        val boiteAnnotationVisible: Boolean = false,
    )

    private val etatComplementaire = MutableStateFlow(EtatUiComplementaire())

    val state = combine(
        observerPsaumes(),
        playerManager.etat,
        etatComplementaire,
    ) { psaumes, etatLecture, extra ->
        val psaumeCourant = psaumes.find { it.fileName == extra.fileNameSelectionne }
        val estLePsaumeEnCours = psaumeCourant != null && etatLecture.fileNameEnCours == psaumeCourant.fileName

        PsaumesListState(
            enChargement = extra.enChargement,
            erreur = extra.erreur,
            psaumes = psaumes,
            psaumeCourant = psaumeCourant,
            enLecture = estLePsaumeEnCours && etatLecture.enLecture,
            enChargementAudio = estLePsaumeEnCours && etatLecture.enChargement,
            positionMs = if (estLePsaumeEnCours) etatLecture.positionMs else 0L,
            dureeMs = if (estLePsaumeEnCours) etatLecture.dureeMs else 0L,
            boiteAnnotationVisible = extra.boiteAnnotationVisible,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PsaumesListState(),
    )

    init {
        chargerPsaumes()
    }

    fun onIntent(intent: PsaumesListIntent) {
        when (intent) {
            PsaumesListIntent.Rafraichir -> chargerPsaumes()
            is PsaumesListIntent.SelectionnerPsaume -> selectionnerPsaume(intent.psaume)
            PsaumesListIntent.BasculerLecturePause -> playerManager.basculerLecturePause()
            PsaumesListIntent.Rejouer ->playerManager.rejouer()
            PsaumesListIntent.Arreter -> playerManager.arreter()
            PsaumesListIntent.Reculer -> playerManager.reculer()
            PsaumesListIntent.Avancer -> playerManager.avancer()
            PsaumesListIntent.OuvrirBoiteAnnotation -> etatComplementaire.update { it.copy(boiteAnnotationVisible = true) }
            PsaumesListIntent.FermerBoiteAnnotation -> etatComplementaire.update { it.copy(boiteAnnotationVisible = false) }
            is PsaumesListIntent.ValiderAnnotation -> validerAnnotation(intent.texte)
            PsaumesListIntent.SupprimerAnnotation -> supprimerAnnotationCourante()
        }
    }

    private fun chargerPsaumes() {
        etatComplementaire.update { it.copy(enChargement = true, erreur = null) }
        viewModelScope.launch {
            val resultat = rafraichirPsaumes()
            etatComplementaire.update {
                it.copy(
                    enChargement = false,
                    erreur = resultat.exceptionOrNull()?.message,
                )
            }
        }
    }

    private fun selectionnerPsaume(psaume: Psaume) {
        etatComplementaire.update { it.copy(fileNameSelectionne = psaume.fileName) }
        playerManager.jouer(psaume.fileName, psaume.url)
    }

    private fun validerAnnotation(texte: String) {
        val fileName = state.value.psaumeCourant?.fileName ?: return
        val texteNettoye = texte.trim()
        if (texteNettoye.isEmpty()) return
        viewModelScope.launch {
            enregistrerAnnotation(fileName, texteNettoye)
        }
        etatComplementaire.update { it.copy(boiteAnnotationVisible = false) }
    }

    private fun supprimerAnnotationCourante() {
        val fileName = state.value.psaumeCourant?.fileName ?: return
        viewModelScope.launch {
            supprimerAnnotation(fileName)
        }
    }
}
