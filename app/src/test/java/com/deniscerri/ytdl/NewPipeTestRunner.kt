package com.involvex.ytmp3dlp

import org.junit.Test
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem

class NewPipeTestRunner {
    @Test
    fun runTest() {
        val methods = PlaylistInfoItem::class.java.methods
        println("=== METHODS START ===")
        methods.forEach { println(it.name) }
        println("=== METHODS END ===")
    }
}
