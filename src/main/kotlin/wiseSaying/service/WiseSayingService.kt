package com.wiseSaying.service

import com.wiseSaying.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun save(content: String, author: String) = wiseSayingRepository.save(content, author)

    fun findAll() = wiseSayingRepository.findAll()

    fun delete(id: Int): Boolean {
        return wiseSayingRepository.deleteById(id)
    }
}