package com.nanit.babybirthday.data.provider.local

import androidx.datastore.core.DataStore
import com.nanit.babybirthday.data.model.Baby
import kotlinx.coroutines.flow.Flow

class BabyDataLocalProvider(private val dataStore: DataStore<Baby>) {

    suspend fun getBaby(): Flow<Baby?> = dataStore.data

    suspend fun saveBaby(baby: Baby) {
        dataStore.updateData {
            it.copy(
                name = baby.name, dateOfBirth = baby.dateOfBirth, picture = baby.picture
            )
        }
    }

    suspend fun saveName(name: String) {
        dataStore.updateData { it.copy(name = name) }
    }

    suspend fun saveBirthDate(birthDate: Long) {
        dataStore.updateData { it.copy(dateOfBirth = birthDate) }
    }

    suspend fun savePicture(picture: String) {
        dataStore.updateData { it.copy(picture = picture) }
    }
}