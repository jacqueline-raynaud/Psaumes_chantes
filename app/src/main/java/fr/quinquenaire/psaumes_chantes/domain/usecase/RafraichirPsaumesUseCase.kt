package fr.quinquenaire.psaumes_chantes.domain.usecase

import fr.quinquenaire.psaumes_chantes.domain.repository.PsaumeRepository
import javax.inject.Inject

class RafraichirPsaumesUseCase @Inject constructor(
    private val repository: PsaumeRepository,
) {
    suspend operator fun invoke(): Result<Unit> = repository.rafraichir()
}
