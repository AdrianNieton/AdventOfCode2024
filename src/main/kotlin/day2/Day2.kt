package day2

import common.AdventDay
import common.Result
import kotlin.math.abs

class Day2(private val filename: String = "day2.txt"): AdventDay() {
    private val reportsList = mutableListOf<List<Int>>()

    init {
        processFile(readFile(filename))
        solve()
    }

    override fun processFile(lines: List<String>) {
        lines.forEach { line ->
            val nums = line.split("\\s+".toRegex()).map { num -> num.toInt() }
            reportsList.add(nums)
        }
    }

    private fun isSafeReport(numList: List<Int>): Boolean {
        val isIncreasing = numList[0] < numList[1]
        for (i in 0 until numList.size - 1) {
            val difference = abs(numList[i] - numList[i + 1])
            if (difference !in 1..3) return false
            if (isIncreasing && numList[i] > numList[i + 1]) return false
            if (!isIncreasing && numList[i] < numList[i + 1]) return false
        }
        return true
    }

    override fun part1(): Result {
        var safeReports = 0
        reportsList.forEach { numList ->
            if (isSafeReport(numList)) {
                safeReports++
            }
        }
        return Result.Number(safeReports)
    }

     override fun part2(): Result {
        var safeReports = 0

        fun canBeMadeSafe(numList: List<Int>): Boolean {
            for (i in numList.indices) {
                val modifiedList = numList.toMutableList().apply { removeAt(i) }
                if (isSafeReport(modifiedList)) return true
            }
            return false
        }

        reportsList.forEach { numList ->
            if (isSafeReport(numList) || canBeMadeSafe(numList)) {
                safeReports++
            }
        }

        return Result.Number(safeReports)
    }
}
