package com.pupdict.fastaid.data.medicalID.medicalnotes

import kotlinx.coroutines.flow.Flow

class MedicalNotesRepositoryImpl(
    private val mnDAO: MedicalNotesDAO
): MedicalNotesRepository {

    override suspend fun upsertMedicalNote(medicalNotes: MedicalNotes) {
        mnDAO.upsertMedicalNote(medicalNotes)
    }

    override suspend fun deleteMedicalNote(medicalNotes: MedicalNotes) {
        mnDAO.deleteMedicalNote(medicalNotes)
    }

    override suspend fun getMedicalNotesbyID(id: Int): MedicalNotes {
        return mnDAO.getMedicalNotesbyID(id)
    }

    override fun getMedicalNotes(): Flow<List<MedicalNotes>> {
        return mnDAO.getMedicalNotes()
    }

}