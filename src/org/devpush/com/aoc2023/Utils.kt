package org.devpush.com.aoc2023

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.readLines

@Throws(IOException::class)
fun readInput(test: Boolean, day: Int, partb: Boolean): MutableList<String> {
    val testDir = if (test) {
        if (partb) "aoc2023/test_b" else "aoc2023/test_a"
    } else {
        if (partb) "aoc2023/input_b" else "aoc2023/input_a"
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
        if (partb) "aoc2023/test_b" else "aoc2023/test_a"
    } else {
        if (partb) "aoc2023/input_b" else "aoc2023/input_a"
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