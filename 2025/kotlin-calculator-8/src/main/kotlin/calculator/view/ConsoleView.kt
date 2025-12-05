package calculator.view

import camp.nextstep.edu.missionutils.Console.readLine

object ConsoleView {

    fun readInput(): String {
        println("덧셈할 문자열을 입력해 주세요.")
        return readLine()
    }

    fun printResult(result: Int) {
        println("결과 : $result")
    }
}