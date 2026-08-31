package fr.quinquenaire.psaumes_chantes.presentation.psaumes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.quinquenaire.psaumes_chantes.R
import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import fr.quinquenaire.psaumes_chantes.presentation.psaumes.formaterDuree
import fr.quinquenaire.psaumes_chantes.ui.theme.PsaumesChantesTheme

@Composable
fun PlayerBar(
    psaume: Psaume,
    enLecture: Boolean,
    enChargement: Boolean,
    positionMs: Long,
    dureeMs: Long,
    onBasculerLecturePause: () -> Unit,
    onArreter: () -> Unit,
    onRejouer : () -> Unit,
    onReculer: () -> Unit,
    onAvancer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = psaume.titre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            if (psaume.annotation != null) {
                Text(
                    text = psaume.annotation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 4.dp),
                )
            } else {
                Text(
                    text = stringResource(R.string.annotation_aucune),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            if (dureeMs > 0L) {
                LinearProgressIndicator(
                    progress = { (positionMs.toFloat() / dureeMs.toFloat()).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = formaterDuree(positionMs), style = MaterialTheme.typography.labelSmall)
                    Text(text = formaterDuree(dureeMs), style = MaterialTheme.typography.labelSmall)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onRejouer) {
                    Icon(
                        imageVector = Icons.Filled.Replay,
                        contentDescription = stringResource(R.string.action_rejouer),
                    )
                }

                IconButton(onClick = onReculer) {
                    Icon(
                        imageVector = Icons.Filled.FastRewind,
                        contentDescription = stringResource(R.string.action_reculer),
                    )
                }
                IconButton(onClick = onArreter) {
                    Icon(
                        imageVector = Icons.Filled.Stop,
                        contentDescription = stringResource(R.string.action_stop),
                    )
                }
                if (enChargement) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    IconButton(onClick = onBasculerLecturePause) {
                        Icon(
                            imageVector = if (enLecture) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = stringResource(
                                if (enLecture) R.string.action_pause else R.string.action_lecture,
                            ),
                        )
                    }
                }
                IconButton(onClick = onAvancer) {
                    Icon(
                        imageVector = Icons.Filled.FastForward,
                        contentDescription = stringResource(R.string.action_avancer),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "PlayerBar - En lecture")
@Composable
private fun PlayerBarEnLecturePreview() {
    PsaumesChantesTheme {
        PlayerBar(
            psaume = Psaume(
                fileName = "psaume_23.mp3",
                titre = "Psaume 23",
                url = "",
                annotation = "Pour la veillée du dimanche"
            ),
            enLecture = true,
            enChargement = false,
            positionMs = 45_000L,
            dureeMs = 180_000L,
            onBasculerLecturePause = {},
            onRejouer = {},
            onArreter = {},
            onReculer = {},
            onAvancer = {}
        )
    }
}

@Preview(showBackground = true, name = "PlayerBar - En pause")
@Composable
private fun PlayerBarEnPausePreview() {
    PsaumesChantesTheme {
        PlayerBar(
            psaume = Psaume(
                fileName = "psaume_23.mp3",
                titre = "Psaume 23",
                url = "",
                annotation = null
            ),
            enLecture = false,
            enChargement = false,
            positionMs = 120_000L,
            dureeMs = 180_000L,
            onBasculerLecturePause = {},
            onRejouer = {},
            onArreter = {},
            onReculer = {},
            onAvancer = {}
        )
    }
}

@Preview(showBackground = true, name = "PlayerBar - En chargement")
@Composable
private fun PlayerBarEnChargementPreview() {
    PsaumesChantesTheme {
        PlayerBar(
            psaume = Psaume(
                fileName = "psaume_23.mp3",
                titre = "Psaume 23",
                url = ""
            ),
            enLecture = false,
            enChargement = true,
            positionMs = 0L,
            dureeMs = 180_000L,
            onBasculerLecturePause = {},
            onRejouer = {},
            onArreter = {},
            onReculer = {},
            onAvancer = {},
        )
    }
}
