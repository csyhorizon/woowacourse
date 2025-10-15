package calculator.domain

object Parser {

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