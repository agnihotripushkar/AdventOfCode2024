package org.devpush.com.aoc2025.day3

import org.devpush.com.aoc2025.readInput

fun main() {

    fun getNo(line: String): Long {
        val size = line.length
        var sumString = ""
        var startIndex = 0
        for (j in 11 downTo 0) {
            for (i in 9 downTo 1) {
                val ch = i.digitToChar()
                val firstIdx = line.indexOf(ch, startIndex)
                if (firstIdx == -1) continue
                if (firstIdx + j < size) {
                    sumString += i.toString()
                    startIndex = firstIdx + 1
                    break
                }
            }
        }
        return sumString.toLong()
    }

    fun part1(): Long {
        val input = readInput(false, 3, false)
        var sum = 0L
        input.forEach { line ->
            for (i in 99 downTo 1) {
                val tenth = (i / 10).digitToChar()
                val oneth = (i % 10).digitToChar()
                val teenthIdx = line.indexOf(tenth)
                if (teenthIdx == -1) continue
                val onethIdx = line.indexOf(oneth, teenthIdx + 1)
                if (onethIdx == -1) continue
                sum += i
                break
            }
        }
        return sum
    }

    fun part2(): Long {
        val input = readInput(false, 3, true)
        var ans = 0L
        input.forEach { line ->
            val no = getNo(line)
            ans += no
        }
        return ans
    }

    println(part1())
    println(part2())
}