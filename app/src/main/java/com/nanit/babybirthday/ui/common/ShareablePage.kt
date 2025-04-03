package com.nanit.babybirthday.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer

@Composable
fun ShareablePage(
    state: ShareState,
    events: (ShareUiEvent) -> Unit,
    modifier: Modifier,
    content: @Composable () -> Unit
) {

    if (state.shareRequested) {
        val graphicsLayer = rememberGraphicsLayer()

        Box(
            modifier = modifier.drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }

                events(ShareUiEvent.Share(graphicsLayer))

                drawLayer(graphicsLayer)
            }) {
            content.invoke()
        }
    } else {
        content.invoke()
    }
}