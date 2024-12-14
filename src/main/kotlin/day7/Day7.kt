package day7

import common.AdventDay
import common.ProcessFileConfig
import common.Result
import kotlin.math.pow

class Day7(private val filename: String = "day7.txt") : AdventDay() {
    private val regex = Regex("""(\d+):\s+((\d+\s*)+)""")

    enum class Operators(val op: Char, val function: (Long, Long) -> Long) {
        SUM('+', { a, b -> a + b }),
        MUL('*', { a, b -> a * b }),
        FUSION('|', { a , b -> (a.toString() + b.toString()).toLong() })
    }

    init {
        val lines = readFile(filename)
        updateConfig(lines = lines, regex = regex)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): MutableMap<Long, MutableList<Long>> {
        val operations = mutableMapOf<Long, MutableList<Long>>()
        val (lines, regex) = config
        lines.forEach { line ->
            regex.find(line)?.let { match ->
                val id = match.groups[1]?.value?.toLongOrNull()
                val numbers = match.groups[2]?.value
                    ?.trim()
                    ?.split("\\s+".toRegex())
                    ?.mapNotNull { it.toLongOrNull() }

                if (id != null && numbers != null) {
                    operations[id] = numbers.toMutableList()
                }
            }
        }
        return operations
    }

    override fun part1(): Result {
        val operations = processFile(getConfig())
        var sum = 0L
        val allowedOperators = Operators.entries.filter { it != Operators.FUSION }

        operations.forEach { (expectedResult, valueList) ->
            if (calculateExpectedNumber(expectedResult, valueList, allowedOperators)) {
                sum += expectedResult
            }
        }

        return Result.Long(sum)
    }

    private fun calculateExpectedNumber(expectedResult: Long, valueList: MutableList<Long>, operators: List<Operators>): Boolean {
        return generateCombinations(valueList, operators).any { result ->
            result == expectedResult
        }
    }

    private fun generateCombinations(valueList: MutableList<Long>, operators: List<Operators>) : List<Long> {
        val numCombinations = operators.size.toDouble().pow((valueList.size - 1).toDouble()).toInt()
        val results = mutableListOf<Long>()

        for (i in 0 until numCombinations) {
            val operatorCombination = mutableListOf<Operators>()
            var index = i
            repeat(valueList.size-1) {
                val operator = operators[index % operators.size]
                operatorCombination.add(operator)
                index /= operators.size
            }

            val result = evaluateExpression(valueList, operatorCombination)
            results.add(result)
        }
        return results
    }

    private fun evaluateExpression(valueList: MutableList<Long>, operatorCombination: MutableList<Operators>) : Long {
        var result = valueList.first()
        for (i in operatorCombination.indices) {
            result = operatorCombination[i].function(result, valueList[i + 1])
        }
        return result
    }

    override fun part2(): Result {
        val operations = processFile(getConfig())
        var sum = 0L

        operations.forEach { (expectedResult, valueList) ->
            if (calculateExpectedNumber(expectedResult, valueList, Operators.entries)) {
                sum += expectedResult
            }
        }

        return Result.Long(sum)
    }
}