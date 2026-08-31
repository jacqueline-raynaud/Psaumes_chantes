package fr.quinquenaire.psaumes_chantes.data.local

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnotationDao {

    @Query("SELECT * FROM annotations")
    fun observerToutes(): Flow<List<AnnotationEntity>>

    @Upsert
    suspend fun upsert(entity: AnnotationEntity)

    @Query("DELETE FROM annotations WHERE fileName = :fileName")
    suspend fun supprimer(fileName: String)
}
