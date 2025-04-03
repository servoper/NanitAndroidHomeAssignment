package com.nanit.babybirthday.ui.common

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanit.babybirthday.data.repository.ShareRepository
import kotlinx.coroutines.launch

private const val TAG = "BirthdayViewModel"

class ShareViewModel(private val repository: ShareRepository) : ViewModel() {

    var state by mutableStateOf(ShareState())
        private set


    fun onEvent(event: ShareUiEvent) {
        when (event) {
            is ShareUiEvent.Share -> prepareScreenshot(event.bitmap)
            is ShareUiEvent.ShareRequested -> state = state.copy(shareRequested = true)
            is ShareUiEvent.ShareFinished -> state =
                state.copy(shareRequested = false, sharableUri = null)
        }
    }

    private fun prepareScreenshot(bitmap: GraphicsLayer) {
        viewModelScope.launch {
            runCatching {
                repository.saveBitmapAndGetUri(bitmap.toImageBitmap().asAndroidBitmap())
            }.onSuccess {
                state = state.copy(sharableUri = it)
            }.onFailure {
                onEvent(ShareUiEvent.ShareFinished)
                Log.d(TAG, "Save Picture Error: ${it.message}")
            }
        }
    }
}

data class ShareState(
    val sharableUri: Uri? = null, val shareRequested: Boolean = false
)

sealed interface ShareUiEvent {
    data class Share(val bitmap: GraphicsLayer) : ShareUiEvent

    object ShareRequested : ShareUiEvent

    object ShareFinished : ShareUiEvent
}