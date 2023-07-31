package com.pupdict.fastaid.data.medicalID

import androidx.room.Database
import androidx.room.RoomDatabase
//import com.pupdict.fastaid.data.medicalID.basicinfo.Allergy
//import com.pupdict.fastaid.data.medicalID.basicinfo.AllergyDAO
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfoDAO
//import com.pupdict.fastaid.data.medicalID.basicinfo.Medication
//import com.pupdict.fastaid.data.medicalID.basicinfo.MedicationDAO
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotes
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotesDAO


@Database(
//    entities = [BasicInfo::class, Allergy::class, Medication::class, MedicalNotes::class],
    entities = [BasicInfo::class, MedicalNotes::class],
    version = 1
)

abstract class MedicalIDDatabase : RoomDatabase() {
    abstract val biDAO: BasicInfoDAO
    abstract val mnDAO: MedicalNotesDAO
//    abstract val alDAO: AllergyDAO
//    abstract val mdDAO: MedicationDAO
}
