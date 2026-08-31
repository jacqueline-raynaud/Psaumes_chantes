package fr.quinquenaire.psaumes_chantes.data.player

data class EtatLecture(
    val fileNameEnCours: String? = null,
    val enLecture: Boolean = false,
    val enChargement: Boolean = false,
    val positionMs: Long = 0L,
    val dureeMs: Long = 0L,
)
