package fr.quinquenaire.psaumes_chantes.presentation.psaumes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PsaumesListScreen(
    modifier: Modifier = Modifier,
    viewModel: PsaumesListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    PsaumesListContent(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}
