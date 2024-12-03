package day3

import common.AdventDay
import common.Result

class Day3(private val filename: String = "day3.txt") : AdventDay() {

    private val instructions = mutableListOf<Pair<Int, Int>>()
    private var regex = Regex("""mul\((\d+),(\d+)\)""")

    init {
        processFile(readFile(filename))
        solve()
    }

    override fun processFile(lines: List<String>) {
        lines.forEach { line ->
            val operations = regex.findAll(line).map { matchResult ->
                val (first, second) = matchResult.destructured
                Pair(first.toInt(), second.toInt())
            }
            instructions.addAll(operations)
        }
    }

    private fun reset() {
        instructions.clear()
//        regex = Regex(
//            """(?<!don't\(\).*?)mul\((\d+),(\d+)\)(?=[^d]|d(?!on't\())*$"""
//        )
        //processFile(readFile(filename))
    }

    override fun part1(): Result {
        val sum = instructions.sumOf { pair -> pair.first * pair.second }
        return Result.Number(sum)
    }

    override fun part2(): Result {
        reset()
        return Result.Number(0)
    }
}