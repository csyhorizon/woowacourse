package calculator.domain

object StringCalculator {

    fun calculate(input: String?): Int {
        if (input.isNullOrBlank()) {
            return 0;
        }

        val numbers = Parser.parse(input)
        return numbers.sum();
    }
}