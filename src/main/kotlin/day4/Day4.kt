package day4

import common.AdventDay
import common.ProcessFileConfig
import common.Result

class Day4(private val filename: String = "day4.txt") : AdventDay() {

    init {
        val lines = readFile(filename)
        updateConfig(lines = lines)
        solve()
    }

    override fun processFile(config: ProcessFileConfig): MutableList<MutableList<Char>> {
        val charMat = mutableListOf<MutableList<Char>>()
        val lines = config.lines
        lines.forEach { line ->
            charMat.add(line.toMutableList())
        }
        return charMat
    }

    override fun part1(): Result {
        val charMat = processFile(getConfig())
        val xmasMatch = "XMAS"
        var xmasCount = 0
        var xmas: String
        charMat.forEachIndexed { i, row ->
            row.forEachIndexed { j, char ->
                // Comprobar en línea horizontal
                if ((j + 3) < row.size) { // Usamos row.size para columnas
                    xmas = "${charMat[i][j]}${charMat[i][j + 1]}${charMat[i][j + 2]}${charMat[i][j + 3]}"
                    if (xmas == xmasMatch || xmas.reversed() == xmasMatch)
                        xmasCount++
                }
                // Comprobar en vertical hacia abajo
                if ((i + 3) < charMat.size) { // Usamos charMat.size para filas
                    xmas = "${charMat[i][j]}${charMat[i + 1][j]}${charMat[i + 2][j]}${charMat[i + 3][j]}"
                    if (xmas == xmasMatch || xmas.reversed() == xmasMatch)
                        xmasCount++
                }
                // Comprobar en diagonal v -->
                if ((i + 3) < charMat.size && (j + 3) < row.size) {
                    xmas = "${charMat[i][j]}${charMat[i + 1][j + 1]}${charMat[i + 2][j + 2]}${charMat[i + 3][j + 3]}"
                    if (xmas == xmasMatch || xmas.reversed() == xmasMatch)
                        xmasCount++
                }
                // Comprobar en diagonal v <--
                if ((i + 3) < charMat.size && (j - 3) >= 0) {
                    xmas = "${charMat[i][j]}${charMat[i + 1][j - 1]}${charMat[i + 2][j - 2]}${charMat[i + 3][j - 3]}"
                    if (xmas == xmasMatch || xmas.reversed() == xmasMatch)
                        xmasCount++
                }
            }
        }
        return Result.Number(xmasCount)
    }

    override fun part2(): Result {
        val charMat = processFile(getConfig())
        val masMatch = "MAS"
        var masCount = 0
        var masLeft: String
        var masRight: String

        charMat.forEachIndexed { i, row ->
            row.forEachIndexed { j, char ->
                //Comprobar diagonal v -->
                if ((i + 2) < charMat.size && (j + 2) < row.size) {
                    masRight = "${charMat[i][j]}${charMat[i + 1][j + 1]}${charMat[i + 2][j + 2]}"
                    masLeft = "${charMat[i][j + 2]}${charMat[i + 1][j + 1]}${charMat[i + 2][j]}"
                    if ((masRight == masMatch || masRight.reversed() == masMatch) && (masLeft == masMatch || masLeft.reversed() == masMatch))
                        masCount++
                }
            }
        }

        return Result.Number(masCount)
    }
}