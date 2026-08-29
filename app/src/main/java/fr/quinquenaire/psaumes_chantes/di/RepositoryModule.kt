package fr.quinquenaire.psaumes_chantes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.quinquenaire.psaumes_chantes.data.repository.PsaumeRepositoryImpl
import fr.quinquenaire.psaumes_chantes.domain.repository.PsaumeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun lierPsaumeRepository(impl: PsaumeRepositoryImpl): PsaumeRepository
}
