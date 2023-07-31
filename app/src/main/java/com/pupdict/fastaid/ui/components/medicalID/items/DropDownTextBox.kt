package com.pupdict.fastaid.ui.components.medicalID.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupdict.fastaid.ui.components.medicalID.events.BasicInfoAddEditEvent.*
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.BasicInfoAddEditViewModel
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.MedicalNotesAddEditViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoCompleteDropDownTextBoxWithIcon(
    value: String,
    valueFor: String,
    label: String,
    categories: List<String>,
    viewModel: BasicInfoAddEditViewModel = hiltViewModel(), // Pass the viewModel directly
    onMonthSelected: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions,
    regexPattern: Regex? = null // Make regexPattern optional with default value null
) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldSize = remember { mutableStateOf(Size.Zero) }
    val isMonthAndDayDisabled = label == "Month" && viewModel.year.isBlank() // Disable month and day if year is empty

    // Get the software keyboard controller
    val keyboardController = current

    // Keep track of whether the keyboard is visible or not
    val isKeyboardVisible by remember { mutableStateOf(false) }

    // Calculate the bottom padding to lift the screen when the keyboard is visible
    val bottomPadding = with(LocalDensity.current) {
        if (isKeyboardVisible) 256.dp else 0.dp // You can adjust the value according to your needs
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = bottomPadding)
    ) {
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(
                    width = 1.dp,
                    color = if (value.isNotBlank()
                    ) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    shape = MaterialTheme.shapes.small
                )
                .onGloballyPositioned { coordinates ->
                    textFieldSize.value = coordinates.size.toSize()
                },
            value = value,
            onValueChange = {
                if (it.isNotBlank()) {
                    keyboardController?.show()
                }
                if (regexPattern == null || regexPattern.matches(it)) { // Only check the regexPattern if it's provided
                    // Update the corresponding viewModel property based on the label
                    when (valueFor) {
                        "Year" -> {
                            viewModel.year = it
                        }
                        "Month" -> {
                            viewModel.month = it
                            onMonthSelected(it)
                        }
                        "Day" -> {
                            viewModel.day = it
                        }
                        "Organ Donor?" -> {
                            viewModel.organDonor = it
                        }
                        "Blood Type" -> {
                            viewModel.bloodType = it
                        }
                        "Gender" -> {
                            viewModel.gender = it
                        }
                    }
                    expanded = true
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
            readOnly = isMonthAndDayDisabled
        ) // Set readOnly based on the isMonthAndDayDisabled state
        { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                innerTextField()

                // Show the close button (clear input) when the text field is not blank, otherwise show the dropdown icon
                if (value.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier.clickable(enabled = true) {
                            when (valueFor) {
                                "Year" -> {
                                    viewModel.year = ""
                                }

                                "Month" -> {
                                    viewModel.month = ""
                                }

                                "Day" -> {
                                    viewModel.day = ""
                                }

                                "Organ Donor?" -> {
                                    viewModel.organDonor = ""
                                }

                                "Blood Type" -> {
                                    viewModel.bloodType = ""
                                }

                                "Gender" -> {
                                    viewModel.gender = ""
                                }
                            }
                            expanded = false
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = if (expanded) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier
                            .clickable(enabled = !isMonthAndDayDisabled) { expanded = !expanded }
                            .padding(start = 8.dp)
                    )
                }
            }
        }

        AnimatedVisibility(visible = expanded && !isMonthAndDayDisabled) { // Hide the dropdown when month and day are disabled
            Card(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .widthIn(max = textFieldSize.value.width.dp)
                    .heightIn(max = 160.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn {
                    items(
                        categories.filter {
                            it.lowercase().contains(value.lowercase()) || it.lowercase().contains("others")
                        }
                    ) { title ->
                        CategoryItem(title = title) {
                            // Update the corresponding viewModel property based on the label
                            when (valueFor) {
                                "Year" -> {
                                    viewModel.year = it
                                }
                                "Month" -> {
                                    viewModel.month = it
                                    onMonthSelected(it)
                                }
                                "Day" -> {
                                    viewModel.day = it
                                }
                                "Organ Donor?" -> {
                                    viewModel.organDonor = it
                                }
                                "Blood Type" -> {
                                    viewModel.bloodType = it
                                }
                                "Gender" -> {
                                    viewModel.gender = it
                                }
                            }
                            expanded = false
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AutoCompleteDropDownTextBoxWithIconMedicalNotes(
    value: String,
    valueFor: String,
    label: String,
    categories: List<String>,
    viewModel: MedicalNotesAddEditViewModel = hiltViewModel(), // Pass the viewModel directly
    keyboardOptions: KeyboardOptions,
    regexPattern: Regex? = null // Make regexPattern optional with default value null
) {
    var expanded by remember { mutableStateOf(false) }
    val textFieldSize = remember { mutableStateOf(Size.Zero) }

    // Get the software keyboard controller
    val keyboardController = current

    // Keep track of whether the keyboard is visible or not
    val isKeyboardVisible by remember { mutableStateOf(false) }

    // Calculate the bottom padding to lift the screen when the keyboard is visible
    val bottomPadding = with(LocalDensity.current) {
        if (isKeyboardVisible) 256.dp else 0.dp // You can adjust the value according to your needs
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = bottomPadding)
    ) {
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 2.dp),
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(
                    width = 1.dp,
                    color = (when (value) {
                        "Medication" -> {
                            MaterialTheme.colorScheme.primary
                        }
                        "Allergies" -> {
                            MaterialTheme.colorScheme.secondary
                        }
                        "Medi-memo" -> {
                            Color.Blue
                        }
                        else -> {
                            MaterialTheme.colorScheme.outline
                        }
                    }) ,
                    shape = MaterialTheme.shapes.small
                )
                .onGloballyPositioned { coordinates ->
                    textFieldSize.value = coordinates.size.toSize()
                },
            value = value,
            onValueChange = {
                if (it.isNotBlank()) {
                    keyboardController?.show()
                }
                if (regexPattern == null || regexPattern.matches(it)) { // Only check the regexPattern if it's provided
                    // Update the corresponding viewModel property based on the label
                    when (valueFor) {
                        "Category" -> {
                            viewModel.category = it
                        }
                    }
                    expanded = true
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            keyboardOptions = keyboardOptions,
            singleLine = true,
        )
        { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                innerTextField()

                // Show the close button (clear input) when the text field is not blank, otherwise show the dropdown icon
                if (value.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray,
                        modifier = Modifier.clickable(enabled = true) {
                            when (valueFor) {
                                "Category" -> {
                                    viewModel.category = ""
                                }
                            }
                            expanded = false
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = if (expanded) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(start = 8.dp)
                    )
                }
            }
        }

        AnimatedVisibility(visible = expanded) { // Hide the dropdown when month and day are disabled
            Card(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .widthIn(max = textFieldSize.value.width.dp)
                    .heightIn(max = 160.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn {
                    items(
                        categories.filter {
                            it.lowercase().contains(value.lowercase()) || it.lowercase().contains("others")
                        }
                    ) { title ->
                        CategoryItem(title = title) {
                            // Update the corresponding viewModel property based on the label
                            when (valueFor) {
                                "Category" -> {
                                    viewModel.category = it
                                }
                            }
                            expanded = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(title) }
            .padding(10.dp)
    ) {
        Text(text = title, fontSize = 16.sp)
    }
}















