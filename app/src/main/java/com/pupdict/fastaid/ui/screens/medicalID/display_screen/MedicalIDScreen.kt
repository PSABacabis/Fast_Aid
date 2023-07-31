package com.pupdict.fastaid.ui.screens.medicalID.display_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import com.pupdict.fastaid.ui.components.medicalID.items.MedicalIDCard
import com.pupdict.fastaid.ui.components.medicalID.items.MedicalNotesItems
import com.pupdict.fastaid.ui.components.medicalID.viewmodels.MedicalIDListViewModel
import com.pupdict.fastaid.utils.Routes
import com.pupdict.fastaid.utils.UiEvent

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MedicalIDScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MedicalIDListViewModel = hiltViewModel(),
) {
    val basicInfo = viewModel.basicInfo.collectAsState(initial = emptyList())
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
            ExtendedFabWithText(onClick = {viewModel.onEvent(MedicalIDListEvent.OnAddMedicalNotesClick)})
        },
        topBar = {
            // TopAppBar content goes here
            TopAppBar(
                title = { Text(text = "Medical ID") },
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
            if (basicInfo.value.isEmpty()){
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                    OutlinedCard(
                        onClick = { viewModel.onEvent(MedicalIDListEvent.OnAddBasicInfoClick) },
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
                                text = "Tap to add a Medical ID",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }

            items(basicInfo.value) { basicInfo ->
                Spacer(modifier = Modifier.height(24.dp))
                MedicalIDCard(
                    basicInfo = basicInfo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(
                                MedicalIDListEvent.OnBasicInfoClick(basicInfo)
                            )
                        }
                )
            }

            items(medicalNotes.value) { medicalNotes ->
                Spacer(modifier = Modifier.height(24.dp))
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
        }
    }
}

@Composable
fun ExtendedFabWithText(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Basic Info",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        text = {
            Text(
                text = "Add Medical Note",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}