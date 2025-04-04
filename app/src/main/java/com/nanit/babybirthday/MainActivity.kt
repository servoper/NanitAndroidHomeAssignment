package com.nanit.babybirthday

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ShareCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nanit.babybirthday.ui.common.ShareUiEvent
import com.nanit.babybirthday.ui.common.ShareViewModel
import com.nanit.babybirthday.ui.common.ShareablePage
import com.nanit.babybirthday.ui.features.birthday.BirthdayPage
import com.nanit.babybirthday.ui.features.details.DetailsPage
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayUiEvent
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayViewModel
import com.nanit.babybirthday.ui.theme.NanitBabyBirthdayTheme
import com.nanit.babybirthday.ui.theme.NanitBlueTheme
import com.nanit.babybirthday.ui.theme.NanitGreenTheme
import com.nanit.babybirthday.ui.theme.NanitYellowTheme
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
                                        it is BirthdayUiEvent.GoToNextPage -> {
                                            navController.navigate(
                                                BirthdayDestination((0..2).random())
                                            )
                                        }

                                        else -> viewModel.onEvent(it)
                                    }
                                }, modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    composable<BirthdayDestination> {
                        val theme = when (it.toRoute<BirthdayDestination>().nanitTheme) {
                            0 -> NanitBlueTheme()
                            1 -> NanitYellowTheme()
                            else -> NanitGreenTheme()
                        }
                        val shareableViewModel = getViewModel<ShareViewModel>()

                        shareableViewModel.state.sharableUri?.let {
                            shareImageToOthers(shareableViewModel.state.sharableUri!!)
                            shareableViewModel.onEvent(ShareUiEvent.ShareFinished)
                        }

                        ShareablePage(
                            shareableViewModel.state,
                            shareableViewModel::onEvent,
                            modifier = Modifier.fillMaxSize(),
                            content = {
                                Scaffold(
                                    modifier = Modifier.fillMaxSize()
                                ) { innerPadding ->
                                    BirthdayPage(
                                        theme,
                                        viewModel.state,
                                        shareableViewModel.state.shareRequested,
                                        {
                                            shareableViewModel.onEvent(ShareUiEvent.ShareRequested)
                                        },
                                        viewModel::onEvent,
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                            })

                    }

                }
            }
        }

    }

    fun shareImageToOthers(
        imageUri: Uri,
    ) {
        ShareCompat.IntentBuilder(this).setType("image/*").setChooserTitle("Share screen")
            .addStream(imageUri).startChooser()
    }
}


@Serializable
object DetailsDestination


@Serializable
data class BirthdayDestination(
    val nanitTheme: Int
)