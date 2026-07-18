package com.wiseSaying.service

import com.wiseSaying.repository.WiseSayingRepository

class WiseSayingService {
    val wiseSayingRepository = WiseSayingRepository()

    fun add(content: String, author: String) = wiseSayingRepository.add(content, author)

    fun findAll() = wiseSayingRepository.findAll()
}