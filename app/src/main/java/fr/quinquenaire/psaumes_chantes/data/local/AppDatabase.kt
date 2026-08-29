package fr.quinquenaire.psaumes_chantes.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [AnnotationEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun annotationDao(): AnnotationDao
}
