package com.pupdict.fastaid.ui.components.medicalID.events

sealed class BasicInfoAddEditEvent {
    data class OnNameChange(val firstName: String, val surName: String, val middleName: String) : BasicInfoAddEditEvent()
    data class OnBirthdateChange(val year: String, val month: String, val day: String) : BasicInfoAddEditEvent()
    data class OnStatisticsChange(val height: String, val weight: String) : BasicInfoAddEditEvent()
    data class OnAdditionalInfoChange(val organDonor: String, val bloodType: String, val gender: String, val address: String) : BasicInfoAddEditEvent()
    object OnSaveBasicInfoClick : BasicInfoAddEditEvent()
}

