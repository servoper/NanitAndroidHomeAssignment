package com.nanit.babybirthday.ui.features.globalviewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanit.babybirthday.data.repository.BabyRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "BirthdayViewModel"

@OptIn(FlowPreview::class)
class BirthdayViewModel(private val repository: BabyRepository) : ViewModel() {

    var state by mutableStateOf(BirthdayState())
        private set

    init {
        snapshotFlow { state.name }.debounce(1000).map {
            saveName(it)
        }.launchIn(viewModelScope)

        getBaby()
    }

    fun onEvent(event: BirthdayUiEvent) {
        when (event) {
            is BirthdayUiEvent.UpdateName -> onNameChange(event.name)
            is BirthdayUiEvent.UpdateBirthDate -> saveBirthDate(event.birthDate)
            is BirthdayUiEvent.UpdatePicture -> setPicture(event.tmpPicture)
            is BirthdayUiEvent.GetBaby -> getBaby()
            else -> Unit
        }
    }

    private fun getBaby() {
        viewModelScope.launch {
            repository.getBaby().collect {
                var years = 0
                var months = 0

                it?.dateOfBirth?.let { time ->
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis -= time
                    years = calendar.get(Calendar.YEAR) - 1970
                    months = calendar.get(Calendar.MONTH)
                }

                if (it != null) state = state.copy(
                    name = it.name,
                    dateOfBirth = it.dateOfBirth,
                    years = years,
                    months = months,
                    picture = it.picture?.toUri()
                )
            }
        }
    }

    private fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    private fun saveName(name: String) {
        viewModelScope.launch {
            runCatching {
                repository.saveName(name)
            }.onFailure {
                Log.d(TAG, "Save Name Error: ${it.message}")
            }
        }
    }

    private fun saveBirthDate(birthDate: Long) {
        viewModelScope.launch {
            runCatching {
                repository.saveBirthDate(birthDate)
            }.onFailure {
                Log.d(TAG, "Save BirthDate Error: ${it.message}")
            }
        }
    }

    private fun setPicture(tmpPicture: Uri) {
        viewModelScope.launch {
            runCatching {
                repository.savePicture(tmpPicture)
            }.onFailure {
                Log.d(TAG, "Set Picture Error: ${it.message}")
            }
        }
    }
}

data class BirthdayState(
    val name: String = "",
    val dateOfBirth: Long? = null,
    val years: Int = 0,
    val months: Int = 0,
    val picture: Uri? = null
)

sealed interface BirthdayUiEvent {
    data class UpdateName(val name: String) : BirthdayUiEvent
    data class UpdateBirthDate(val birthDate: Long) : BirthdayUiEvent
    data class UpdatePicture(val tmpPicture: Uri) : BirthdayUiEvent
    data object GetBaby : BirthdayUiEvent
    data object GoToNextPage : BirthdayUiEvent
}