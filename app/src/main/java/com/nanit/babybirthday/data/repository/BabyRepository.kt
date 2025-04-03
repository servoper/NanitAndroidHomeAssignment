package com.nanit.babybirthday.data.repository

import android.net.Uri
import com.nanit.babybirthday.data.model.Baby
import com.nanit.babybirthday.data.provider.local.BabyDataLocalProvider
import kotlinx.coroutines.flow.Flow

class BabyRepository(private val babyDataLocalProvider: BabyDataLocalProvider) {

    suspend fun saveName(name: String) {
        babyDataLocalProvider.saveName(name)
    }

    suspend fun saveBirthDate(birthDate: Long) {
        babyDataLocalProvider.saveBirthDate(birthDate)
    }

    suspend fun savePicture(tmpUri: Uri) {
        babyDataLocalProvider.savePicture(tmpUri)
    }

    fun getBaby(): Flow<Baby?> = babyDataLocalProvider.getBaby()
}