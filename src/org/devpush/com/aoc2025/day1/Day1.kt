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
                println("L $startPoint")
            }
            else{
                startPoint = (startPoint + move) % totalSize
                println("R $startPoint")
            }
            if(startPoint==0){
                zeroCount++
            }
        }

        return zeroCount

    }

    println(part1())

}