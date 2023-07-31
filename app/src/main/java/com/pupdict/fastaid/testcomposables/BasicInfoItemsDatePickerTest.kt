package com.pupdict.fastaid.testcomposables

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.BasicInfoAddEditViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicInfoPickerTextBoxTest(
    viewModel: BasicInfoAddEditViewModel = hiltViewModel()
) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val calendarState = rememberUseCaseState()
    val isInputFinished = remember { mutableStateOf(false) }

    val dateValidator: (String) -> Boolean = { date ->
        try {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d")
            LocalDate.parse(date, dateFormatter)
            true
        } catch (e: Exception) {
            false
        }
    }

    val context = LocalContext.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        value = if (isInputFinished.value) selectedDate.value?.toString() ?: "" else "",
        onValueChange = { newValue ->
            if (newValue.isEmpty()) {
                selectedDate.value = null
                isInputFinished.value = false
            } else {
                selectedDate.value = LocalDate.parse(newValue)
                isInputFinished.value = false
            }
        },
        label = { Text("Birthdate") },
        trailingIcon = {
            IconButton(onClick = {
                calendarState.show()
                isInputFinished.value = true
            }) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Open calendar"
                )
            }
        }
    )

    BasicInfoDatePicker(
        selectedDate = selectedDate.value,
        onDateSelected = { newDate ->
            selectedDate.value = newDate
//            viewModel.birthday = newDate
        },
        calendarState = calendarState
    )

    if (isInputFinished.value && selectedDate.value != null) {
        if (!dateValidator(selectedDate.value.toString())) {
            showToast("Invalid input. Please use the format yyyy-M-d.", context)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicInfoDatePicker(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    calendarState: UseCaseState
) {
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Date(
            selectedDate = selectedDate
        ) { newDate ->
            onDateSelected(newDate)
        },
    )
}

fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

