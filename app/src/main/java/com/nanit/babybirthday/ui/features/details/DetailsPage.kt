package com.nanit.babybirthday.ui.features.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nanit.babybirthday.R
import com.nanit.babybirthday.ui.common.DateSelector
import com.nanit.babybirthday.ui.common.OverlappingCircularImagesOn45Degrees
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayState
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayUiEvent
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme

@Composable
fun DetailsPage(
    state: BirthdayState, events: (BirthdayUiEvent) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.app_name), fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        NameInput(state.name, onNameChanged = { events(BirthdayUiEvent.UpdateName(it)) })

        Spacer(modifier = Modifier.height(16.dp))

        DateSelector(
            state.dateOfBirth,
            stringResource(R.string.age),
            onDateSelected = { events(BirthdayUiEvent.UpdateBirthDate(it)) })

        Spacer(modifier = Modifier.height(16.dp))

        OverlappingCircularImagesOn45Degrees(
            placeholder = painterResource(id = R.drawable.image_paceholder_yellow),
            overlappingImage = painterResource(id = R.drawable.add_image_yellow),
            selectedImageUri = state.picture,
            onImageSelected = { events(BirthdayUiEvent.UpdatePicture(it)) },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // TODO show next screen
        }) {
            Text(stringResource(R.string.show_birthday_screen))
        }
    }
}

@Composable
fun NameInput(name: String? = null, onNameChanged: (String) -> Unit) {
    OutlinedTextField(
        value = name ?: "",
        onValueChange = onNameChanged,
        label = { Text(stringResource(R.string.name)) })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NanitBabyBirthdayTheme {
        DetailsPage(BirthdayState(), {})
    }
}