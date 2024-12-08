package day6

import common.AdventDay
import common.ProcessFileConfig
import common.Result

class Day6(private val filename: String = "day6.txt") : AdventDay() {
    private val validChars = setOf('^', 'v', '>', '<')
    init {
        val lines = readFile(filename)
        updateConfig(lines = lines)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): Pair<Int, MutableList<MutableList<Char>>> {
        val charMat = mutableListOf<MutableList<Char>>()
        val lines = config.lines
        var rowId = 0
        lines.forEachIndexed { index, line ->
            if (line.count { it in validChars } == 1) {
                rowId = index
            }
            charMat.add(line.toMutableList())
        }
        return Pair(rowId, charMat)
    }

    private tailrec fun move(
        charMat: MutableList<MutableList<Char>>,
        i: Int,
        j: Int,
        value: Char,
        distinctPositions: MutableSet<Pair<Int, Int>>,
        ended: Boolean = false
    ): MutableSet<Pair<Int, Int>> {
        if (ended) return distinctPositions
        var isEnded = false
        var auxi = i
        var auxj = j
        var auxValue = value

        distinctPositions.add(Pair(i, j))

        when (value) {
            '^' -> {
                if (i - 1 >= 0)
                    if (charMat[i - 1][j] != '#') {
                        charMat[i][j] = 'X'
                        charMat[i - 1][j] = '^'
                        auxi = i - 1
                    } else {
                        charMat[i][j] = '>'
                        auxValue = '>'
                    }
                else isEnded = true
            }

            '>' -> {
                if (j + 1 < charMat[i].size)
                    if (charMat[i][j + 1] != '#') {
                        charMat[i][j] = 'X'
                        charMat[i][j + 1] = '>'
                        auxj = j + 1
                    } else {
                        charMat[i][j] = 'v'
                        auxValue = 'v'
                    }
                else isEnded = true
            }

            'v' -> {
                if (i + 1 < charMat.size)
                    if (charMat[i + 1][j] != '#') {
                        charMat[i][j] = 'X'
                        charMat[i + 1][j] = 'v'
                        auxi = i + 1
                    } else {
                        charMat[i][j] = '<'
                        auxValue = '<'
                    }
                else isEnded = true
            }

            '<' -> {
                if (j - 1 >= 0)
                    if (charMat[i][j - 1] != '#') {
                        charMat[i][j] = 'X'
                        charMat[i][j - 1] = '<'
                        auxj = j - 1
                    } else {
                        charMat[i][j] = '^'
                        auxValue = '^'
                    }
                else isEnded = true
            }
        }

        return move(charMat, auxi, auxj, auxValue, distinctPositions, isEnded)
    }

    override fun part1(): Result {
        val (rowId, charMat) = processFile(getConfig())
        val distinctPositions = mutableSetOf<Pair<Int, Int>>()
        var colId = 0
        var value = ' '
        charMat[rowId].forEachIndexed { col, char ->
            if (char in validChars) {
                value = char
                colId = col
                return@forEachIndexed //detener ejecucion
            }
        }
        move(charMat, rowId, colId, value, distinctPositions)

        val sum = distinctPositions.size
        return Result.Number(sum)
    }

    override fun part2(): Result {
        return Result.Number(0)
    }
}