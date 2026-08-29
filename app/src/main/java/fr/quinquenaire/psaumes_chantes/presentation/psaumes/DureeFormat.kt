package fr.quinquenaire.psaumes_chantes.presentation.psaumes

import java.util.Locale

fun formaterDuree(ms: Long): String {
    val totalSecondes = (ms / 1000).coerceAtLeast(0)
    val minutes = totalSecondes / 60
    val secondes = totalSecondes % 60
    return String.format(Locale.getDefault(), "%d:%02d", minutes, secondes)
}
