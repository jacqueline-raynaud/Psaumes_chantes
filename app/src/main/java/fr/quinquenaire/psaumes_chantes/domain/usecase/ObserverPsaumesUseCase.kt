package fr.quinquenaire.psaumes_chantes.domain.usecase

import fr.quinquenaire.psaumes_chantes.domain.model.Psaume
import fr.quinquenaire.psaumes_chantes.domain.repository.PsaumeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserverPsaumesUseCase @Inject constructor(
    private val repository: PsaumeRepository,
) {
    operator fun invoke(): Flow<List<Psaume>> = repository.observerPsaumes()
}
