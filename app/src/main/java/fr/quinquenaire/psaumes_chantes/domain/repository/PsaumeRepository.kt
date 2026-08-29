package fr.quinquenaire.psaumes_chantes.domain.repository

import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import kotlinx.coroutines.flow.Flow

interface PsaumeRepository {

    fun observerPsaumes(): Flow<List<Psaume>>

    suspend fun rafraichir(): Result<Unit>

    suspend fun enregistrerAnnotation(fileName: String, texte: String)

    suspend fun supprimerAnnotation(fileName: String)
}
