package com.pupdict.fastaid.ui.components.medicalID.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pupdict.fastaid.R
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import java.time.LocalDate
import java.time.Period


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalIDCardTest(
    basicInfo: BasicInfo,
    modifier: Modifier = Modifier,
) {

    val currentDate = LocalDate.now()
    val yearString = basicInfo.year
    val dayString = basicInfo.day
    val monthNames = mutableListOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val monthMap = mutableMapOf<String, String>()

    for (month in monthNames) {
        when (month) {
            "January" -> monthMap[month] = "01"
            "February" -> monthMap[month] = "02"
            "March" -> monthMap[month] = "03"
            "April" -> monthMap[month] = "04"
            "May" -> monthMap[month] = "05"
            "June" -> monthMap[month] = "06"
            "July" -> monthMap[month] = "07"
            "August" -> monthMap[month] = "08"
            "September" -> monthMap[month] = "09"
            "October" -> monthMap[month] = "10"
            "November" -> monthMap[month] = "11"
            "December" -> monthMap[month] = "12"
        }
    }

    var monthString = basicInfo.month
    monthString = monthMap[monthString] ?: monthString

    val year = yearString.toInt()
    val month = monthString.toInt()
    val day = dayString.toInt()

    val birthday = LocalDate.of(year, month, day)
    val age = Period.between(birthday, currentDate).years

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        // Placeholder for the photo
        Box(
            modifier = Modifier
                .height(48.dp)
                .width(48.dp) // Assuming you want a square placeholder
                .background(color = MaterialTheme.colorScheme.onPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Rounded.Person, contentDescription = "Placeholder")
        }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            onClick = { /*TODO*/ },
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Names
                    Column(
                        modifier = Modifier.weight(4f)
                    ) {
                        Text(
                            text = "${basicInfo.firstName} ${basicInfo.middleName} ${basicInfo.surName}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = basicInfo.gender + " | <Example Address>",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Age
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Age")
                        Text(text = age.toString(), style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Height
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Height")
                        Text(text = "${basicInfo.height}cm", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Organ Donor
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Organ Donor")
                        Text(text = basicInfo.organDonor, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Birthday
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Birthday")
                        Text(text = birthday.toString(), style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Weight
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Weight")
                        Text(text = "${basicInfo.weight}kg", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Blood Type
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Blood Type")
                        Text(text = basicInfo.bloodType, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalIDCard(
    basicInfo: BasicInfo,
    modifier: Modifier = Modifier,
) {

    val currentDate = LocalDate.now()
    val yearString = basicInfo.year
    val dayString = basicInfo.day
    val monthNames = mutableListOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val monthMap = mutableMapOf<String, String>()

    for (month in monthNames) {
        when (month) {
            "January" -> monthMap[month] = "01"
            "February" -> monthMap[month] = "02"
            "March" -> monthMap[month] = "03"
            "April" -> monthMap[month] = "04"
            "May" -> monthMap[month] = "05"
            "June" -> monthMap[month] = "06"
            "July" -> monthMap[month] = "07"
            "August" -> monthMap[month] = "08"
            "September" -> monthMap[month] = "09"
            "October" -> monthMap[month] = "10"
            "November" -> monthMap[month] = "11"
            "December" -> monthMap[month] = "12"
        }
    }

    var monthString = basicInfo.month
    monthString = monthMap[monthString] ?: monthString

    val year = yearString.toInt()
    val month = monthString.toInt()
    val day = dayString.toInt()

    val birthday = LocalDate.of(year, month, day)
    val age = Period.between(birthday, currentDate).years

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = modifier
                .height(48.dp)
                .background(color = MaterialTheme.colorScheme.onPrimary)
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            onClick = { /*TODO*/ },
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f)) {
                        Card(
                            modifier.fillMaxSize(),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Box(
                                modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Image(painter = painterResource(id = R.drawable.ic_fastaid_logo_light) , contentDescription = "FastAidLogo" )
                            }
                        }
                    }
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(4f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${basicInfo.firstName} ${basicInfo.middleName} ${basicInfo.surName}",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${basicInfo.gender} \u2022 Metro Manila PH",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                Spacer(modifier.height(20.dp))

                Row(
                    modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Age")
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = age.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Height")
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${basicInfo.height} cm",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                    verticalArrangement = Arrangement.Center
                    ) {
                    Row(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Organ Donor")
                    }
                    Row(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = basicInfo.organDonor,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                }

                Spacer(modifier.height(12.dp))

                Row(
                    modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Birthday")
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = birthday.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Weight")
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${basicInfo.weight} kg",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                    Column(
                        modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Blood Type")
                        }
                        Row(
                            modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = basicInfo.bloodType,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MedicalIDCardPreview() {
//    MedicalIDCard()
}
