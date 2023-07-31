package com.pupdict.fastaid.ui.components.medicalID.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotes
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotesRepository
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalNotesAddEditEvent
import com.pupdict.fastaid.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalNotesAddEditViewModel @Inject constructor(
    private val medicalnotesRepository: MedicalNotesRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var medicalNotes by mutableStateOf<MedicalNotes?>(null)
        private set

    var category by mutableStateOf("")

    var title by mutableStateOf("")

    var description by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val medicalNotesID = savedStateHandle.get<Int>("medicalNotesID")!!
        if (medicalNotesID != -1) {
            viewModelScope.launch {
                medicalnotesRepository.getMedicalNotesbyID(medicalNotesID)?.let { medicalNotes ->
                    category = medicalNotes.category
                    title = medicalNotes.title
                    description = medicalNotes.description
                    this@MedicalNotesAddEditViewModel.medicalNotes = medicalNotes
                }
            }
        }
    }

    fun onEvent(event: MedicalNotesAddEditEvent) {
        when (event) {

            is MedicalNotesAddEditEvent.OnTitleChange -> {
                title = event.title
            }

            is MedicalNotesAddEditEvent.OnCategoryChange -> {
                category = event.category
            }

            is MedicalNotesAddEditEvent.OnDescriptionChange -> {
                description = event.description
            }

            is MedicalNotesAddEditEvent.OnSaveMedicalNoteInfoClick -> {
                viewModelScope.launch {

                    if (category.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The CATEGORY is blank! Don't forget to put one."
                        ))
                        return@launch
                    }

                    medicalnotesRepository.upsertMedicalNote(
                        MedicalNotes(
                            category = category,
                            title = title,
                            description = description ,
                            isDone = medicalNotes?.isDone ?: false,
                            id = medicalNotes?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}