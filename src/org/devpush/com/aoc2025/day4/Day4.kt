package org.devpush.com.aoc2025.day4

import org.devpush.com.aoc2025.readInput

fun main(){
    fun part1():Int{
        val input1 = readInput(false,4,false)
        val matrix = input1.map { eachRow ->
            eachRow.toCharArray()
        }.toTypedArray()
        val rows = matrix.size
        val cols = matrix[0].size

        var ans = 0
        val directions = arrayOf(
            intArrayOf(-1, 0),
            intArrayOf(-1, -1),
            intArrayOf(-1, 1),
            intArrayOf(1, 0),
            intArrayOf(1, -1),
            intArrayOf(1, 1),
            intArrayOf(0, -1),
            intArrayOf(0, 1)
        )

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (matrix[i][j] == '@') {
                    var neighborCount = 0

                    for (dir in directions) {
                        val newRow = i + dir[0]
                        val newCol = j + dir[1]

                        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                            if (matrix[newRow][newCol] == '@') {
                                neighborCount++
                            }
                        }
                    }

                    if (neighborCount < 4) {
                        ans++
                    }

                }
            }
        }
        return ans
    }

    println(part1())

    fun part2():Int{

        val input2 = readInput(false,4,true)

        val matrix = input2.map{eachRow ->
            eachRow.toCharArray()
        }.toTypedArray()

        val rows = matrix.size
        val cols = matrix[0].size


        var ans = 0

        while (true){
            val nodesToUpdate = mutableListOf<Pair<Int, Int>>()

            val directions = arrayOf(
                intArrayOf(-1, 0),
                intArrayOf(-1, -1),
                intArrayOf(-1, 1),
                intArrayOf(1, 0),
                intArrayOf(1, -1),
                intArrayOf(1, 1),
                intArrayOf(0, -1),
                intArrayOf(0, 1)
            )

            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    if (matrix[i][j] == '@') {
                        var neighborCount = 0

                        for (dir in directions) {
                            val newRow = i + dir[0]
                            val newCol = j + dir[1]

                            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                                if (matrix[newRow][newCol] == '@') {
                                    neighborCount++
                                }
                            }
                        }

                        if (neighborCount < 4) {
                            nodesToUpdate.add(Pair(i, j))
                        }

                    }
                }
            }

            if (nodesToUpdate.isEmpty()) {
                break
            }

            ans += nodesToUpdate.size

            for ((r, c) in nodesToUpdate) {
                matrix[r][c] = 'x'
            }
        }

        return ans
    }

    println(part2())

}