package day1

import common.AdventDay
import common.Result
import kotlin.math.abs


class Day1(private val filename: String = "day1.txt"): AdventDay() {
    private val leftList = mutableListOf<Int>()
    private val rightList = mutableListOf<Int>()

    init {
        val lines = this.readFile(filename)
        processFile(lines)
        this.solve()
    }

    override fun processFile(lines: List<String>) {
        lines.forEach { line ->
            val (num1, num2) = line.split("\\s+".toRegex()).map { num -> num.toInt() }
            leftList.add(num1)
            rightList.add(num2)
        }
    }

    override fun part1() : Result {
        leftList.sort()
        rightList.sort()
        val totalDistance = leftList.zip(rightList) { left, right ->
            abs(left - right)
        }.sum()
        return Result.Number(totalDistance)
    }

    override fun part2() : Result {
        val frequencyMap = rightList.groupingBy { it }.eachCount()
        val similarityScore = leftList.sumOf { leftNum ->
            (frequencyMap[leftNum] ?: 0) * leftNum
        }
       return Result.Number(similarityScore)
    }

//  First version O(N^2)
//    override fun part2() {
//        var similarityScore = 0
//        leftList.forEach { leftNum ->
//            var count = 0
//            rightList.forEach { rightNum ->
//                if (leftNum == rightNum) {
//                    count++
//                }
//            }
//            similarityScore += leftNum * count
//        }
//        println("Part2: $similarityScore")
//    }
}