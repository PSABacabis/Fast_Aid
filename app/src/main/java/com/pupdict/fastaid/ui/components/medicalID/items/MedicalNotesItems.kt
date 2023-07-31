package com.pupdict.fastaid.ui.components.medicalID.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotes
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalIDListEvent

@Composable
fun MedicalNotesItems(
    medicalNotes: MedicalNotes,
    onEvent: (MedicalIDListEvent) -> Unit,
    modifier: Modifier = Modifier,
    categoryStyle: TextStyle = MaterialTheme.typography.titleLarge,
    titleStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {

    val color = when (medicalNotes.category) {
        "Medication" -> {
            MaterialTheme.colorScheme.primary
        }
        "Allergies" -> {
            MaterialTheme.colorScheme.secondary
        }
        else -> {
            Color.Blue
        }
    }

    OutlinedCard (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, color)

    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color)
            ) {

                IconButton(onClick = { onEvent(MedicalIDListEvent.DeleteMedicalNotes(medicalNotes)) }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete Note",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(
                    text = medicalNotes.category.uppercase(),
                    style = categoryStyle.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary
                )

                medicalNotes.isDone?.let {
                    Checkbox(
                        checked = it,
                        colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.onPrimary, checkedColor = color),
                        onCheckedChange = { isChecked ->
                            onEvent(
                                MedicalIDListEvent.OnDoneChangeMedicalNotes(
                                    medicalNotes,
                                    isChecked
                                )
                            )
                        }
                    )
                }
            }

            Column(
                modifier = modifier
                    .padding(20.dp)
            ){

                medicalNotes.title.let {
                    if (medicalNotes.title.isBlank()) {
                        Text(
                            text = "TITLE is empty. Tap to enter one!",
                            style = titleStyle.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Justify, color = color),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = it,
                            style = titleStyle.copy(fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Justify, color = color),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                medicalNotes.description.let {
                    if (medicalNotes.description.isBlank()) {
                        Text(
                            text = "DESCRIPTION is empty. Tap to enter one!",
                            style = descriptionStyle.copy(textAlign = TextAlign.Justify),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = it,
                            style = descriptionStyle.copy(textAlign = TextAlign.Justify),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}





