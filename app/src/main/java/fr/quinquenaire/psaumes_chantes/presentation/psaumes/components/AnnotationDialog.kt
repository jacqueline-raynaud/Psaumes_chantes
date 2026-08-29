package fr.quinquenaire.psaumes_chantes.presentation.psaumes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.quinquenaire.psaumes_chantes.R

@Composable
fun AnnotationDialog(
    titrePsaume: String,
    annotationExistante: String?,
    onValider: (String) -> Unit,
    onFermer: () -> Unit,
) {
    var texte by remember(annotationExistante) { mutableStateOf(annotationExistante.orEmpty()) }

    AlertDialog(
        onDismissRequest = onFermer,
        title = {
            Text(
                text = if (annotationExistante != null) {
                    stringResource(R.string.annotation_modifier)
                } else {
                    stringResource(R.string.annotation_ajouter)
                },
            )
        },
        text = {
            Column {
                Text(
                    text = titrePsaume,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                OutlinedTextField(
                    value = texte,
                    onValueChange = { texte = it },
                    label = { Text(stringResource(R.string.annotation_champ_libelle)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 6,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onValider(texte) },
                enabled = texte.isNotBlank(),
            ) {
                Text(stringResource(R.string.annotation_valider))
            }
        },
        dismissButton = {
            TextButton(onClick = onFermer) {
                Text(stringResource(R.string.annotation_annuler))
            }
        },
    )
}
