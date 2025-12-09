package org.devpush.com.aoc2025.day7

import org.devpush.com.aoc2025.readInput

fun main() {

    fun part1(): Long {
        // Implement the logic for part 1 here
        val input = readInput(false, 7, false)

        val height = input.size
        val width = input[0].length
        val matrix = IntArray(width * height) { 0 }

        for (r in 0 until height) {
            val line = input[r]
            for (c in 0 until width) {
                val char = line[c]
                val index = r * width + c
                if (char == 'S') {
                    matrix[index] = 1
                } else if (char == '^') {
                    matrix[index] = 2
                }
            }
        }


        var splitCount = 0L

        for (i in 1 until height) {
            for (j in 0 until width) {
                val prevRowIndex = (i - 1) * width + j
                val currIndex = i * width + j
                if (matrix[prevRowIndex] == 1) {
                    if (matrix[currIndex] == 2) {
                        splitCount++

                        if (j > 0) {
                            val leftIndex = i * width + (j - 1)
                            if (matrix[leftIndex] == 0) {
                                matrix[leftIndex] = 1
                            }
                        }
                        if (j < width - 1) {
                            val rightIndex = i * width + (j + 1)
                            if (matrix[rightIndex] == 0) {
                                matrix[rightIndex] = 1
                            }
                        }
                    } else {
                        matrix[currIndex] = 1
                    }
                }
            }
        }

        return splitCount
    }

    fun part2(): Long {
        // Implement the logic for part 2 here
        val input = readInput(false, 7, true)
        val height = input.size
        val width = input[0].length
        val matrix = Array(height) { LongArray(width) }

        for (r in 0 until height) {
            for (c in 0 until width) {
                if (input[r][c] == 'S') {
                    matrix[r][c] = 1L
                }
            }
        }

        for (r in 0 until height-1) {
            for (c in 0 until width) {
                val currentCount = matrix[r][c]
                if(currentCount>0L){
                    val nextChar = input[r + 1][c]
                    if(nextChar == '^') {
                        if (c > 0) {
                            matrix[r + 1][c - 1] += currentCount
                        }
                        if (c < width - 1) {
                            matrix[r + 1][c + 1] += currentCount
                        }
                    }
                    else {
                        matrix[r + 1][c] += currentCount
                    }
                }
            }

        }

        return matrix[height-1].sum()
    }

    println(part1())
    println(part2())

}