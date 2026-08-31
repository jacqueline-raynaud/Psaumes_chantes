package fr.quinquenaire.psaumes_chantes.di

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.quinquenaire.psaumes_chantes.data.local.AnnotationDao
import fr.quinquenaire.psaumes_chantes.data.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun fournirAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = "psaumes_chantes.db",
        )
            .setDriver(BundledSQLiteDriver())
            .build()

    @Provides
    fun fournirAnnotationDao(database: AppDatabase): AnnotationDao = database.annotationDao()
}
