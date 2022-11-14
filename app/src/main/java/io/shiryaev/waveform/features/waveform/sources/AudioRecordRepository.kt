package io.shiryaev.waveform.features.waveform.sources

import android.util.Log
import io.shiryaev.waveform.features.waveform.sources.exceptions.cancelChildrenAndJoin
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class AudioRecordRepository(
    private val audioRecorder: AudioRecorder = AudioRecorder()
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val _audioBufferFlow = MutableSharedFlow<ShortArray>()
    val audioBufferFlow = _audioBufferFlow.asSharedFlow()

    init {
        launch {
            audioRecorder.audioBufferFlow
                .shareIn(this, started = SharingStarted.Eagerly, replay = 1)
                .collect(_audioBufferFlow::emit)
        }
    }

    suspend fun startRecording() = withContext(coroutineContext) {

        Log.i(
            AudioRecorder.TAG,
            "(${this@AudioRecordRepository::class.simpleName}) Start recording"
        )

        audioRecorder.startRecording()
    }

    suspend fun stopRecording() {

        Log.i(
            AudioRecorder.TAG,
            "(${this@AudioRecordRepository::class.simpleName}) Start recording"
        )

        audioRecorder.stopAudioRecording()
    }
}