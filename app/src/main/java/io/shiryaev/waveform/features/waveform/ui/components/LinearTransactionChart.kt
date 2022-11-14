package io.shiryaev.waveform.features.waveform.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import io.shiryaev.waveform.features.waveform.data.TransactionPerSecond

@Composable
fun LinearTransactionChart(
    modifier: Modifier = Modifier,
    transactionPerSecond: TransactionPerSecond
) {
    if (transactionPerSecond.transactions.isEmpty()) return

    Canvas(modifier = modifier) {
        val totalRecords = transactionPerSecond.transactions.size

        val lineDistance = size.width / (totalRecords + 1)

        val canvasHeight = size.height

        var currentLineDistance = 0F + lineDistance

        transactionPerSecond.transactions.forEachIndexed { index, transactionRate ->
            if (totalRecords >= index + 2) {
                drawLine(
                    start = Offset(
                        x = currentLineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = transactionPerSecond.maxTransaction,
                            currentTransactionRate = transactionRate.transactionPerSecondValue,
                            canvasHeight = canvasHeight
                        )
                    ),
                    end = Offset(
                        x = currentLineDistance + lineDistance,
                        y = calculateYCoordinate(
                            higherTransactionRateValue = transactionPerSecond.maxTransaction,
                            currentTransactionRate = transactionPerSecond.transactions[index + 1]
                                .transactionPerSecondValue,
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