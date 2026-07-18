package com.wiseSaying.controller

import com.wiseSaying.service.WiseSayingService

class WiseSayingController(private val wiseSayingService: WiseSayingService) {

    fun write() {
        print("명언 : ")
        val content = readlnOrNull() ?: ""
        print("작가 : ")
        val author = readlnOrNull() ?: ""
        val id = wiseSayingService.save(content, author)
        println("${id}번 명언이 등록되었습니다.")
    }

    fun list(input: String) {
        val queryParams = input.substringAfter("목록?", "")
        if (queryParams.isEmpty() || !queryParams.contains("=")) {
            println("번호 / 작가 / 명언")
            println("----------------------")
            wiseSayingService.findAll().asReversed().forEach {
                println("${it.id} / ${it.author} / ${it.content}")
            }
            return
        }

        val params = queryParams.split("&").associate { param ->
            val parts = param.split("=", limit = 2)
            parts[0] to parts.getOrElse(1) { "" }
        }

        val keywordType = params["keywordType"] ?: ""
        val keyword = params["keyword"] ?: ""

        if (keywordType.isNotEmpty() && keyword.isNotEmpty()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
            println("번호 / 작가 / 명언")
            println("----------------------")
            wiseSayingService.findByKeyword(keywordType, keyword).asReversed().forEach {
                println("${it.id} / ${it.author} / ${it.content}")
            }
        }
    }

    fun delete(id: Int) {
        val isDeleted = wiseSayingService.delete(id)
        if (isDeleted) {
            println("${id}번 명언이 삭제되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun modify(id: Int) {
        val found = wiseSayingService.findById(id)
        if (found != null) {
            println("명언(기존) : ${found.content}")
            print("명언 : ")
            val newContent = readlnOrNull() ?: ""
            println("작가(기존) : ${found.author}")
            print("작가 : ")
            val newAuthor = readlnOrNull() ?: ""
            wiseSayingService.update(id, newContent, newAuthor)
            println("${id}번 명언이 수정되었습니다.")
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }
}