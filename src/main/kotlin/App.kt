package com

import com.wiseSaying.controller.WiseSayingController
import com.wiseSaying.repository.WiseSayingRepository
import com.wiseSaying.service.WiseSayingService

class App {
    val wiseSayingRepository = WiseSayingRepository()
    val wiseSayingService = WiseSayingService(wiseSayingRepository)
    val wiseSayingController = WiseSayingController(wiseSayingService)

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
                input == "목록" -> wiseSayingController.list()

                input.startsWith("삭제?") -> {
                    val id = input.substringAfter("id=").toIntOrNull()
                    if (id != null) wiseSayingController.delete(id) else println("올바른 id를 입력해주세요.")
                }

                input.startsWith("수정?") -> {
                    val id = input.substringAfter("id=").toIntOrNull()
                    if (id != null) wiseSayingController.modify(id) else println("올바른 id를 입력해주세요.")
                }

                else -> println("존재하지 않는 명령어입니다.")
            }
        }
    }
}