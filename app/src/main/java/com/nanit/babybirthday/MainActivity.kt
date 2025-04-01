package com.nanit.babybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nanit.babybirthday.ui.features.details.DetailsPage
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayViewModel
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NanitBabyBirthdayTheme {
                val viewModel = getViewModel<BirthdayViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailsPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}