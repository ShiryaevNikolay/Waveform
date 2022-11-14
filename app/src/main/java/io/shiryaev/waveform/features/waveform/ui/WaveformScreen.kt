package io.shiryaev.waveform.features.waveform.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.shiryaev.waveform.features.waveform.ui.components.Amplitude
import io.shiryaev.waveform.features.waveform.ui.components.AudioAmplitude

@Composable
@Preview(showBackground = true)
fun WaveformScreen(
    viewModel: WaveformViewModel = viewModel()
) {

    val audioData by viewModel.audioDataFlow.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Amplitude(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                audioData = audioData
            )
            // AudioAmplitude(
            //     modifier = Modifier
            //         .fillMaxSize()
            //         .weight(1f),
            //     audioData = audioData
            // )
            Button(
                // enabled = isRecording.not(),
                onClick = { viewModel.startRecording() }
            ) {
                Text(
                    text = "Start record"
                )
            }
            Button(
                // enabled = isRecording,
                onClick = { viewModel.stopRecording() }
            ) {
                Text(text = "Stop record")
            }
        }
    }
}