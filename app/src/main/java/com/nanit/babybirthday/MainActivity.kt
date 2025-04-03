package com.nanit.babybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nanit.babybirthday.ui.features.birthday.BirthdayPage
import com.nanit.babybirthday.ui.features.details.DetailsPage
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayUiEvent
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayViewModel
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NanitBabyBirthdayTheme {
                val viewModel = getViewModel<BirthdayViewModel>()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = DetailsDestination) {
                    composable<DetailsDestination> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            DetailsPage(
                                viewModel.state, {
                                    when {
                                        it is BirthdayUiEvent.GoToNextPage -> navController.navigate(
                                            BirthdayDestination
                                        )

                                        else -> viewModel.onEvent(it)
                                    }
                                }, modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    composable<BirthdayDestination> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            BirthdayPage(
                                viewModel.state,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                }
            }
        }

    }
}


@Serializable
object DetailsDestination


@Serializable
object BirthdayDestination