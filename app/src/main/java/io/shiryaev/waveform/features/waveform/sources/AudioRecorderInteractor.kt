package io.shiryaev.waveform.features.waveform.sources

import android.util.Log
import io.shiryaev.waveform.features.waveform.data.AudioData
import io.shiryaev.waveform.features.waveform.sources.exceptions.cancelChildrenAndJoin
import io.shiryaev.waveform.features.waveform.sources.exceptions.contextJob
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AudioRecorderInteractor(
    val audioRecorderRepository: AudioRecordRepository
) : CoroutineScope {

    override val coroutineContext =
        Dispatchers.IO + SupervisorJob() + CoroutineName("AudioRecorderInteractor")

    private val _audioBufferFlow = MutableSharedFlow<ShortArray>()
    val audioBufferFlow = _audioBufferFlow.asSharedFlow()

    init {
        launch {
            audioRecorderRepository.audioBufferFlow
                .shareIn(this, started = SharingStarted.Eagerly, replay = 1)
                .collect(_audioBufferFlow::emit)
        }
    }

    private val hasRunningJob: Boolean
        get() = contextJob.children.any(Job::isActive)

    suspend fun startRecording() {
        chancelRunningJob()
        withContext(coroutineContext) {

            Log.i(
                AudioRecorder.TAG,
                "(${this@AudioRecorderInteractor::class.simpleName}) Start recording"
            )
            audioRecorderRepository.startRecording()
        }
    }

    suspend fun stopRecording() {
        Log.i(AudioRecorder.TAG, "(${this::class.simpleName}) Stop recording")
        audioRecorderRepository.stopRecording()
    }

    private suspend fun chancelRunningJob() = withContext(coroutineContext) {
        if (hasRunningJob) contextJob.cancelChildrenAndJoin()
    }
}