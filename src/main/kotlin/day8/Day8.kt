package day8

import common.AdventDay
import common.ProcessFileConfig
import common.Result

class Day8(private val filename: String = "day8.txt") : AdventDay() {

    init {
        val lines = readFile(filename)
        updateConfig(lines = lines, regex = Regex("""[a-zA-Z0-9]"""))
        solve()
    }

    override fun processFile(config: ProcessFileConfig) {

    }

    override fun part1(): Result {
        return Result.Number(0)
    }

    override fun part2(): Result {
        return Result.Number(0)
    }
}