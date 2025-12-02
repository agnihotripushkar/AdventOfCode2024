package org.devpush.com.aoc2025.day1

import org.devpush.com.aoc2025.readInput

fun main(){
    fun part1(): Int{
        var startPoint = 50
        var zeroCount = 0
        val totalSize = 100
        val input = readInput(false,1,false)
        input.forEach {
            val move = it.drop(1).toInt()
            if(it[0]=='L'){
                startPoint = (startPoint - move)
                startPoint = (startPoint % totalSize + totalSize) % totalSize
                //println("L $startPoint")
            }
            else{
                startPoint = (startPoint + move) % totalSize
                //println("R $startPoint")
            }
            if(startPoint==0){
                zeroCount++
            }
        }

        return zeroCount

    }

    fun part2(): Int{
        var startPoint = 50
        var zeroCount = 0
        val totalSize = 100
        val input = readInput(false,1,true)
        input.forEach {
            val move = it.drop(1).toInt()
            if (it[0] == 'L') {
                val distToZero = if (startPoint == 0) totalSize else startPoint
                if (move >= distToZero){
                    zeroCount++
                    val remaining = move - distToZero
                    zeroCount += remaining / totalSize
                    startPoint = (totalSize - (remaining % totalSize)) % totalSize
                }
                else {
                    startPoint = if (startPoint == 0) totalSize - move else startPoint - move
                }
            } else {
                val virtualStart = startPoint
                val virtualEnd = virtualStart + move
                zeroCount += (virtualEnd / totalSize) - (virtualStart / totalSize)
                startPoint = virtualEnd % totalSize
            }
        }
        return zeroCount
    }

    println(part1())
    println(part2())

}