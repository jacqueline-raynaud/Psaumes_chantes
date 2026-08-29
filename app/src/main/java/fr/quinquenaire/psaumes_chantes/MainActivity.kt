package fr.quinquenaire.psaumes_chantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import fr.quinquenaire.psaumes_chantes.presentation.psaumes.PsaumesListScreen
import fr.quinquenaire.psaumes_chantes.ui.theme.PsaumesChantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsaumesChantesTheme {
                PsaumesListScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
