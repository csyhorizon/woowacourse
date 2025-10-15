package calculator.util

object Validator {

    fun checkNegative(numbers: List<Int>) {
        val negatives = numbers.filter { it < 0 }
        if (negatives.isNotEmpty()) {
            throw IllegalArgumentException("음수 입력 불가: $negatives")
        }
    }
}