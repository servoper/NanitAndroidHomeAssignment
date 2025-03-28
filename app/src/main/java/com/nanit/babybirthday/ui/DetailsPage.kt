package com.nanit.babybirthday.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme

@Composable
fun DetailsPage(modifier: Modifier = Modifier) {
    var showToast by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.app_name), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        NameInput()
        Spacer(modifier = Modifier.height(16.dp))
        DateSelector(stringResource(R.string.age))
        Spacer(modifier = Modifier.height(16.dp))
        OverlappingCircularImagesOn45Degrees(
            image = painterResource(id = R.drawable.image_paceholder_yellow),
            overlappingImage = painterResource(id = R.drawable.add_image_yellow),
            modifier = Modifier
                .padding(16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, indication = null
                ) {
                    //TODO add image picker
                })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // TODO show next screen
        }) {
            Text(stringResource(R.string.show_birthday_screen))
        }
    }
}

@Composable
fun NameInput() {
    var nameState by remember {
        mutableStateOf("")
    }
    OutlinedTextField(value = nameState, onValueChange = {
        nameState = it
    }, label = { Text(stringResource(R.string.name)) })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NanitBabyBirthdayTheme {
        DetailsPage()
    }
}