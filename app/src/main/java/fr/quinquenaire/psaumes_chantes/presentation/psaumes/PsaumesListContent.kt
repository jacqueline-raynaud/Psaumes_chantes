package fr.quinquenaire.psaumes_chantes.presentation.psaumes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.quinquenaire.psaumes_chantes.R
import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import fr.quinquenaire.psaumes_chantes.presentation.psaumes.components.AnnotationDialog
import fr.quinquenaire.psaumes_chantes.presentation.psaumes.components.PlayerBar
import fr.quinquenaire.psaumes_chantes.presentation.psaumes.components.PsaumeItem
import fr.quinquenaire.psaumes_chantes.ui.theme.PsaumesChantesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsaumesListContent(
    state: PsaumesListState,
    onIntent: (PsaumesListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val psaumeCourant = state.psaumeCourant

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.liste_titre)) })
        },
        bottomBar = {
            if (psaumeCourant != null) {
                PlayerBar(
                    psaume = psaumeCourant,
                    enLecture = state.enLecture,
                    enChargement = state.enChargementAudio,
                    positionMs = state.positionMs,
                    dureeMs = state.dureeMs,
                    onBasculerLecturePause = { onIntent(PsaumesListIntent.BasculerLecturePause) },
                    onArreter = { onIntent(PsaumesListIntent.Arreter) },
                    onReculer = { onIntent(PsaumesListIntent.Reculer) },
                    onAvancer = { onIntent(PsaumesListIntent.Avancer) },
                )
            }
        },
        floatingActionButton = {
            if (psaumeCourant != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (psaumeCourant.annotation != null) {
                        SmallFloatingActionButton(
                            onClick = { onIntent(PsaumesListIntent.SupprimerAnnotation) },
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.annotation_supprimer))
                        }
                        Box(modifier = Modifier.padding(top = 12.dp))
                    }
                    FloatingActionButton(
                        onClick = { onIntent(PsaumesListIntent.OuvrirBoiteAnnotation) },
                    ) {
                        Icon(
                            imageVector = if (psaumeCourant.annotation != null) Icons.Filled.Edit else Icons.Filled.Add,
                            contentDescription = stringResource(
                                if (psaumeCourant.annotation != null) R.string.annotation_modifier else R.string.annotation_ajouter,
                            ),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        when {
            state.enChargement -> EtatChargement(modifier = Modifier.padding(innerPadding))
            state.erreur != null -> EtatErreur(
                message = state.erreur,
                onReessayer = { onIntent(PsaumesListIntent.Rafraichir) },
                modifier = Modifier.padding(innerPadding),
            )
            state.psaumes.isEmpty() -> EtatVide(modifier = Modifier.padding(innerPadding))
            else -> ListePsaumes(
                psaumes = state.psaumes,
                fileNameEnCours = psaumeCourant?.fileName,
                onPsaumeClick = { onIntent(PsaumesListIntent.SelectionnerPsaume(it)) },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }

    if (state.boiteAnnotationVisible && psaumeCourant != null) {
        AnnotationDialog(
            titrePsaume = psaumeCourant.titre,
            annotationExistante = psaumeCourant.annotation,
            onValider = { onIntent(PsaumesListIntent.ValiderAnnotation(it)) },
            onFermer = { onIntent(PsaumesListIntent.FermerBoiteAnnotation) },
        )
    }
}

@Composable
private fun ListePsaumes(
    psaumes: List<Psaume>,
    fileNameEnCours: String?,
    onPsaumeClick: (Psaume) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(psaumes, key = { it.fileName }) { psaume ->
            PsaumeItem(
                psaume = psaume,
                enCours = psaume.fileName == fileNameEnCours,
                onClick = { onPsaumeClick(psaume) },
            )
        }
    }
}

@Composable
private fun EtatChargement(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(
                text = stringResource(R.string.chargement),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

@Composable
private fun EtatErreur(message: String, onReessayer: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message.ifBlank { stringResource(R.string.erreur_chargement_generique) },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
            TextButton(onClick = onReessayer, modifier = Modifier.padding(top = 8.dp)) {
                Text(stringResource(R.string.reessayer))
            }
        }
    }
}

@Composable
private fun EtatVide(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.liste_vide), style = MaterialTheme.typography.bodyMedium)
    }
}

private val psaumesDeDemonstration = listOf(
    Psaume(fileName = "psaume_23.mp3", titre = "Psaume 23", url = "", annotation = "Pour la veillée du dimanche"),
    Psaume(fileName = "psaume_50.mp3", titre = "Psaume 50", url = ""),
    Psaume(fileName = "psaume_150.mp3", titre = "Psaume 150", url = ""),
)

@Preview(showBackground = true, name = "Liste - chargement")
@Composable
private fun PsaumesListContentChargementPreview() {
    PsaumesChantesTheme {
        PsaumesListContent(state = PsaumesListState(enChargement = true), onIntent = {})
    }
}

@Preview(showBackground = true, name = "Liste - erreur")
@Composable
private fun PsaumesListContentErreurPreview() {
    PsaumesChantesTheme {
        PsaumesListContent(
            state = PsaumesListState(enChargement = false, erreur = "Connexion impossible"),
            onIntent = {},
        )
    }
}

@Preview(showBackground = true, name = "Liste - lecture en cours")
@Composable
private fun PsaumesListContentLecturePreview() {
    PsaumesChantesTheme {
        PsaumesListContent(
            state = PsaumesListState(
                enChargement = false,
                psaumes = psaumesDeDemonstration,
                psaumeCourant = psaumesDeDemonstration.first(),
                enLecture = true,
                positionMs = 45_000L,
                dureeMs = 180_000L,
            ),
            onIntent = {},
        )
    }
}

@Preview(showBackground = true, name = "Liste - boîte annotation")
@Composable
private fun PsaumesListContentAnnotationPreview() {
    PsaumesChantesTheme {
        PsaumesListContent(
            state = PsaumesListState(
                enChargement = false,
                psaumes = psaumesDeDemonstration,
                psaumeCourant = psaumesDeDemonstration.first(),
                boiteAnnotationVisible = true,
            ),
            onIntent = {},
        )
    }
}
