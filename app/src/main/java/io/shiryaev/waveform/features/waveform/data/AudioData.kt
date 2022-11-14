package io.shiryaev.waveform.features.waveform.data

data class AudioData(
    val maxValue: Short = 0,
    val data: List<Short> = emptyList()
)