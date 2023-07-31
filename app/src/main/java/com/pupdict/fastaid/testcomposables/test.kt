package com.pupdict.fastaid.testcomposables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.Period

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgeFromBirthdate(birthDate: LocalDate) {
    val currentDate = LocalDate.now()
    val age = Period.between(birthDate, currentDate).years

    Column {
        Text("Birthdate: $birthDate")
        Text("Age: $age")
    }
}

// Example usage:
@RequiresApi(Build.VERSION_CODES.O)
const val yr = 2001
const val mn = 7
const val dy = 31
@RequiresApi(Build.VERSION_CODES.O)
val birthDate: LocalDate = LocalDate.of(yr, mn, dy)
//com.pupdict.fastaid.testcomposables.AgeFromBirthdate(com.pupdict.fastaid.getBirthDate)