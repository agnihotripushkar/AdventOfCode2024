package org.devpush.com.aoc2025

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.reader

@Throws(IOException::class)
fun readInput(test: Boolean, day: Int, partb: Boolean): MutableList<String> {
    val testDir = if (test) {
        if (partb) "aoc2025/test_b" else "aoc2025/test_a"
    } else {
        if (partb) "aoc2025/input_b" else "aoc2025/input_a"
    }
    val fileName = "$testDir/day$day.txt"
    val list = Path(fileName).readLines().toMutableList()
//    val br = BufferedReader(FileReader("$testDir/day$day.txt"))
//    var line: String?
//    val list: MutableList<String?> = ArrayList<String?>()
//    while ((br.readLine().also { line = it }) != null) {
//        list.add(line)
//    }
    return list
}

@Throws(IOException::class)
fun readWholeinput(test: Boolean, day: Int, partb: Boolean): String {
    var testDir = ""
    testDir = if (test) {
        if (partb) "aoc2025/test_b" else "aoc2025/test_a"
    } else {
        if (partb) "aoc2025/input_b" else "aoc2025/input_a"
    }
    val reader = BufferedReader(FileReader("$testDir/day$day.txt"))
    // Read the entire file content into a single string
    val input = StringBuilder()
    var line: String?
    while ((reader.readLine().also { line = it }) != null) {
        input.append(line).append("\n")
    }
    return input.toString()
}

fun convertListToMatrix(list: MutableList<String?>?): Array<CharArray?> {
    if (list == null || list.isEmpty()) {
        return Array<CharArray?>(0) { CharArray(0) }
    }

    val rows = list.size
    val cols = list.get(0)!!.length
    val matrix = Array<CharArray?>(rows) { CharArray(cols) }

    for (i in 0..<rows) {
        matrix[i] = list[i]!!.toCharArray()
    }

    return matrix
}

fun convertListToIntMatrix(list: MutableList<String?>?): Array<IntArray?> {
    if (list == null || list.isEmpty()) {
        return Array<IntArray?>(0) { IntArray(0) }
    }

    val rows = list.size
    val cols = list[0]!!.length
    val matrix = Array<IntArray?>(rows) { IntArray(cols) }

    for (i in 0..<rows) {
        for (j in 0..<cols) {
            matrix[i]!![j] = list[i]!![j].toString().toInt()
        }
    }

    return matrix
}

fun isSafe(row: Int, col: Int, matrix: Array<CharArray?>): Boolean {
    val rowSize = matrix.size
    val colSize = matrix[0]!!.size

    if ((row == 0 || col == 0 || row == (rowSize - 1) || col == (colSize - 1)) && matrix[row]!![col] == '.') {
        return false
    }

    return (row in 0..<rowSize && col >= 0 && col < (colSize))
}

fun isInteger(num: Float): Boolean {
    return num == num.toInt().toFloat()
}

fun isInteger(num: Long): Boolean {
    return num == num.toInt().toLong()
}