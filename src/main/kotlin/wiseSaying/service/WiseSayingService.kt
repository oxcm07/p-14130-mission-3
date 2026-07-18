package com.wiseSaying.service

import com.wiseSaying.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun save(content: String, author: String) = wiseSayingRepository.save(content, author)

    fun findAll() = wiseSayingRepository.findAll()

    fun findById(id: Int) = wiseSayingRepository.findById(id)

    fun delete(id: Int): Boolean {
        return wiseSayingRepository.deleteById(id)
    }

    fun update(id: Int, content: String, author: String) {
        wiseSayingRepository.update(id, content, author)
    }
}