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

    override fun part1(): Result {
        val sum = instructions.sumOf { pair -> pair.first * pair.second }
        return Result.Number(sum)
    }

    override fun part2(): Result {
        instructions.clear()
        var enabled = true
        regex = Regex("""do\(\)|don't\(\)|mul\((\d+),(\d+)\)""")
        readFile(filename).forEach { line ->
            regex.findAll(line).forEach { match ->
                if (match.value == "do()") enabled = true
                if (match.value == "don't()") enabled = false
                if (enabled && match.value != "do()") {
                    val (first, second) = match.destructured
                    instructions.add(Pair(first.toInt(), second.toInt()))
                }
            }

        }

        val sum = instructions.sumOf { pair -> pair.first * pair.second }
        return Result.Number(sum)
    }
}