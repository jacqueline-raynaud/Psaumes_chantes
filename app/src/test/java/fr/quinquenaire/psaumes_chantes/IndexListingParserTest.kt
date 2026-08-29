package fr.quinquenaire.psaumes_chantes

import fr.quinquenaire.psaumes_chantes.data.remote.IndexListingParser
import org.junit.Assert.assertEquals
import org.junit.Test

class IndexListingParserTest {

    private val baseUrl = "https://exemple.invalid/dossier/"

    @Test
    fun `extrait uniquement les liens mp3`() {
        val html = """
            <html><body>
            <a href="Psaume_02.mp3">Psaume_02.mp3</a>
            <a href="notice.pdf">notice.pdf</a>
            <a href="Psaume_10.mp3">Psaume_10.mp3</a>
            </body></html>
        """.trimIndent()

        val resultat = IndexListingParser.parser(html, baseUrl)

        assertEquals(2, resultat.size)
        assertEquals(listOf("Psaume_02.mp3", "Psaume_10.mp3"), resultat.map { it.fileName })
    }

    @Test
    fun `trie numeriquement les fichiers`() {
        val html = """
            <a href="Psaume_2.mp3">Psaume_2.mp3</a>
            <a href="Psaume_10.mp3">Psaume_10.mp3</a>
            <a href="Psaume_1.mp3">Psaume_1.mp3</a>
        """.trimIndent()

        val resultat = IndexListingParser.parser(html, baseUrl)

        assertEquals(listOf("Psaume_1.mp3", "Psaume_2.mp3", "Psaume_10.mp3"), resultat.map { it.fileName })
    }

    @Test
    fun `construit un titre lisible et une url absolue`() {
        val html = """<a href="Psaume_23_le_Seigneur.mp3">Psaume_23_le_Seigneur.mp3</a>"""

        val resultat = IndexListingParser.parser(html, baseUrl)

        assertEquals("Psaume 23 le Seigneur", resultat.single().titre)
        assertEquals("${baseUrl}Psaume_23_le_Seigneur.mp3", resultat.single().url)
    }
}
