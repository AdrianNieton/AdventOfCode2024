package common

import java.io.File

abstract class AdventDay {
    private val staticPath = "src/main/resources/"
    private var config = ProcessFileConfig(emptyList())

    protected fun readFile(filename: String = ""): List<String> {
        return try {
            File(staticPath + filename).readLines()
        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
            emptyList()
        }
    }

    protected abstract fun processFile(config: ProcessFileConfig = this.config): Any
    protected abstract fun part1(): Result
    protected abstract fun part2(): Result

    protected fun getConfig() = config
    protected fun updateConfig(
        lines: List<String>? = null,
        regex: Regex? = null,
        flag: Boolean? = null
    ) {
        config = config.copy(
            lines =  lines ?: config.lines,
            regex = regex ?: config.regex,
            flag = flag ?: config.flag
        )
    }

    private fun formatResult(result: Result): String {
        return when (result) {
            is Result.Number -> result.value.toString()
            is Result.Long -> result.value.toString()
        }
    }

    protected fun solve() {
        println("--------${this.javaClass.simpleName}--------")
        println("Part 1 : ${formatResult(part1())}")
        println("Part 2 : ${formatResult(part2())}")
        println()
    }
}

data class ProcessFileConfig(
    val lines: List<String>,
    val regex: Regex = Regex(".*"),
    val flag: Boolean = true
)