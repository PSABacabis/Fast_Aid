package com.pupdict.fastaid.data.medicalID.medicalnotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicalNotes")
data class MedicalNotes(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val description: String,
    val isDone: Boolean?,
    val category: String,
)



