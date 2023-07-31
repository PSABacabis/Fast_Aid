package com.pupdict.fastaid.ui.components.medicalID.events

import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import com.pupdict.fastaid.data.medicalID.medicalnotes.MedicalNotes

sealed class MedicalIDListEvent {

    data class DeleteBasicInfo(val basicInfo: BasicInfo): MedicalIDListEvent()
    object OnUndoDeleteClickBasicInfo: MedicalIDListEvent()
    data class OnBasicInfoClick(val basicInfo: BasicInfo): MedicalIDListEvent()
    object OnAddBasicInfoClick: MedicalIDListEvent()

    data class DeleteMedicalNotes(val medicalNotes: MedicalNotes): MedicalIDListEvent()
    data class OnDoneChangeMedicalNotes(val medicalNotes: MedicalNotes, val isDone: Boolean): MedicalIDListEvent()
    object OnUndoDeleteClickMedicalNotes: MedicalIDListEvent()
    data class OnMedicalNotesClick(val medicalNotes: MedicalNotes): MedicalIDListEvent()
    object OnAddMedicalNotesClick: MedicalIDListEvent()
}

