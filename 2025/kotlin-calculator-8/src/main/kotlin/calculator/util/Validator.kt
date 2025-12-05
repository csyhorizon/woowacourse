package calculator.util

object Validator {

    fun checkNegative(numbers: List<Int>) {
        val negatives = numbers.filter { it < 0 }
        if (negatives.isNotEmpty()) {
            throw IllegalArgumentException("음수 입력 불가: $negatives")
        }
    }

    fun checkInvalidCharacters(input: String, customDelimiter: String?) {
        var allowedChars = "0-9,:\n"

        if (customDelimiter != null) {
            val escapedDelimiter = Regex.escape(customDelimiter)
            allowedChars += escapedDelimiter;
        }

        val regex = Regex("""[^$allowedChars]""")

        if (regex.containsMatchIn(input)) {
            throw IllegalArgumentException("잘못된 문자 포함: $input")
        }
    }
}