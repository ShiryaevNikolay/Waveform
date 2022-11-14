package io.shiryaev.waveform.features.waveform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.shiryaev.waveform.features.waveform.data.AudioData
import kotlin.math.abs

@Composable
fun AudioAmplitude(
    modifier: Modifier = Modifier,
    audioData: AudioData
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyRow {
            items(audioData.data) { audioValue ->
                Box(
                    modifier = Modifier
                        .height(abs(audioValue.toFloat()).dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}