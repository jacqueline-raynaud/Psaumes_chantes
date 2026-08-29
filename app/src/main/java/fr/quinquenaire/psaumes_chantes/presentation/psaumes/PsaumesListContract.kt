package fr.quinquenaire.psaumes_chantes.presentation.psaumes

import fr.quinquenaire.psaumes_chantes.domain.model.Psaume

data class PsaumesListState(
    val enChargement: Boolean = true,
    val erreur: String? = null,
    val psaumes: List<Psaume> = emptyList(),
    val psaumeCourant: Psaume? = null,
    val enLecture: Boolean = false,
    val enChargementAudio: Boolean = false,
    val positionMs: Long = 0L,
    val dureeMs: Long = 0L,
    val boiteAnnotationVisible: Boolean = false,
)

sealed interface PsaumesListIntent {
    data object Rafraichir : PsaumesListIntent
    data class SelectionnerPsaume(val psaume: Psaume) : PsaumesListIntent
    data object BasculerLecturePause : PsaumesListIntent
    data object Arreter : PsaumesListIntent
    data object Reculer : PsaumesListIntent
    data object Avancer : PsaumesListIntent
    data object OuvrirBoiteAnnotation : PsaumesListIntent
    data object FermerBoiteAnnotation : PsaumesListIntent
    data class ValiderAnnotation(val texte: String) : PsaumesListIntent
    data object SupprimerAnnotation : PsaumesListIntent
}
