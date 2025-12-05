package calculator.domain

import calculator.util.Validator

object Parser {

    fun parse(input: String): List<Int> {
        val (customDelimiter, numberPart) = extractDelimiterAndData(input)

        Validator.checkInvalidCharacters(numberPart, customDelimiter)

        val allDelimiters = mutableListOf(",", ":")
        if (customDelimiter != null) {
            allDelimiters.add(customDelimiter)
        }

        val delimiterRegexString = allDelimiters
            .joinToString(separator = "|") { Regex.escape(it) }

        val finalDelimiterRegex = Regex("($delimiterRegexString|\\n)")

        val numberStrings = numberPart
            .split(finalDelimiterRegex)
            .filter { it.isNotBlank() }

        val numbers = numberStrings.map { it.toInt() }
        Validator.checkNegative(numbers)

        return numbers
    }

    private fun extractDelimiterAndData(input: String): Pair<String?, String> {
        val customDelimiterRegex = Regex("""//(.)\\n""")
        val match = customDelimiterRegex.find(input)

        return if (match != null) {
            val delimiter = match.groupValues[1]
            val numbers = input.substring(match.range.last + 1)
            Pair(delimiter, numbers)
        } else {
            Pair(null, input)
        }
    }
}