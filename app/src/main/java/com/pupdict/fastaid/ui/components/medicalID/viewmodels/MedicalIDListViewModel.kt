package com.pupdict.fastaid.ui.components.medicalID.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.pupdict.fastaid.data.medicalID.basicinfo.Allergy
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfoRepository
//import com.pupdict.fastaid.data.medicalID.basicinfo.Medication
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotes
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotesRepository
import com.pupdict.fastaid.ui.components.medicalID.events.MedicalIDListEvent
import com.pupdict.fastaid.utils.Routes
import com.pupdict.fastaid.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicalIDListViewModel @Inject constructor(
    private val basicInfoRepository: BasicInfoRepository,
    private val medicalNotesRepository: MedicalNotesRepository
): ViewModel() {

    val basicInfo = basicInfoRepository.getBasicInfo()
    val medicalNotes = medicalNotesRepository.getMedicalNotes()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedBasicInfo: BasicInfo? = null
    private var deletedMedicalNotes: MedicalNotes? = null

    /*** Handle the events received from the UI */

    fun onEvent(event: MedicalIDListEvent) {
        when (event) {
            is MedicalIDListEvent.DeleteBasicInfo -> {
                // Delete the basic info
                viewModelScope.launch {
                    deletedBasicInfo = event.basicInfo
                    basicInfoRepository.deleteBasicInfo(event.basicInfo)
                }
            }
            is MedicalIDListEvent.DeleteMedicalNotes -> {
                // Delete the medical notes
                viewModelScope.launch {
                    deletedMedicalNotes = event.medicalNotes
                    medicalNotesRepository.deleteMedicalNote(event.medicalNotes)
                }
            }

            is MedicalIDListEvent.OnAddBasicInfoClick -> {
                // Navigate to the basic info edit screen
                sendUiEvent(UiEvent.Navigate(Routes.MEDICAL_ID_BASICINFO_EDIT))
            }
            is MedicalIDListEvent.OnAddMedicalNotesClick -> {
                // Navigate to the medical notes edit screen
                sendUiEvent(UiEvent.Navigate(Routes.MEDICAL_ID_MEDICALNOTES_EDIT))
            }

            is MedicalIDListEvent.OnBasicInfoClick -> {
                // Navigate to the specific basic info edit screen
                sendUiEvent(UiEvent.Navigate(Routes.MEDICAL_ID_BASICINFO_EDIT + "?basicInfoID=${event.basicInfo.id}"))
            }
            is MedicalIDListEvent.OnMedicalNotesClick -> {
                // Navigate to the specific medical notes edit screen
                sendUiEvent(UiEvent.Navigate(Routes.MEDICAL_ID_MEDICALNOTES_EDIT + "?medicalNotesID=${event.medicalNotes.id}"))
            }

            is MedicalIDListEvent.OnDoneChangeMedicalNotes -> {
                // Update the "isDone" flag of the medical notes
                viewModelScope.launch {
                    medicalNotesRepository.upsertMedicalNote(
                        event.medicalNotes.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is MedicalIDListEvent.OnUndoDeleteClickBasicInfo -> {
                // Undo the deletion of the basic info
                deletedBasicInfo?.let { basicInfo ->
                    viewModelScope.launch {
                        basicInfoRepository.upsertBasicInfo(basicInfo)
                    }
                }
            }
            is MedicalIDListEvent.OnUndoDeleteClickMedicalNotes -> {
                // Undo the deletion of the medical notes
                deletedMedicalNotes?.let { medicalNotes ->
                    viewModelScope.launch {
                        medicalNotesRepository.upsertMedicalNote(medicalNotes)
                    }
                }
            }
        }
    }

    /*** Send a UI event to be observed by the UI layer */

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
