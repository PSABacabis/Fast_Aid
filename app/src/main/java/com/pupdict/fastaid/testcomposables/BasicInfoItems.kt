package com.pupdict.fastaid.testcomposables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalIDListEvent
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicInfoItems(
    basicInfo: BasicInfo,
    selectedValue: String,
    onEvent: (MedicalIDListEvent) -> Unit,
//    modifier: Modifier = Modifier,
//    categoryStyle: TextStyle = MaterialTheme.typography.titleLarge,
//    titleStyle: TextStyle = MaterialTheme.typography.bodyLarge,
//    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {

    val currentDate = LocalDate.now()
//    val birthday = basicInfo.birthday
//    val age = Period.between(birthday, currentDate).years

    Column() {
        IconButton(onClick = { onEvent(MedicalIDListEvent.DeleteBasicInfo(basicInfo)) }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Note",
            )
        }
        Text(text = basicInfo.firstName)
        Text(text = basicInfo.surName)
        Text(text = basicInfo.middleName)

//        Text(text = age.toString())
//        Text(text = birthday.toString())

        Text(text = basicInfo.height.toString())
        Text(text = basicInfo.weight.toString())

        Text(text = basicInfo.organDonor)
        Text(text = basicInfo.bloodType)
        Text(text = basicInfo.gender)
        Text(text = basicInfo.address)
    }
}
