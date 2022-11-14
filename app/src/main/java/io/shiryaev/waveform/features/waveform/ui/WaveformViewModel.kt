package io.shiryaev.waveform.features.waveform.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shiryaev.waveform.features.waveform.data.AudioData
import io.shiryaev.waveform.features.waveform.sources.AudioRecordRepository
import io.shiryaev.waveform.features.waveform.sources.AudioRecorder
import io.shiryaev.waveform.features.waveform.sources.AudioRecorderInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class WaveformViewModel(
    private val waveformModel: WaveformModel = WaveformModel(
        audioRecorderInteractor = AudioRecorderInteractor(
            audioRecorderRepository = AudioRecordRepository()
        )
    )
) : ViewModel(), CoroutineScope by MainScope() {

    // val audioDataFlow = waveformModel.audioDataFlow
    private val _audioDataFlow = MutableStateFlow(AudioData())
    val audioDataFlow = _audioDataFlow.asStateFlow()

    init {
        launch {
            waveformModel.audioDataFlow
                .shareIn(this, started = SharingStarted.Eagerly, replay = 1)
                .collect(_audioDataFlow::emit)
        }
    }

    fun startRecording() {

        Log.i(AudioRecorder.TAG, "(${this::class.simpleName}) Click Start recording")

        viewModelScope.launch {
            Log.i(
                AudioRecorder.TAG,
                "(${this@WaveformViewModel::class.simpleName}) Start recording"
            )

            waveformModel.startRecording()
        }
    }

    fun stopRecording() {

        Log.i(AudioRecorder.TAG, "(${this::class.simpleName})Click Stop recording")

        viewModelScope.launch {

            Log.i(AudioRecorder.TAG, "(${this@WaveformViewModel::class.simpleName}) Stop recording")

            waveformModel.stopRecording()
        }
    }
}