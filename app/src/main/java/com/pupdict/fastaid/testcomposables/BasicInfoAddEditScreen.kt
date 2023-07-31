package com.pupdict.fastaid.testcomposables
//import android.annotation.SuppressLint
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Check
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.pupdict.fastaid.ui.components.medicalID.events.BasicInfoAddEditEvent
//import com.pupdict.fastaid.ui.components.medicalID.items.AutoCompleteDropDownTextBox
//import com.pupdict.fastaid.ui.components.medicalID.viewmodels.BasicInfoAddEditViewModel
//import com.pupdict.fastaid.utils.UiEvent
//import java.time.LocalDate
//import java.util.*
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun BasicInfoAddEditScreen(
//    onPopBackStack: () -> Unit,
//    viewModel: BasicInfoAddEditViewModel = hiltViewModel()
//) {
//    val modifier = Modifier
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
//    var showDialog by remember { mutableStateOf(false) }
//    val disabledValue = ""
//    var selectedIndex by remember { mutableIntStateOf(0) }
//    val scaffoldState = remember { SnackbarHostState() }
//    val scrollState = rememberScrollState()
//    var rowSize by remember { mutableStateOf(Size.Zero) }
//
//    LaunchedEffect(key1 = true) {
//        viewModel.uiEvent.collect { event ->
//            when (event) {
//                is UiEvent.PopBackStack -> onPopBackStack()
//                is UiEvent.ShowSnackbar -> {
//                    scaffoldState.showSnackbar(
//                        message = event.message,
//                        actionLabel = event.action
//                    )
//                }
//                else -> Unit
//            }
//        }
//    }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(scaffoldState) },
//        modifier = modifier.fillMaxSize(),
//    ) {
//        Column(
//            modifier = modifier
//                .verticalScroll(scrollState)
//        ) {
//
//            Text(
//                text = "Enter Basic Information Details",
//                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
//                modifier = modifier.padding(start = 30.dp)
//            )
//
//            Spacer(modifier = modifier.height(10.dp))
//
//            OutlinedTextField(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp),
//                value = viewModel.firstName,
//                onValueChange = { viewModel.onEvent(BasicInfoAddEditEvent.OnNameChange(firstName = it, surName = viewModel.surName, middleName = viewModel.middleName)) },
//                label = { Text(text = "First Name") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify)
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp),
//                value = viewModel.surName,
//                onValueChange = { viewModel.onEvent(BasicInfoAddEditEvent.OnNameChange(firstName = viewModel.firstName, surName = it, middleName = viewModel.middleName)) },
//                label = { Text(text = "Surname") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify)
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp),
//                value = viewModel.middleName,
//                onValueChange = { viewModel.onEvent(BasicInfoAddEditEvent.OnNameChange(firstName = viewModel.firstName, surName = viewModel.surName, middleName = it)) },
//                label = { Text(text = "Middle Name") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify)
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//
//            // Birthdate
////            BasicInfoDatePicker()
//
//
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            //Statistics
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp),
//                value = viewModel.height?.toString() ?: "",
//                onValueChange = {
//                    it.toDoubleOrNull()
//                        ?.let { it1 -> BasicInfoAddEditEvent.OnStatisticsChange(height = it1, weight = viewModel.weight) }
//                        ?.let { it2 -> viewModel.onEvent(it2) }
//                },
//                label = { Text(text = "Height (in cm)") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify)
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            OutlinedTextField(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp),
//                value = viewModel.weight?.toString() ?: "",
//                onValueChange = {
//                    it.toDoubleOrNull()
//                        ?.let { it1 -> BasicInfoAddEditEvent.OnStatisticsChange(weight = it1, height = viewModel.height) }
//                        ?.let { it2 -> viewModel.onEvent(it2) }
//                },
//                label = { Text(text = "Weight (in kg)") },
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Justify)
//            )
//            Spacer(modifier = modifier.height(10.dp))
//            AutoCompleteDropDownTextBox(
//                label = "Organ Donor?",
//                content = viewModel.organDonor,
//                categories = mutableListOf("Yes", "No")
//            )
//            Spacer(modifier = modifier.height(10.dp))
//            AutoCompleteDropDownTextBox(
//                label = "Blood Type",
//                content = viewModel.bloodType,
//                categories = mutableListOf("A", "A+", "A-", "B", "B+", "B-", "AB", "AB+", "AB-", "0", "0+", "O-")
//            )
//            Spacer(modifier = modifier.height(10.dp))
//            AutoCompleteDropDownTextBox(
//                label = "Gender",
//                content = viewModel.gender,
//                categories = mutableListOf("Male", "Female", "Others", "I prefer not to say")
//            )
//            Button(onClick = { viewModel.onEvent(BasicInfoAddEditEvent.OnSaveBasicInfoClick) }) {
//                Icon(
//                    imageVector = Icons.Rounded.Check,
//                    contentDescription = "Save Medical Note"
//                )
//            }
//        }
//    }
//}
//
