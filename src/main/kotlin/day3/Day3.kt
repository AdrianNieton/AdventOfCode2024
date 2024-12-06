package day3

import common.AdventDay
import common.ProcessFileConfig
import common.Result

class Day3(private val filename: String = "day3.txt") : AdventDay() {

    private val regex = Regex("""mul\((\d+),(\d+)\)""")
    private val regexPart2 = Regex("""do\(\)|don't\(\)|mul\((\d+),(\d+)\)""")

    init {
        val lines = readFile(filename)
        updateConfig(lines = lines, regex = regex, flag = false)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): List<Pair<Int, Int>> {
        val (lines, regex, flag) = config
        var enabled = true
        val pairList = mutableListOf<Pair<Int, Int>>()
        lines.forEach { line ->
            regex.findAll(line).forEach { match ->
                if (flag) {
                    if (match.value == "do()") enabled = true
                    if (match.value == "don't()") enabled = false
                }
                if (enabled && match.value != "do()") {
                    val (first, second) = match.destructured
                    pairList.add(Pair(first.toInt(), second.toInt()))
                }
            }
        }
        return pairList
    }

    override fun part1(): Result {
        val instructions = processFile(getConfig())
        val sum = instructions.sumOf { pair -> pair.first * pair.second }
        return Result.Number(sum)
    }

    override fun part2(): Result {
        updateConfig(regex = regexPart2, flag = true)
        val instructions = processFile(getConfig())
        val sum = instructions.sumOf { pair -> pair.first * pair.second }
        return Result.Number(sum)
    }
}