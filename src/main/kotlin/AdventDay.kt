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
    protected abstract fun part1()
    protected abstract fun part2()
    protected fun solve() {
        part1()
        part2()
    }
}