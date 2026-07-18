package com

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
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

    private fun runWithInput(input: String) {
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)
        
        App().run()
    }

    @Test
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

        runWithInput(input)

        val output = outputStream.toString().replace("\r\n", "\n").trim()

        val expected = """
            == 명언 앱 ==
            명령) 명언 : 작가 : 1번 명언이 등록되었습니다.
            명령) 명언 : 작가 : 2번 명언이 등록되었습니다.
            명령) 번호 / 작가 / 명언
            ----------------------
            2 / 작자미상 / 과거에 집착하지 마라.
            1 / 작자미상 / 현재를 사랑하라.
            명령) 1번 명언이 삭제되었습니다.
            명령) 1번 명언은 존재하지 않습니다.
            명령) 3번 명언은 존재하지 않습니다.
            명령) 명언(기존) : 과거에 집착하지 마라.
            명언 : 작가(기존) : 작자미상
            작가 : 2번 명언이 수정되었습니다.
            명령) 번호 / 작가 / 명언
            ----------------------
            2 / 홍길동 / 현재와 자신을 사랑하라.
            명령) 프로그램을 종료합니다.
        """.trimIndent()

        assertEquals(expected, output)
    }
}
