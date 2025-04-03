package com.nanit.babybirthday

import com.nanit.babybirthday.data.provider.local.BabyDataLocalProvider
import com.nanit.babybirthday.data.repository.BabyRepository
import com.nanit.babybirthday.data.repository.ShareRepository
import com.nanit.babybirthday.ui.common.ShareViewModel
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single {
        BabyDataLocalProvider(androidContext())
    }

    single { BabyRepository(get()) }

    single { ShareRepository(get()) }

    viewModel { BirthdayViewModel(get()) }

    viewModel { ShareViewModel(get()) }
}