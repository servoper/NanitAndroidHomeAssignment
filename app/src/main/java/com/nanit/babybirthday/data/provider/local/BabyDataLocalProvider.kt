package com.nanit.babybirthday.data.provider.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.nanit.babybirthday.data.model.Baby
import com.nanit.babybirthday.data.model.BabySerializer
import kotlinx.coroutines.flow.Flow

class BabyDataLocalProvider(private val context: Context) {
    private val Context.dataStore: DataStore<Baby> by dataStore(
        fileName = "baby_birthday_app.json",
        serializer = BabySerializer
    )

    suspend fun getBaby(): Flow<Baby?> = context.dataStore.data

    suspend fun saveBaby(baby: Baby) {
        context.dataStore.updateData {
            it.copy(
                name = baby.name, dateOfBirth = baby.dateOfBirth, picture = baby.picture
            )
        }
    }

    suspend fun saveName(name: String) {
        context.dataStore.updateData { it.copy(name = name) }
    }

    suspend fun saveBirthDate(birthDate: Long) {
        context.dataStore.updateData { it.copy(dateOfBirth = birthDate) }
    }

    suspend fun savePicture(picture: String) {
        context.dataStore.updateData { it.copy(picture = picture) }
    }
}