package com.nanit.babybirthday.ui

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }


@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun Context.createImageUri(): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, ".jpg", getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
    )
    return FileProvider.getUriForFile(
        this, "com.nanit.babybirthday.provider",
        image
    )
}

fun Long.toDateString(): String = SimpleDateFormat.getDateInstance().format(Date(this))

fun String.toMillisecondsDate(): Long? = SimpleDateFormat.getDateInstance().parse(this)?.time