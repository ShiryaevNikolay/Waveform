package io.shiryaev.waveform.features.waveform.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

suspend inline fun <T> retry(attempts: Int, delay: Long = 0, block: (attempt: Int) -> T): T {
    check(attempts > 0)
    for (attempt in 1..attempts) {
        try {
            return block(attempt)
        } catch (error: CancellationException) {
            throw error
        } catch (error: Throwable) {
            if (attempt == attempts) throw error
        }
        if (delay > 0) delay(delay)
    }
    throw IllegalStateException()
}