package com

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class AppTest {
    private val standardIn: InputStream = System.`in`
    private val standardOut: PrintStream = System.out
    private lateinit var outputStream: ByteArrayOutputStream

    @BeforeEach
    fun setUp() {
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setIn(standardIn)
        System.setOut(standardOut)
    }

    private fun runWithInput(input: String, loadInitData: Boolean = false) {
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)
        
        App(loadInitData = loadInitData).run()
    }

    @Test
    @DisplayName("CRUD")
    fun t1() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록
            삭제?id=1
            삭제?id=1
            수정?id=3
            수정?id=2
            현재와 자신을 사랑하라.
            홍길동
            목록
            종료
        """.trimIndent()

        runWithInput(input, loadInitData = false)

        val output = outputStream.toString().replace("\r\n", "\n").trim()

        val expected = """
            == 명언 앱 ==
            명령) 명언 : 작가 : 1번 명언이 등록되었습니다.
            명령) 명언 : 작가 : 2번 명언이 등록되었습니다.
            명령) 번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
            1 / 작자미상 / 현재를 사랑하라.
            ----------------------
            페이지 : [1]
            명령) 1번 명언이 삭제되었습니다.
            명령) 1번 명언은 존재하지 않습니다.
            명령) 3번 명언은 존재하지 않습니다.
            명령) 명언(기존) : 과거에 집착하지 마라.
            명언 : 작가(기존) : 작자미상
            작가 : 2번 명언이 수정되었습니다.
            명령) 번호 / 작가 / 명언
            ----------------------
            2 / 홍길동 / 현재와 자신을 사랑하라.
            ----------------------
            페이지 : [1]
            명령) 프로그램을 종료합니다.
        """.trimIndent()

        assertEquals(expected, output)
    }

    @Test
    @DisplayName("검색")
    fun t2() {
        val input = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거
            목록?keywordType=author&keyword=작자
            종료
        """.trimIndent()

        runWithInput(input, loadInitData = false)

        val output = outputStream.toString().replace("\r\n", "\n").trim()

        val expected = """
            == 명언 앱 ==
            명령) 명언 : 작가 : 1번 명언이 등록되었습니다.
            명령) 명언 : 작가 : 2번 명언이 등록되었습니다.
            명령) ----------------------
            검색타입 : content
            검색어 : 과거
            ----------------------
            번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
            ----------------------
            페이지 : [1]
            명령) ----------------------
            검색타입 : author
            검색어 : 작자
            ----------------------
            번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
            1 / 작자미상 / 현재를 사랑하라.
            ----------------------
            페이지 : [1]
            명령) 프로그램을 종료합니다.
        """.trimIndent()

        assertEquals(expected, output)
    }

    @Test
    @DisplayName("페이징")
    fun t3() {
        val input = """
            목록
            목록?page=2
            종료
        """.trimIndent()

        runWithInput(input, loadInitData = true)

        val output = outputStream.toString().replace("\r\n", "\n").trim()

        val expected = """
            == 명언 앱 ==
            명령) 번호 / 작가 / 명언
            ----------------------
            10 / 작자미상 10 / 명언 10
            9 / 작자미상 9 / 명언 9
            8 / 작자미상 8 / 명언 8
            7 / 작자미상 7 / 명언 7
            6 / 작자미상 6 / 명언 6
            ----------------------
            페이지 : [1] / 2
            명령) 번호 / 작가 / 명언
            ----------------------
            5 / 작자미상 5 / 명언 5
            4 / 작자미상 4 / 명언 4
            3 / 작자미상 3 / 명언 3
            2 / 작자미상 2 / 명언 2
            1 / 작자미상 1 / 명언 1
            ----------------------
            페이지 : 1 / [2]
            명령) 프로그램을 종료합니다.
        """.trimIndent()

        assertEquals(expected, output)
    }
}