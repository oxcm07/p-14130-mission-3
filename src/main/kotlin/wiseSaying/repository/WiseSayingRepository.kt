package com.wiseSaying.repository

import com.wiseSaying.entity.WiseSaying

class WiseSayingRepository {
    val wiseSayings = mutableListOf<WiseSaying>()
    var count = 0

    fun add(content: String, author: String): Int {
        ++count
        wiseSayings.add(WiseSaying(count, content, author))
        return count
    }
}