package org.devpush.com.aoc2023.day01

import org.devpush.com.aoc2023.readInput

val numWords = listOf(
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine"
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first(Char::isDigit).digitToInt()
            val lastDigit = line.last(Char::isDigit).digitToInt()
            val no = (firstDigit * 10) + lastDigit
            //println(no)
            no
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val a = getFirstDigit(it)
            val b = getLastDigit(it)
            (a * 10) + b
        }
    }

    val input = readInput(false, 1,false)
    println(part1(input))

    val input2 = readInput(false,1,true)
    println(part2(input2))
}



private fun getFirstDigit(line: String): Int {
    line.forEachIndexed { index, char ->
        if (char.isDigit()) return char.digitToInt()

        numWords.forEachIndexed { numStringIndex, numString ->
            if (line.substring(index).startsWith(numString)) {
                return numStringIndex + 1
            }
        }
    }

    return -1
}

private fun getLastDigit(line: String): Int {
    for (index in line.lastIndex downTo 0){
        val char = line[index]
        if (char.isDigit()) return char.digitToInt()

        numWords.forEachIndexed { listIndex, s ->
            if (line.substring(index).startsWith(s)){
                return listIndex + 1
            }
        }
    }
    return -1
}



