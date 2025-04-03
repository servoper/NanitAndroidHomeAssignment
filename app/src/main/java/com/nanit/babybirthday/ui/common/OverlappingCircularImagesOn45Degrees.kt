package com.nanit.babybirthday.ui.common

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import coil3.compose.AsyncImage
import com.nanit.babybirthday.R
import com.nanit.babybirthday.ui.createImageUri
import com.nanit.babybirthday.ui.dpToPx
import com.nanit.babybirthday.ui.pxToDp
import kotlin.math.sqrt

@Composable
fun OverlappingCircularImagesOn45Degrees(
    placeholder: Painter,
    overlappingImage: Painter,
    selectedImageUri: Uri? = null,
    onImageSelected: ((Uri) -> Unit)? = null,
    borderColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = placeholder.intrinsicSize.height.pxToDp(),
    overlaySize: Dp = overlappingImage.intrinsicSize.height.pxToDp(),
    overlayOffsetX: Dp = ((sqrt(2F) - 1) * size) / (2 * sqrt(2F)) + (overlaySize / 2),
    overlayOffsetY: Dp = size - overlayOffsetX + overlaySize
) {
    var choosePicture by remember { mutableStateOf(false) }

    if (choosePicture && onImageSelected != null) {
        CombinedImagePickerContent {
            choosePicture = false
            onImageSelected(it)
        }
    }

    val borderRadius = (size / 2).dpToPx()

    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) {
                choosePicture = true
            }
            .drawBehind {
                drawCircle(
                    color = borderColor,
                    radius = borderRadius,
                )
            }) {
        AsyncImage(
            model = selectedImageUri,
            placeholder = placeholder,
            error = placeholder,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.baby_picture),
            modifier = Modifier
                .size(size)
                .padding(7.dp)
                .clip(CircleShape)
        )

        if (onImageSelected != null) {
            Image(
                painter = overlappingImage,
                contentDescription = stringResource(R.string.choose_image),
                modifier = Modifier
                    .size(overlaySize)
                    .offset(x = size - overlayOffsetX, y = size - overlayOffsetY)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
        }
    }
}

@Composable
fun CombinedImagePickerContent(
    onImageSelected: (uri: Uri) -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }
    var takePicture by remember { mutableStateOf(false) }

    val pickMediaLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                onImageSelected(uri)
            }
        }

    if (takePicture) {
        TakePicture(onImageCaptured = {
            onImageSelected(it)
            takePicture = false
        })
    }

    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnBackPress = true, dismissOnClickOutside = true
            ),
            onDismissRequest = { showDialog = false },
            title = { Text("Select Image Source") },
            confirmButton = {
                Button(onClick = {
                    takePicture = true
                    showDialog = false
                }) {
                    Text("Take Photo")
                }
            },
            dismissButton = {
                Button(onClick = {
                    pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    showDialog = false
                }) {
                    Text("Pick from Gallery")
                }
            })
    }
}

@Composable
fun TakePicture(onImageCaptured: (uri: Uri) -> Unit) {
    var takePicture by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            takePicture = true
        }
    }

    if (ContextCompat.checkSelfPermission(
            LocalContext.current, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        takePicture = true
    } else {
        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    val uriCameraPhotoUri = LocalContext.current.createImageUri()
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                onImageCaptured(uriCameraPhotoUri)
            }
            takePicture = false
        }

    if (takePicture) {

        LaunchedEffect(Unit) {
            cameraLauncher.launch(uriCameraPhotoUri)
        }
    }
}