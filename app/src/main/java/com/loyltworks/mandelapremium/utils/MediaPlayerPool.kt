package com.loyltworks.mandelapremium.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

class MediaPlayerPool(context: Context, maxStreams: Int) {
    private val context: Context = context.applicationContext

    private val mediaPlayerPool = mutableListOf<MediaPlayer>().also {
        for (i in 0..maxStreams) it += buildPlayer()
    }
    private val playersInUse = mutableListOf<MediaPlayer>()

    private fun buildPlayer() = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { recyclePlayer(it) }
    }

    /**
     * Returns a [MediaPlayer] if one is available,
     * otherwise null.
     */
    private fun requestPlayer(): MediaPlayer? {
        return if (!mediaPlayerPool.isEmpty()) {
            mediaPlayerPool.removeAt(0).also {
                playersInUse += it
            }
        } else null
    }

    private fun recyclePlayer(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
        playersInUse -= mediaPlayer
        mediaPlayerPool += mediaPlayer
    }

    fun playSound(@RawRes rawResId: Int) {
        val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
        val mediaPlayer = requestPlayer() ?: return

        mediaPlayer.run {
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset,
                    assetFileDescriptor.declaredLength)
            prepareAsync()
        }
    }
}