package com

import com.wiseSaying.controller.WiseSayingController

class App {
    val wiseSayingController = WiseSayingController()

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
            }
        }
    }
}