package fr.quinquenaire.psaumes_chantes.domain.usecase

import fr.quinquenaire.psaumes_chantes.domain.repository.PsaumeRepository
import javax.inject.Inject

class EnregistrerAnnotationUseCase @Inject constructor(
    private val repository: PsaumeRepository,
) {
    suspend operator fun invoke(fileName: String, texte: String) {
        repository.enregistrerAnnotation(fileName, texte)
    }
}

class SupprimerAnnotationUseCase @Inject constructor(
    private val repository: PsaumeRepository,
) {
    suspend operator fun invoke(fileName: String) {
        repository.supprimerAnnotation(fileName)
    }
}
