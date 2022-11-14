package io.shiryaev.waveform.features.waveform.ui

import android.util.Log
import io.shiryaev.waveform.features.waveform.data.AudioData
import io.shiryaev.waveform.features.waveform.sources.AudioRecorder
import io.shiryaev.waveform.features.waveform.sources.AudioRecorderInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WaveformModel(
    private val audioRecorderInteractor: AudioRecorderInteractor
) : CoroutineScope by MainScope() {

    private val dataFlow = MutableStateFlow(AudioData())
    val audioDataFlow get() = dataFlow.asStateFlow()

    init {
        launch {
            audioRecorderInteractor.audioBufferFlow
                .shareIn(this, started = SharingStarted.Eagerly, replay = 1)
                .collect { buffer ->
                    Log.i(AudioRecorder.TAG, buffer.map { it.toString() }.toString())

                    dataFlow.update { currentAudioData ->
                        currentAudioData.copy(
                            maxValue = currentAudioData.getNewMaxValue(buffer),
                            data = currentAudioData.data + buffer.toList()
                        )
                    }
                }
        }
    }

    suspend fun startRecording() {

        Log.i(AudioRecorder.TAG, "(${this@WaveformModel::class.simpleName}) Start recording")

        audioRecorderInteractor.startRecording()
    }

    suspend fun stopRecording() {

        Log.i(AudioRecorder.TAG, "(${this@WaveformModel::class.simpleName}) Stop recording")

        audioRecorderInteractor.stopRecording()
    }

    private fun AudioData.getNewMaxValue(data: ShortArray): Short {
        val maxValueOfData = data.maxOrNull() ?: return maxValue
        return maxValueOfData.takeIf { it > maxValue } ?: maxValue
    }
}