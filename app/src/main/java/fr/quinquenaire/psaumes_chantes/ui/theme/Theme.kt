package fr.quinquenaire.psaumes_chantes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = RoseVif,
    onPrimary = FondSombre,
    secondary = VioletClairAccent,
    onSecondary = FondSombre,
    tertiary = RoseFonce,
    background = FondSombre,
    onBackground = SurfaceClaireAccent,
    surface = SurfaceSombre,
    onSurface = SurfaceClaireAccent,
)

// Même en thème clair, on reste sur des teintes rose/violet soutenues,
// jamais pastel : c'est un choix d'identité visuelle, pas un mode "dark only".
private val LightColorScheme = lightColorScheme(
    primary = VioletMoyen,
    onPrimary = SurfaceClaireAccent,
    secondary = RoseFonce,
    onSecondary = SurfaceClaireAccent,
    tertiary = RoseVif,
    background = VioletProfond,
    onBackground = SurfaceClaireAccent,
    surface = SurfaceSombre,
    onSurface = SurfaceClaireAccent,
)

@Composable
fun PsaumesChantesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
