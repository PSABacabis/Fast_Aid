package com.pupdict.fastaid.ui.components.medicalID.events

sealed class MedicalNotesAddEditEvent {
    data class OnCategoryChange(val category: String): MedicalNotesAddEditEvent()
    data class OnTitleChange(val title: String): MedicalNotesAddEditEvent()
    data class OnDescriptionChange(val description: String): MedicalNotesAddEditEvent()
    object OnSaveMedicalNoteInfoClick: MedicalNotesAddEditEvent()
}
