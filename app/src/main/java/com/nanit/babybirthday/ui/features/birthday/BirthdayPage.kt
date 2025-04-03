package com.nanit.babybirthday.ui.features.birthday

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nanit.babybirthday.R
import com.nanit.babybirthday.ui.common.OverlappingCircularImagesOn45Degrees
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayState
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayUiEvent
import com.nanit.babybirthday.ui.theme.AgeDrawableResource
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme
import com.nanit.babybirthday.ui.theme.Typography
import com.nanit.babybirthday.ui.theme.Yellow20
import com.nanit.babybirthday.ui.theme.Yellow40

@Composable
fun BirthdayPage(
    state: BirthdayState, modifier: Modifier = Modifier
) {

    Box(modifier = Modifier.background(Yellow20)) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 140.dp, start = 50.dp, end = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 20.dp, bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(R.string.yout_baby_today_is, state.name).uppercase(),
                    style = Typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(13.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.left_swirls),
                        contentDescription = null,
                    )
                    Image(
                        painter = painterResource(
                            id = (if (state.years > 0)
                                AgeDrawableResource.getDrawable(state.years) else
                                AgeDrawableResource.getDrawable(state.months)).drawable
                        ),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 22.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.right_swirls),
                        contentDescription = null,
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    (if (state.years > 0)
                        pluralStringResource(id = R.plurals.year_old, count = state.years)
                    else
                        pluralStringResource(
                            id = R.plurals.month_old,
                            count = state.months
                        )).uppercase(),
                    style = Typography.titleLarge,
                )
            }

            OverlappingCircularImagesOn45Degrees(
                placeholder = painterResource(id = R.drawable.image_paceholder_yellow),
                overlappingImage = painterResource(id = R.drawable.add_image_yellow),
                selectedImageUri = state.picture,
                borderColor = Yellow40,
            )
        }
        Image(
            painter = painterResource(id = R.drawable.bg_yellow),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )

        Image(
            painter = painterResource(id = R.drawable.nanit),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 110.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NanitBabyBirthdayTheme {
        BirthdayPage(BirthdayState(name = "Christiano Ronaldo", years = 2, months = 0))
    }
}