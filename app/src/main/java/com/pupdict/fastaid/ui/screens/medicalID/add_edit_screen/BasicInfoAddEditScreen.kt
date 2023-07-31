package com.pupdict.fastaid.ui.screens.medicalID.add_edit_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupdict.fastaid.R
import com.pupdict.fastaid.ui.components.medicalID.events.BasicInfoAddEditEvent
import com.pupdict.fastaid.ui.components.medicalID.items.AutoCompleteDropDownTextBoxWithIcon
import com.pupdict.fastaid.ui.components.medicalID.items.DoubleTextField
import com.pupdict.fastaid.ui.components.medicalID.items.StringTextField
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.BasicInfoAddEditViewModel
import com.pupdict.fastaid.utils.UiEvent
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BasicInfoAddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: BasicInfoAddEditViewModel = hiltViewModel(),
) {
    val modifier = Modifier
    val scaffoldState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val totalFields = 10
    val filledFields = calculateFilledFields(viewModel)
    val progress = filledFields.toFloat() / totalFields.toFloat()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(scaffoldState) },
        modifier = modifier.fillMaxSize(),
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Medical ID") },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (viewModel.firstName.isNotEmpty()) {
                                viewModel.firstName = ""
                                viewModel.surName = ""
                                viewModel.middleName = ""
                                viewModel.year = ""
                                viewModel.month = ""
                                viewModel.day = ""
                                viewModel.height = ""
                                viewModel.weight = ""
                                viewModel.organDonor = ""
                                viewModel.bloodType = ""
                                viewModel.gender = ""
                            }
                        }
                    ) { // Call the resetFields callback on click
                        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                    }
                }
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier
                    .fillMaxSize()
                    .weight(9f)
            ) {
                // Content of each page
                Page1Content(viewModel = viewModel)
                Spacer(modifier = Modifier.height(48.dp))
            }

            Column(
                modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Linear progress indicator at the bottom of the screen
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    Alignment.Center
                ) {
                    Button(
                        onClick = { viewModel.onEvent(BasicInfoAddEditEvent.OnSaveBasicInfoClick) },
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        if (viewModel.basicInfo?.id == null) {
                            Text(text = "Create Medical ID")
                        } else {
                            Text(text = "Save Medical ID changes")
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Page1Content(viewModel: BasicInfoAddEditViewModel) {
    var section1Visible by remember { mutableStateOf(true) }
    var section2Visible by remember { mutableStateOf(false) }
    var section3Visible by remember { mutableStateOf(false) }
    var section4Visible by remember { mutableStateOf(false) }

    // Calculate the progress based on the number of filled fields
    val filledFields = calculateFilledFields(viewModel)
    val scrollState = rememberScrollState()


    LaunchedEffect(viewModel.firstName, viewModel.surName, viewModel.middleName,
        viewModel.height, viewModel.weight, viewModel.year, viewModel.month,
        viewModel.day, viewModel.organDonor, viewModel.bloodType, viewModel.gender) {
        // When any of the fields change, update the visibility of each section
        section1Visible = filledFields >= 0
        section2Visible = filledFields >= 2 // Adjust the value based on the number of fields in Section 2
        section3Visible = filledFields >= 4 // Adjust the value based on the number of fields in Section 3
        section4Visible = filledFields >= 7 // Adjust the value based on the number of fields in Section 3
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)        ,
        Arrangement.spacedBy(12.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section 1
        AnimatedVisibility(visible = section1Visible) {
            Section1(viewModel, Modifier.fillMaxWidth())
        }

        // Section 2
        AnimatedVisibility(visible = section2Visible) {
            Section2(viewModel, Modifier.fillMaxWidth())
        }

        // Section 3
        AnimatedVisibility(visible = section3Visible) {
            Section3(viewModel, Modifier.fillMaxWidth())
        }

        // Section 4
        AnimatedVisibility(visible = section4Visible) {
            Section4(viewModel, Modifier.fillMaxWidth())
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Section1(viewModel: BasicInfoAddEditViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, Arrangement.spacedBy(16.dp)) {
        WavingGradientCard()
        StringTextField(value = viewModel.firstName, valueFor = "First Name", label = "First Name")
        StringTextField(value = viewModel.surName, valueFor = "Surname", label = "Surname")
        StringTextField(value = viewModel.middleName, valueFor = "Middle Name", label = "Middle Name")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Section2(viewModel: BasicInfoAddEditViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier, Arrangement.spacedBy(16.dp)) {
        Spacer(modifier = modifier.height(1.dp))
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = modifier.height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            DoubleTextField(
                value = viewModel.height,
                valueFor = "height",
                label = "Height (in cm)"
            )

            DoubleTextField(
                value = viewModel.weight,
                valueFor = "weight",
                label = "Weight (in kg)"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Section3(viewModel: BasicInfoAddEditViewModel, modifier: Modifier = Modifier) {

    val yearsList: MutableList<String> = mutableListOf()
    for (number in 1900..2023) {
        yearsList.add(number.toString())
    }
    var daysList by remember { mutableStateOf<List<String>>(emptyList()) }
    val monthNames = mutableListOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    fun updateDaysList(selectedMonth: String) {
        val selectedDay = viewModel.day.toIntOrNull()

        val daysInMonth = when (selectedMonth) {
            "February" -> {
                // Check for leap year logic and set daysInMonth accordingly
                // Example: For simplicity, assuming all years divisible by 4 are leap years
                val leapYear = viewModel.year.toInt() % 4 == 0
                if (leapYear) 29 else 28
            }
            "April", "June", "September", "November" -> 30
            else -> 31
        }

        daysList = (1..daysInMonth).map { it.toString() }

        if ((selectedDay == null) || (selectedDay > daysInMonth) || viewModel.day.isBlank()) {
            // If the selected day is not valid for the selected month,
            // or viewModel.day is empty, update it to the maximum valid day
            viewModel.day = daysInMonth.toString()
        }
    }

    Column(modifier = modifier, Arrangement.spacedBy(16.dp)) {

        Spacer(modifier = modifier.height(1.dp))
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = modifier.height(1.dp))

        AutoCompleteDropDownTextBoxWithIcon(
            label = "Year",
            categories = yearsList,
            value = viewModel.year,
            valueFor = "Year",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            regexPattern = Regex("\\d{0,4}")
        )

        AutoCompleteDropDownTextBoxWithIcon(
            label = "Month",
            categories = monthNames,
            value = viewModel.month,
            valueFor = "Month",
            onMonthSelected = { selectedMonth ->
                updateDaysList(selectedMonth)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            regexPattern = Regex("^[a-zA-Z]*\$")
        )

        AutoCompleteDropDownTextBoxWithIcon(
            label = "Day",
            categories = daysList,
            value = viewModel.day,
            valueFor = "Day",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            regexPattern = Regex("\\d{0,2}")
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Section4(viewModel: BasicInfoAddEditViewModel, modifier: Modifier = Modifier) {

    val organDonorYN = mutableListOf("Yes", "No")
    val bloodType = mutableListOf("A", "A+", "A-", "B", "B+", "B-", "AB", "AB+", "AB-", "O", "O+", "O-")
    val genderChoice = mutableListOf("Male", "Female", "Others", "I prefer not to say")

    Column(modifier = modifier, Arrangement.spacedBy(16.dp)) {

        Spacer(modifier = modifier.height(1.dp))
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = modifier.height(1.dp))
        AutoCompleteDropDownTextBoxWithIcon(
            value = viewModel.organDonor,
            valueFor = "Organ Donor?",
            label = "Organ Donor",
            categories = organDonorYN,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            regexPattern = Regex("^[a-zA-Z]*\$")
        )
        AutoCompleteDropDownTextBoxWithIcon(
            value = viewModel.bloodType,
            valueFor = "Blood Type",
            label = "Blood Type",
            categories = bloodType,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            regexPattern = Regex("^[a-zA-Z]*\$")
        )
        AutoCompleteDropDownTextBoxWithIcon(
            value = viewModel.gender,
            valueFor = "Gender",
            label = "Gender",
            categories = genderChoice,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            regexPattern = Regex("^[a-zA-Z]*\$")
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun calculateFilledFields(viewModel: BasicInfoAddEditViewModel): Int {
    var filledFields = 0
    // Check each field in the viewModel and count filled fields
    if (viewModel.firstName.isNotBlank()) filledFields++
    if (viewModel.surName.isNotBlank()) filledFields++
    if (viewModel.height.isNotBlank()) filledFields++
    if (viewModel.weight.isNotBlank()) filledFields++
    if (viewModel.year.isNotBlank()) filledFields++
    if (viewModel.month.isNotBlank()) filledFields++
    if (viewModel.day.isNotBlank()) filledFields++
    if (viewModel.organDonor.isNotBlank()) filledFields++
    if (viewModel.bloodType.isNotBlank()) filledFields++
    if (viewModel.gender.isNotBlank()) filledFields++

    return filledFields
}

@Composable
fun WavingGradientCard(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 10000
                0f at 0 with LinearOutSlowInEasing
                1f at 1000 with LinearOutSlowInEasing
                0f at 2000 with LinearOutSlowInEasing
                1f at 3000 with LinearOutSlowInEasing
                0f at 4000 with LinearOutSlowInEasing
                1f at 5000 with LinearOutSlowInEasing
                0f at 6000 with LinearOutSlowInEasing
                1f at 7000 with LinearOutSlowInEasing
                0f at 8000 with LinearOutSlowInEasing
                1f at 9000 with LinearOutSlowInEasing
                0f at 10000 with LinearOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart
        )
    )

    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.primary,
    )

    val cornerRadiusValue = 16.dp // Set your desired corner radius value here

    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .height(64.dp),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val brush = Brush.linearGradient(
                colors = gradientColors,
                start = Offset(size.width * animationProgress, 0f),
                end = Offset(size.width * (animationProgress + 1), 0f),
            )
            drawRoundRect(
                brush = brush,
                size = size,
                cornerRadius = CornerRadius(cornerRadiusValue.toPx()),
                alpha = 0.8f,
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    this.translationY = 6.dp.toPx() * animationProgress // Move the icon vertically
                },
                tint = Color.White,
            )
            Image(painter = painterResource(id = R.drawable.ic_fastaid_logo_light) , contentDescription = "FastAidLogo" )
        }
    }
}