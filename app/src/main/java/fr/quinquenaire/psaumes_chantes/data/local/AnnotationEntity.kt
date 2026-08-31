package fr.quinquenaire.psaumes_chantes.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "annotations")
data class AnnotationEntity(
    @PrimaryKey
    val fileName: String,
    val texte: String,
    val misAJourLe: Long,
)
