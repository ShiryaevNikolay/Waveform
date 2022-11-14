package io.shiryaev.waveform.features.waveform.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import io.shiryaev.waveform.features.waveform.data.AudioData

@Composable
@Preview(showBackground = true)
fun Amplitude(
    modifier: Modifier = Modifier,
    audioData: AudioData = AudioData()
) {
    // Box(
    //     modifier = modifier,
    //     contentAlignment = Alignment.Center
    // ) {
    //     Text(text = audioData.maxValue.toString())
    // }

    // ====================================
    if (audioData.data.isEmpty()) return

    Canvas(modifier = modifier) {
        val totalDistance = audioData.data.size

        val lineDistance = size.width / (totalDistance + 1)

        val canvasHeight = size.height

        var currentLineDistance = 0F + lineDistance

        audioData.data.forEachIndexed { index, audioValue ->
            if (totalDistance >= index + 2) {
                drawLine(
                    start = Offset(
                        x = currentLineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = audioData.maxValue,
                            currentTransactionRate = audioValue,
                            canvasHeight = canvasHeight
                        )
                    ),
                    end = Offset(
                        x = currentLineDistance + lineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = audioData.maxValue,
                            currentTransactionRate = audioData.data[index + 1],
                            canvasHeight = canvasHeight
                        )
                    ),
                    color = Color(40, 193, 218),
                    strokeWidth = Stroke.DefaultMiter
                )
            }
            currentLineDistance += lineDistance
        }
    }
}

private fun calculateYCoordinate(
    higherTransactionRateValue: Short,
    currentTransactionRate: Short,
    canvasHeight: Float
): Float {
    val maxAndCurrentValueDifference = (higherTransactionRateValue - currentTransactionRate)
        .toFloat()
    val relativePercentageOfScreen = canvasHeight / higherTransactionRateValue
    return maxAndCurrentValueDifference * relativePercentageOfScreen
}
