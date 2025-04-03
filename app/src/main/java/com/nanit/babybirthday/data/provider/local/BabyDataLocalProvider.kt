package com.nanit.babybirthday.data.provider.local

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.nanit.babybirthday.data.model.Baby
import com.nanit.babybirthday.data.model.BabySerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

private const val BABY_IMAGE_FILE_NAME = "my_baby.jpg"

class BabyDataLocalProvider(private val context: Context) {
    private val Context.dataStore: DataStore<Baby> by dataStore(
        fileName = "baby_birthday_app.json", serializer = BabySerializer
    )

    fun getBaby(): Flow<Baby?> = context.dataStore.data

    suspend fun saveName(name: String) {
        context.dataStore.updateData { it.copy(name = name) }
    }

    suspend fun saveBirthDate(birthDate: Long) {
        context.dataStore.updateData { it.copy(dateOfBirth = birthDate) }
    }

    suspend fun savePicture(tmpPicture: Uri) {
        val file = File(context.filesDir, tmpPicture.lastPathSegment ?: BABY_IMAGE_FILE_NAME)

        withContext(Dispatchers.IO) {
            val pictureBites = context.contentResolver.openInputStream(tmpPicture)?.use {
                it.readBytes()
            }
            FileOutputStream(file).use {
                it.write(pictureBites)
            }
        }

        savePicture(file.absolutePath)
    }

    private suspend fun savePicture(picture: String) {
        context.dataStore.updateData { it.copy(picture = picture) }
    }
}