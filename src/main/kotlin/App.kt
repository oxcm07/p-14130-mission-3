package com

import com.global.BaseInitData
import com.wiseSaying.controller.WiseSayingController
import com.wiseSaying.repository.WiseSayingRepository
import com.wiseSaying.service.WiseSayingService

class App(private val loadInitData: Boolean = true) {
    private val wiseSayingRepository = WiseSayingRepository()
    private val wiseSayingService = WiseSayingService(wiseSayingRepository)
    private val wiseSayingController = WiseSayingController(wiseSayingService)

    init {
        if (loadInitData) {
            BaseInitData(wiseSayingRepository).initialize()
        }
    }

    fun run() {
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val input = readlnOrNull() ?: ""

            if (input == "종료") {
                println("프로그램을 종료합니다.")
                break
            }

            when {
                input == "등록" -> wiseSayingController.write()
                input.startsWith("목록") -> wiseSayingController.list(input)

                input.startsWith("삭제") -> wiseSayingController.delete(input)
                input.startsWith("수정") -> wiseSayingController.modify(input)

                else -> println("존재하지 않는 명령어입니다.")
            }
        }
    }
}