package com.wiseSaying.repository

import com.wiseSaying.entity.WiseSaying

class WiseSayingRepository {
    val wiseSayings = mutableListOf<WiseSaying>()
    var count = 0

    fun save(content: String, author: String): Int {
        ++count
        wiseSayings.add(WiseSaying(count, content, author))
        return count
    }

    fun findAll(): List<WiseSaying> = wiseSayings

    fun findById(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    fun findByKeyword(keywordType: String, keyword: String): List<WiseSaying> {
        return when (keywordType) {
            "content" -> wiseSayings.filter { it.content.contains(keyword) }
            "author" -> wiseSayings.filter { it.author.contains(keyword) }
            else -> emptyList()
        }
    }

    fun deleteById(id: Int): Boolean {
        return wiseSayings.removeIf { it.id == id }
    }

    fun update(id: Int, content: String, author: String) {
        val index = wiseSayings.indexOfFirst { it.id == id }
        if (index != -1) {
            wiseSayings[index] = WiseSaying(id, content, author)
        }
    }
}