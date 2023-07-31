package com.pupdict.fastaid.ui.screens.medicalID.display_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalIDListEvent
import com.pupdict.fastaid.ui.components.medicalID.items.MedicalNotesItems
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.MedicalIDListViewModel
import com.pupdict.fastaid.utils.Routes
import com.pupdict.fastaid.utils.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicalNotesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MedicalIDListViewModel = hiltViewModel(),
) {
    val medicalNotes = viewModel.medicalNotes.collectAsState(initial = emptyList())
    val scaffoldState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                val result = scaffoldState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result==SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(MedicalIDListEvent.OnUndoDeleteClickMedicalNotes)
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(scaffoldState)},
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.onEvent(MedicalIDListEvent.OnAddMedicalNotesClick)}) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Medical Notes"
                )
            }
        },
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Medical Notes") },
                navigationIcon = {
                    IconButton(onClick = {  onNavigate(UiEvent.Navigate(Routes.FASTAID_MAINSCREEN)) }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){
        LazyColumn(
            Modifier.fillMaxSize(),
        ){

            item {
                Button(
                    onClick = { onNavigate(UiEvent.Navigate(Routes.FASTAID_MAINSCREEN)) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Go Back")
                }
            }


            items(medicalNotes.value) { medicalNotes ->
                Spacer(modifier = Modifier.height(10.dp))
                MedicalNotesItems(
                    medicalNotes = medicalNotes,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                MedicalIDListEvent.OnMedicalNotesClick(medicalNotes)
                            )
                        }
                )
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedCard(
                    onClick = { viewModel.onEvent(MedicalIDListEvent.OnAddMedicalNotesClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 20.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                    ){
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add Medical Notes",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = "Tap to add a Medical Note",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}