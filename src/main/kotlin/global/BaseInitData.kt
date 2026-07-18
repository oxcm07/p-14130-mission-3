package com.global

import com.wiseSaying.repository.WiseSayingRepository

class BaseInitData(private val wiseSayingRepository: WiseSayingRepository) {
    fun initialize() {
        for (i in 1..10) {
            wiseSayingRepository.save("명언 $i", "작자미상 $i")
        }
    }
}