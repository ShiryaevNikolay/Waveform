package io.shiryaev.waveform.features.waveform.data

data class TransactionPerSecond(
    val maxTransaction: Short,
    val transactions: List<TransactionRate>
)

data class TransactionRate(
    val timeStamp: Long,
    val transactionPerSecondValue: Short
)
