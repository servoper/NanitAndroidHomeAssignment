package com.nanit.babybirthday.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ShareRepository(private val context: Context) {

    suspend fun saveBitmapAndGetUri(bitmap: Bitmap): Uri = withContext(Dispatchers.IO) {
        val filename = "baby_birthday_${System.currentTimeMillis()}.jpg" // Use a unique filename

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 (Q) and above, use MediaStore
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + "/BabyBirthday"
                ) // Subdirectory
                put(MediaStore.Images.Media.IS_PENDING, 1) // While writing
            }

            val collection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val uri = context.contentResolver.insert(collection, values)!!

            uri.also { imageUri ->
                context.contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0) // Done writing
                context.contentResolver.update(imageUri, values, null, null)
            }
        } else {
            // For Android versions below Q, use FileOutputStream and FileProvider
            val directory = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "BabyBirthday"
            )
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, filename)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            FileProvider.getUriForFile(context, "com.nanit.babybirthday.provider", file)
        }
    }
}