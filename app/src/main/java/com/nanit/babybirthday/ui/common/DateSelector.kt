package com.nanit.babybirthday.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nanit.babybirthday.R
import com.nanit.babybirthday.ui.toDateString

@Composable
fun DateSelector(
    dateMilliseconds: Long? = null, label: String,
    onDateSelected: (Long) -> Unit
) {

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = dateMilliseconds?.toDateString() ?: "",
        onValueChange = {},
        interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release || it is PressInteraction.Cancel) {
                        showDatePicker = true
                    }
                }
            }
        },
        label = { Text(label) })

    if (showDatePicker) {
        DatePickerModal(onDateSelected = {
            it?.let { onDateSelected(it) }
        }, onDismiss = { showDatePicker = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text(stringResource(R.string.ok))
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(stringResource(R.string.cancel))
        }
    }) {
        DatePicker(state = datePickerState)
    }
}