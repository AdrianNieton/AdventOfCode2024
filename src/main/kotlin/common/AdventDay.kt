package common

import java.io.File

abstract class AdventDay {
    val staticPath = "src/main/resources/"

    protected fun readFile(filename: String = ""): List<String> {
        return try {
            File(staticPath + filename).readLines()
        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
            emptyList()
        }
    }

    protected abstract fun processFile(lines: List<String>)
    protected abstract fun part1(): Result
    protected abstract fun part2(): Result

    protected fun formatResult(result: Result): String {
        return when (result) {
            is Result.Number -> result.value.toString()
        }
    }

    protected fun solve() {
        println("Part 1 : ${formatResult(part1())}")
        println("Part 2 : ${formatResult(part2())}")
    }
}