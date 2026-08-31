package fr.quinquenaire.psaumes_chantes.data.remote

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * Parseur du listing HTML "autoindex" (Apache/Nginx) exposé par le dossier
 * distant : on en extrait uniquement les liens qui pointent vers des .mp3.
 */
object IndexListingParser {

    private val hrefMp3Regex = Regex(
        pattern = """href\s*=\s*["']([^"']+\.mp3)["']""",
        option = RegexOption.IGNORE_CASE,
    )

    fun parser(html: String, baseUrl: String): List<PsaumeDistant> {
        val base = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"

        return hrefMp3Regex.findAll(html)
            .map { it.groupValues[1] }
            .filterNot { it.startsWith("?") || it.startsWith("../") }
            .distinct()
            .map { href -> versPsaumeDistant(href, base) }
            .sortedWith(compareBy(NaturalOrderComparator) { it.fileName })
            .toList()
    }

    private fun versPsaumeDistant(href: String, baseUrl: String): PsaumeDistant {
        val url = if (href.startsWith("http://") || href.startsWith("https://")) href else baseUrl + href
        val fileName = href.substringAfterLast("/")
        val decode = runCatching { URLDecoder.decode(fileName, StandardCharsets.UTF_8.name()) }.getOrDefault(fileName)
        val titre = decode
            .removeSuffix(".mp3")
            .removeSuffix(".MP3")
            .replace('_', ' ')
            .replace('-', ' ')
            .trim()
            .replaceFirstChar { it.uppercase() }

        return PsaumeDistant(fileName = fileName, titre = titre, url = url)
    }
}

/** Trie "Psaume_2" avant "Psaume_10" en comparant les segments numériques comme des nombres. */
private object NaturalOrderComparator : Comparator<String> {
    private val segmentRegex = Regex("""\d+|\D+""")

    override fun compare(a: String, b: String): Int {
        val segmentsA = segmentRegex.findAll(a).map { it.value }.iterator()
        val segmentsB = segmentRegex.findAll(b).map { it.value }.iterator()

        while (segmentsA.hasNext() && segmentsB.hasNext()) {
            val sa = segmentsA.next()
            val sb = segmentsB.next()
            val comparaison = if (sa.firstOrNull()?.isDigit() == true && sb.firstOrNull()?.isDigit() == true) {
                (sa.toLongOrNull() ?: 0L).compareTo(sb.toLongOrNull() ?: 0L)
            } else {
                sa.compareTo(sb)
            }
            if (comparaison != 0) return comparaison
        }
        return a.length.compareTo(b.length)
    }
}
