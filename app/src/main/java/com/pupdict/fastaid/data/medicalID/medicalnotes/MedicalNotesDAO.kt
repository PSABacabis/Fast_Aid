package com.pupdict.fastaid.data.medicalID.medicalnotes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalNotesDAO {

    @Upsert
    suspend fun upsertMedicalNote(medicalNotes: MedicalNotes)

    @Delete
    suspend fun deleteMedicalNote(medicalNotes: MedicalNotes)

    @Query("SELECT * FROM medicalNotes WHERE id = :id")
    suspend fun getMedicalNotesbyID(id: Int):MedicalNotes

//    @Query("SELECT * FROM medicalNotes" )
//    fun getMedicalNotes(): Flow<List<MedicalNotes>>

    @Query("SELECT * FROM medicalNotes ORDER BY CASE category WHEN 'Allergies' THEN 1 WHEN 'Medication' THEN 2 WHEN 'Medi-memo' THEN 3 ELSE 4 END" )
    fun getMedicalNotes(): Flow<List<MedicalNotes>>


}
