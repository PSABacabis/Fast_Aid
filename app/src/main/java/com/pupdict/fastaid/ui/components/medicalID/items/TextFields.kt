package com.pupdict.fastaid.ui.components.medicalID.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupdict.fastaid.ui.components.medicalID.events.BasicInfoAddEditEvent
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalNotesAddEditEvent
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.BasicInfoAddEditViewModel
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.MedicalNotesAddEditViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DoubleTextField(
    value: String,
    valueFor: String,
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify, lineHeight = 40.sp),
    viewModel: BasicInfoAddEditViewModel = hiltViewModel()
) {
    val formattedValue = remember(value) { mutableStateOf(value) }
    val isPlaceholder = formattedValue.value == "0.0"
    val borderColor = if (formattedValue.value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(start = 3.dp, bottom = 2.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        BasicTextField(
            modifier = modifier
//                .fillMaxWidth()
                .width(172.dp)
                .height(40.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(border = BorderStroke(1.dp, color = borderColor), shape = MaterialTheme.shapes.small),
            value = if (isPlaceholder) "" else formattedValue.value,
            onValueChange = {
                val hasDot = formattedValue.value.contains(".")
                if (it.matches(Regex("^[0-9]*\\.?[0-9]*\$"))) {
                    if (it == "." && hasDot) {
                        return@BasicTextField
                    }

                    formattedValue.value = it
                    if (valueFor == "height") {
                        viewModel.height = it
                        BasicInfoAddEditEvent.OnStatisticsChange(
                            height = it,
                            weight = viewModel.weight
                        )

                    } else if (valueFor == "weight") {
                        viewModel.weight = it
                        BasicInfoAddEditEvent.OnStatisticsChange(
                            height = viewModel.height,
                            weight = it
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Row(
                    modifier.padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            },
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StringTextField(
    value: String,
    valueFor: String,
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify, lineHeight = 40.sp),
    viewModel: BasicInfoAddEditViewModel = hiltViewModel()
) {
    val borderColor = if (value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val keyboard = when (valueFor) {
            "Middle Name" -> {
                KeyboardOptions(imeAction = ImeAction.Done)
            }
            else -> {
                KeyboardOptions(imeAction = ImeAction.Next)
            }
        }
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(border = BorderStroke(1.dp, color = borderColor), shape = MaterialTheme.shapes.small),
            value = value,
            onValueChange = {
                when (valueFor) {
                    "First Name" -> {
                        run {
                            viewModel.firstName = it
                            viewModel.onEvent(
                                BasicInfoAddEditEvent.OnNameChange(
                                    firstName = it,
                                    surName = viewModel.surName,
                                    middleName = viewModel.middleName
                                )
                            )
                        }
                    }
                    "Surname" -> {
                        run {
                            viewModel.surName = it
                            viewModel.onEvent(
                                BasicInfoAddEditEvent.OnNameChange(
                                    firstName = viewModel.firstName,
                                    surName = it,
                                    middleName = viewModel.middleName
                                )
                            )
                        }
                    }
                    "Middle Name" -> {
                        run {
                            viewModel.middleName = it
                            viewModel.onEvent(
                                BasicInfoAddEditEvent.OnNameChange(
                                    firstName = viewModel.firstName,
                                    surName = viewModel.surName,
                                    middleName = it
                                )
                            )
                        }
                    }
                }
            },
            keyboardOptions = keyboard,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Row(
                    modifier.padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StringTextFieldMedicalNotes(
    value: String,
    valueFor: String,
    modifier: Modifier = Modifier,
    label: String,
    textStyle: TextStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify, lineHeight = 40.sp),
    viewModel: MedicalNotesAddEditViewModel = hiltViewModel()
) {
    val borderColor = if (value.isBlank()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val keyboard = when (valueFor) {
            "Description" -> {
                KeyboardOptions(imeAction = ImeAction.Done)
            }
            else -> {
                KeyboardOptions(imeAction = ImeAction.Next)
            }
        }
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(border = BorderStroke(1.dp, color = borderColor), shape = MaterialTheme.shapes.small),
            value = value,
            onValueChange = {
                when (valueFor) {
                    "Title" -> {
                        run {
                            viewModel.title = it
                            viewModel.onEvent(
                                MedicalNotesAddEditEvent.OnTitleChange(it)
                            )
                        }
                    }
                    "Description" -> {
                        run {
                            viewModel.description = it
                            viewModel.onEvent(
                                MedicalNotesAddEditEvent.OnDescriptionChange(it)
                            )
                        }
                    }
                }
            },
            keyboardOptions = keyboard,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Row(
                    modifier.padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            }
        )
    }
}


