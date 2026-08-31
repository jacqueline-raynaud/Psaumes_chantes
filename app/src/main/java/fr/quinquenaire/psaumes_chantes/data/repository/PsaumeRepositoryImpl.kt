package fr.quinquenaire.psaumes_chantes.data.repository

import fr.quinquenaire.psaumes_chantes.data.local.AnnotationDao
import fr.quinquenaire.psaumes_chantes.data.local.AnnotationEntity
import fr.quinquenaire.psaumes_chantes.data.remote.PsaumeDistant
import fr.quinquenaire.psaumes_chantes.data.remote.PsaumeRemoteDataSource
import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import fr.quinquenaire.psaumes_chantes.domain.repository.PsaumeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PsaumeRepositoryImpl @Inject constructor(
    private val remoteDataSource: PsaumeRemoteDataSource,
    private val annotationDao: AnnotationDao,
) : PsaumeRepository {

    private val catalogue = MutableStateFlow<List<PsaumeDistant>>(emptyList())

    override fun observerPsaumes(): Flow<List<Psaume>> =
        combine(catalogue, annotationDao.observerToutes()) { psaumesDistants, annotations ->
            val annotationParFichier = annotations.associateBy { it.fileName }
            psaumesDistants.map { distant ->
                Psaume(
                    fileName = distant.fileName,
                    titre = distant.titre,
                    url = distant.url,
                    annotation = annotationParFichier[distant.fileName]?.texte,
                )
            }
        }

    override suspend fun rafraichir(): Result<Unit> = runCatching {
        catalogue.value = remoteDataSource.recupererPsaumes()
    }

    override suspend fun enregistrerAnnotation(fileName: String, texte: String) {
        annotationDao.upsert(AnnotationEntity(fileName = fileName, texte = texte, misAJourLe = System.currentTimeMillis()))
    }

    override suspend fun supprimerAnnotation(fileName: String) {
        annotationDao.supprimer(fileName)
    }
}
