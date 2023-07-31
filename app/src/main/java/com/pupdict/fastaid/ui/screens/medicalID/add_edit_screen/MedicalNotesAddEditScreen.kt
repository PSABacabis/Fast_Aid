package com.pupdict.fastaid.ui.screens.medicalID.add_edit_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalNotesAddEditEvent
import com.pupdict.fastaid.ui.components.medicalID.items.AutoCompleteDropDownTextBoxWithIconMedicalNotes
import com.pupdict.fastaid.ui.components.medicalID.items.StringTextFieldMedicalNotes
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.MedicalNotesAddEditViewModel
import com.pupdict.fastaid.utils.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicalNotesAddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: MedicalNotesAddEditViewModel = hiltViewModel()
) {
    val modifier = Modifier
    val scaffoldState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val totalFields = 3
    val filledFields = calculateFilledFieldsMedicalNotes(viewModel)
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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Medical Notes") },
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (viewModel.category.isNotEmpty()) {
                                viewModel.category = ""
                                viewModel.title = ""
                                viewModel.description = ""
                            }
                        }
                    ) { // Call the resetFields callback on click
                        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(modifier = modifier
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
                    .weight(9f))
            {
                Section1MedicalNotes(viewModel = viewModel)
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
                        onClick = { viewModel.onEvent(MedicalNotesAddEditEvent.OnSaveMedicalNoteInfoClick) },
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        if (viewModel.medicalNotes?.id == null) {
                            Text(text = "Create Medical Note")
                        } else {
                            Text(text = "Save Medical Note changes")
                        }
                    }
                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun calculateFilledFieldsMedicalNotes(viewModel: MedicalNotesAddEditViewModel): Int {
    var filledFields = 0
    // Check each field in the viewModel and count filled fields
    if (viewModel.category.isNotBlank()) filledFields++
    if (viewModel.title.isNotBlank()) filledFields++
    if (viewModel.description.isNotBlank()) filledFields++
    return filledFields
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Section1MedicalNotes(viewModel: MedicalNotesAddEditViewModel , modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        Arrangement.spacedBy(16.dp)
    ) {
        val category = mutableListOf("Medication", "Allergies", "Medi-memo")

        WavingGradientCard()

        AutoCompleteDropDownTextBoxWithIconMedicalNotes(
            value = viewModel.category ,
            valueFor = "Category" ,
            label = "Category" ,
            categories = category ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )

        StringTextFieldMedicalNotes(
            value = viewModel.title,
            valueFor = "Title",
            label = "Title"
        )

        StringTextFieldMedicalNotes(
            value = viewModel.description,
            valueFor = "Description",
            label = "Description"
        )
    }
}
