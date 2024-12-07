package day5

import common.AdventDay
import common.ProcessFileConfig
import common.Result

typealias ProcessResult = Pair<MutableList<Pair<Int, Int>>, MutableList<MutableList<Int>>>

class Day5(private val filename: String = "day5.txt") : AdventDay() {
    init {
        val lines = readFile(filename)
        updateConfig(lines = lines)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): ProcessResult {
        val pairList = mutableListOf<Pair<Int, Int>>()
        val orderList = mutableListOf<MutableList<Int>>()
        val lines = config.lines
        lines.forEach { line ->
            if (line.contains("|")) {
                val (first, second) = line.split("|")
                pairList.add(Pair(first.toInt(), second.toInt()))
            }
            else if (line.isNotEmpty()){
                val orders = line.split(",").map { it.toInt() }.toMutableList()
                orderList.add(orders)
            }
        }
        return Pair(pairList, orderList)
    }

    private fun checkIfCorrect(numList: List<Int>, pairList: List<Pair<Int, Int>>): Boolean {
        val positionMap = numList.withIndex().associate { it.value to it.index }
        return pairList.all { (x, y) ->
            if (x !in positionMap || y !in positionMap) true
            else positionMap[x]!! < positionMap[y]!!
        }
    }

    override fun part1(): Result {
        val (pairList, orderList) = processFile(getConfig())
        var orderSum = 0

        orderList.forEach { numList ->
            val isCorrect = checkIfCorrect(numList, pairList)
            if (isCorrect) orderSum += numList[numList.size / 2]
        }
        return Result.Number(orderSum)
    }

    override fun part2(): Result {
        val (pairList, orderList) = processFile(getConfig())
        val graph = mutableMapOf<Int, MutableList<Int>>()
        val incorrectListIndexes = mutableSetOf<Int>()
        var orderSum = 0

        pairList.forEach { (x, y) ->
            graph.computeIfAbsent(x) { mutableListOf() }.add(y)
            graph.computeIfAbsent(y) { mutableListOf() }
        }

        orderList.forEachIndexed { index, numList ->
            var isCorrect = false
            var i = 0
            while (!isCorrect) {
                if (!graph[numList[i]]!!.contains(numList[i + 1])) {
                    val aux = numList[i]
                    numList[i] = numList[i + 1]
                    numList[i + 1] = aux
                    incorrectListIndexes.add(index)
                }
                isCorrect = checkIfCorrect(numList, pairList)
                if ((i+1) >= (numList.size - 1)) i = 0
                else i++
            }
        }

        incorrectListIndexes.forEach { index ->
            orderSum += orderList[index][orderList[index].size/2]
        }
        return Result.Number(orderSum)
    }
}