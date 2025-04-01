package com.nanit.babybirthday

import androidx.datastore.dataStore
import com.nanit.babybirthday.data.model.BabySerializer
import com.nanit.babybirthday.data.provider.local.BabyDataLocalProvider
import com.nanit.babybirthday.data.repository.BabyRepository
import com.nanit.babybirthday.ui.features.globalviewmodels.BirthdayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single {
        dataStore("baby_birthday_app.json", BabySerializer)
    }

    single {
        BabyDataLocalProvider(get())
    }

    single { BabyRepository(get()) }

    viewModel { BirthdayViewModel(get()) }
}