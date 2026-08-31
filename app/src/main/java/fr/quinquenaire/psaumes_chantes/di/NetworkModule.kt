package fr.quinquenaire.psaumes_chantes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.quinquenaire.psaumes_chantes.BuildConfig
import fr.quinquenaire.psaumes_chantes.data.remote.BaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @BaseUrl
    fun fournirBaseUrl(): String = BuildConfig.PSAUMES_BASE_URL

    @Provides
    @Singleton
    fun fournirHttpClient(): HttpClient = HttpClient(CIO)
}
