package com.pupdict.fastaid.data.medicalID.basicinfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basic_info")
data class BasicInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var age: Int,
    val height: String,
    val weight: String,
    val gender: String,
    val bloodType: String,
    val organDonor: String,
    val year: String,
    val month: String,
    val day: String,
    val firstName: String,
    val middleName: String,
    val surName: String,
    val address: String
)
