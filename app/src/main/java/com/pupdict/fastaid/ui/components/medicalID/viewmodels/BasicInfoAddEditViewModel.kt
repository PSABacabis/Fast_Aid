package com.pupdict.fastaid.ui.components.medicalID.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfo
import com.pupdict.fastaid.data.medicalID.basicinfo.BasicInfoRepository
import com.pupdict.fastaid.ui.components.medicalID.events.BasicInfoAddEditEvent
import com.pupdict.fastaid.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class BasicInfoAddEditViewModel @Inject constructor(
    private val basicinfoRepository: BasicInfoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var basicInfo by mutableStateOf<BasicInfo?>(null)
        private set

    //Name
    var firstName by mutableStateOf("")
    var surName by mutableStateOf("")
    var middleName by mutableStateOf("")

    //Birthdate
    var year by mutableStateOf("")
    var month by mutableStateOf("")
    var day by mutableStateOf("")
    var age by mutableIntStateOf(0)

    //Statistics
    var height by mutableStateOf("")
    var weight by mutableStateOf("")

    //Additional Information
    var organDonor by mutableStateOf("")
    var bloodType by mutableStateOf("")
    var gender by mutableStateOf("")
    var address by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val basicInfoID = savedStateHandle.get<Int>("basicInfoID")!!
        if (basicInfoID != -1) {
            viewModelScope.launch {
                basicinfoRepository.getBasicInfobyID(basicInfoID)?.let { basicInfo ->
                    firstName = basicInfo.firstName
                    surName = basicInfo.surName
                    middleName = basicInfo.middleName
                    year = basicInfo.year
                    month = basicInfo.month
                    day = basicInfo.day
                    age = basicInfo.age
                    height = basicInfo.height
                    weight = basicInfo.weight
                    organDonor = basicInfo.organDonor
                    gender = basicInfo.gender
                    bloodType = basicInfo.bloodType
                    address = basicInfo.address
                    this@BasicInfoAddEditViewModel.basicInfo = basicInfo
                }
            }
        }
    }

    fun onEvent(event: BasicInfoAddEditEvent) {
        when (event) {
            is BasicInfoAddEditEvent.OnBirthdateChange -> {
                year = event.year
                month = event.month
                day = event.day
            }
            is BasicInfoAddEditEvent.OnNameChange -> {
                firstName = event.firstName
                surName = event.surName
                middleName = event.middleName
            }
            is BasicInfoAddEditEvent.OnStatisticsChange -> {
                weight = event.weight
                height = event.height
            }
            is BasicInfoAddEditEvent.OnAdditionalInfoChange -> {
                organDonor = event.organDonor
                bloodType = event.bloodType
                gender = event.gender
                address = event.address
            }

            is BasicInfoAddEditEvent.OnSaveBasicInfoClick -> {
                viewModelScope.launch {
                    when {
                        firstName.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! FIRST NAME field is empty."))
                            return@launch
                        }
                        surName.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! SURNAME field is empty."))
                            return@launch
                        }
//                        middleName.isBlank() -> {
//                            sendUiEvent(UiEvent.ShowSnackbar("Oops! MIDDLE NAME field is empty."))
//                            return@launch
//                        }
                        year.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! YEAR field is empty."))
                            return@launch
                        }
                        month.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! MONTH field is empty."))
                            return@launch
                        }
                        day.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! DAY field is empty."))
                            return@launch
                        }
                        height.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! HEIGHT field is empty."))
                            return@launch
                        }
                        weight.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! WEIGHT field is empty."))
                            return@launch
                        }
                        height == "." -> {
                            sendUiEvent((UiEvent.ShowSnackbar("You need to put a number for HEIGHT.")))
                            return@launch
                        }
                        weight == "." -> {
                            sendUiEvent((UiEvent.ShowSnackbar("You need to put a number for WEIGHT.")))
                            return@launch
                        }
                        organDonor.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! ORGAN DONOR field is empty."))
                            return@launch
                        }
                        bloodType.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! BLOOD TYPE field is empty."))
                            return@launch
                        }
                        gender.isBlank() -> {
                            sendUiEvent(UiEvent.ShowSnackbar("Oops! GENDER field is empty."))
                            return@launch
                        }
//                        address.isBlank() -> {
//                            sendUiEvent(UiEvent.ShowSnackbar("Oops! ADDRESS field is empty."))
//                            return@launch
//                        }
//                        contactPersonName.isBlank() -> {
//                            sendUiEvent(UiEvent.ShowSnackbar("Oops! CONTACT PERSON NAME field is empty."))
//                        }
//                        contactPersonNumber.isBlank() -> {
//                            sendUiEvent(UiEvent.ShowSnackbar("Oops! CONTACT PERSON NUMBER field is empty."))
//                        }
                    }
                    basicinfoRepository.upsertBasicInfo(
                        BasicInfo(
                            age = age,
                            height = height,
                            weight = weight,
                            firstName = firstName,
                            surName = surName,
                            middleName = middleName,
                            year = year,
                            month = month,
                            day = day,
                            organDonor = organDonor,
                            bloodType = bloodType,
                            gender = gender,
                            address = address,
                            id = basicInfo?.id
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
