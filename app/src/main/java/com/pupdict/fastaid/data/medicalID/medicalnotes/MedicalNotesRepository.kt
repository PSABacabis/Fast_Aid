package com.pupdict.fastaid.data.medicalID.medicalnotes

import kotlinx.coroutines.flow.Flow

interface MedicalNotesRepository {

    suspend fun upsertMedicalNote(medicalNotes: MedicalNotes)

    suspend fun deleteMedicalNote(medicalNotes: MedicalNotes)

    suspend fun getMedicalNotesbyID(id: Int):MedicalNotes

    fun getMedicalNotes(): Flow<List<MedicalNotes>>

}