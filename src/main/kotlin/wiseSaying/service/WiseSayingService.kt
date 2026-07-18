package com.wiseSaying.service

import com.wiseSaying.entity.WiseSaying
import com.wiseSaying.repository.WiseSayingRepository

class WiseSayingService(private val wiseSayingRepository: WiseSayingRepository) {

    fun save(content: String, author: String) = wiseSayingRepository.save(content, author)

    fun findAll(page: Int, keywordType: String?, keyword: String?): Pair<List<WiseSaying>, Int> {
        var all = wiseSayingRepository.findAll()

        if (keywordType != null && keyword != null) {
            all = when (keywordType) {
                "content" -> all.filter { saying -> saying.content.contains(keyword) }
                "author" -> all.filter { saying -> saying.author.contains(keyword) }
                else -> all
            }
        }

        val chunks = all.asReversed().chunked(5)
        val totalPages = maxOf(1, chunks.size)
        val safePage = page.coerceIn(1, totalPages)
        val pagedList = chunks.getOrNull(safePage - 1) ?: emptyList()
        return Pair(pagedList, totalPages)
    }

    fun findById(id: Int) = wiseSayingRepository.findById(id)

    fun delete(id: Int): Boolean {
        return wiseSayingRepository.deleteById(id)
    }

    fun update(id: Int, content: String, author: String) {
        wiseSayingRepository.update(id, content, author)
    }
}