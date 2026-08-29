package fr.quinquenaire.psaumes_chantes.domain.model

data class Psaume(
    val fileName: String,
    val titre: String,
    val url: String,
    val annotation: String? = null,
)
