package com.nanit.babybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.nanit.babybirthday.ui.DetailsPage
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NanitBabyBirthdayTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailsPage(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}