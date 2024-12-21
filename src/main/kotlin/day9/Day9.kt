package day9

import common.AdventDay
import common.ProcessFileConfig
import common.Result
import java.util.LinkedList
import java.util.Queue
import kotlin.math.abs

class Day9(private val filename: String = "day9.txt") : AdventDay() {

    init {
        val lines = readFile(filename)
        updateConfig(lines = lines)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): Pair<MutableMap<Int, MutableList<Int>>, MutableMap<Int, Queue<Int>>> {
        val lines = config.lines
        val formattedArray = mutableMapOf<Int, MutableList<Int>>()
        val freeSpaces = mutableMapOf<Int, Queue<Int>>()

        lines.forEachIndexed { numLine, line ->
            var isBlock = true
            var numId = 0
            formattedArray[numLine] = mutableListOf()
            freeSpaces[numLine] = LinkedList()
            line.forEach { num ->
                if (isBlock) {
                    repeat(num.digitToInt()) {
                        formattedArray[numLine]?.add(numId)
                    }
                    numId++
                } else {
                    repeat(num.digitToInt()) {
                        formattedArray[numLine]?.add(-1)
                        freeSpaces[numLine]?.add(formattedArray[numLine]?.lastIndex)
                    }
                }
                isBlock = !isBlock
            }
        }
        return Pair(formattedArray, freeSpaces)
    }

    private fun moveFileBocks(blocksMap: MutableMap<Int, MutableList<Int>>, freeSpaces: MutableMap<Int, Queue<Int>>) {
        blocksMap.forEach { (index, blocks) ->
            while (freeSpaces[index]!!.isNotEmpty()) {
                if (blocks[blocks.lastIndex] == -1) {
                    freeSpaces[index]?.removeIf { it == blocks.lastIndex }
                    blocks.removeAt(blocks.lastIndex)
                } else {
                    blocks[freeSpaces[index]!!.remove()] = blocks[blocks.lastIndex]
                    blocks.removeAt(blocks.lastIndex)
                }
            }
        }
    }

    private fun getFreeSpacesBlocks(freeSpaces: MutableMap<Int, Queue<Int>>): MutableList<MutableList<Int>> {
        val freeSpacesBlocks = mutableListOf<MutableList<Int>>()
        freeSpaces.toList().forEach { (index, row) ->
            val freeSpacesRow = row.toList()
            var countBlock = 1
            val auxList = mutableSetOf<Int>()
            for (i in 0 until freeSpacesRow.size-1) {
                if (abs(freeSpacesRow[i] - freeSpacesRow[i + 1]) == 1) {
                    countBlock++
                    auxList.add(freeSpacesRow[i])
                    auxList.add(freeSpacesRow[i + 1])
                }
                else {
                    auxList.add(freeSpacesRow[i])
                    freeSpacesBlocks.add(auxList.toMutableList())
                    auxList.clear()
                    auxList.add(freeSpacesRow[i + 1])
                    countBlock = 1
                }
            }
            if (auxList.isNotEmpty()) //Last element
                freeSpacesBlocks.add(auxList.toMutableList())
        }
        return freeSpacesBlocks
    }

    private fun moveWholeBlocks(blocksMap: MutableMap<Int, MutableList<Int>>, freeSpaces: MutableList<MutableList<Int>>) {
        blocksMap.forEach { (index, blocks) ->
            var count = 1
            val auxList = mutableSetOf<Int>()
            for (i in blocks.lastIndex downTo 1) {
                if (blocks[i] != -1)
                    auxList.add(i)
                if (blocks[i] == blocks[i - 1]) {
                    count++
                    auxList.add(i - 1)
                    continue
                }
                else {
                    freeSpaces.forEach { spacesList ->
                        if (spacesList.size >= count) {
                            val indexToRemove = mutableListOf<Int>()
                            auxList.forEachIndexed { index, numIndex ->
                                val freeIndex = spacesList[index]
                                if (i > freeIndex) {
                                    blocks[freeIndex] = blocks[numIndex]
                                    blocks[numIndex] = 0
                                    indexToRemove.add(freeIndex)
                                }
                            }
                            spacesList.removeAll(indexToRemove)
                            auxList.clear()
                        }
                    }
                    auxList.clear()
                    count = 1
                }
            }
            println(blocks)
            blocks.forEachIndexed { index, value ->
                if (value == -1) {
                    blocks[index] = 0
                }
            }
        }
    }

    private fun calculateCheckSum(blocks: MutableMap<Int, MutableList<Int>>) : Map<Int, Long> {
        return blocks.mapValues { (_, block) ->
            block.foldIndexed(0) { index, acc, num -> acc + index * num }
        }
    }

    override fun part1(): Result {
        val (blocks, freeSpaces) = processFile(getConfig())
        moveFileBocks(blocks, freeSpaces)
        val resultsMap = calculateCheckSum(blocks)

        return Result.Long(resultsMap.firstNotNullOf { it.value })
    }

    override fun part2(): Result {
        val (blocks, freeSpaces) = processFile(getConfig())
        val freeSpacesBlocks = getFreeSpacesBlocks(freeSpaces)
        moveWholeBlocks(blocks, freeSpacesBlocks)
        val resultsMap = calculateCheckSum(blocks)
        return  Result.Long(resultsMap.firstNotNullOf { it.value })
    }
}