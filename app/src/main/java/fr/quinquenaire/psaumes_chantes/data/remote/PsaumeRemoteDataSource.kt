package fr.quinquenaire.psaumes_chantes.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class PsaumeRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient,
    @BaseUrl private val baseUrl: String,
) {
    suspend fun recupererPsaumes(): List<PsaumeDistant> {
        val html = httpClient.get(baseUrl).bodyAsText()
        return IndexListingParser.parser(html, baseUrl)
    }
}
