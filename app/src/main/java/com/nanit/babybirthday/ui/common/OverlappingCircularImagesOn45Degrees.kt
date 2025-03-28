package com.nanit.babybirthday.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.nanit.babybirthday.ui.pxToDp
import kotlin.math.sqrt

@Composable
fun OverlappingCircularImagesOn45Degrees(
    image: Painter,
    overlappingImage: Painter,
    modifier: Modifier = Modifier,
    size: Dp = image.intrinsicSize.height.pxToDp(),
    overlaySize: Dp = overlappingImage.intrinsicSize.height.pxToDp(),
    overlayOffsetX: Dp = ((sqrt(2F) - 1) * size) / (2 * sqrt(2F)) + (overlaySize / 2),
    overlayOffsetY: Dp = size - overlayOffsetX + overlaySize
) {
    Box(modifier = modifier) {
        Image(
            painter = image,
            contentDescription = "Base Image",
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
        )

        Image(
            painter = overlappingImage,
            contentDescription = "Overlay Image",
            modifier = Modifier
                .size(overlaySize)
                .offset(x = size - overlayOffsetX, y = size - overlayOffsetY)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
        )
    }
}