package com.wiseSaying.controller

import com.wiseSaying.service.WiseSayingService

class WiseSayingController {
    val wiseSayingService = WiseSayingService()

    fun write() {
        print("명언 : ")
        val content = readlnOrNull() ?: ""
        print("작가 : ")
        val author = readlnOrNull() ?: ""
        val id = wiseSayingService.add(content, author)
        println("${id}번 명언이 등록되었습니다.")
    }
}