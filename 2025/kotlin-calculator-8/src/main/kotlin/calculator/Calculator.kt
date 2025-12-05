package calculator

import calculator.domain.StringCalculator
import calculator.view.ConsoleView

class Calculator {
    fun run() {
        val input = ConsoleView.readInput()
        val result = StringCalculator.calculate(input)
        ConsoleView.printResult(result)
    }
}