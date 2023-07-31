package com.pupdict.fastaid.ui.components.medicalID.items

import android.os.Build
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicInfoDatePickerTextBox(
    viewModel: BasicInfoAddEditViewModel = hiltViewModel()
) {
    val selectedDate = remember { mutableStateOf<LocalDate?>(LocalDate.now().minusDays(3)) }
    val calendarState = rememberUseCaseState()

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        value = selectedDate.value?.toString() ?: "",
        onValueChange = { /* Handle value change if needed */ },
        label = { Text("Birthdate") },
        trailingIcon = {
            IconButton(onClick = { calendarState.show() }) {
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


