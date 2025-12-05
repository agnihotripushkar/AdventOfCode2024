package org.devpush.com.aoc2023.day02

import org.devpush.com.aoc2023.readInput

fun main(){

    fun part1(){
        val r = 12
        val g = 13
        val b = 14
        val input1 = readInput(true,2,false)
        input1.forEach { line ->
            val record = line.split(":")
            val arr = record.last().split(";")
            for (i in arr.indices) {
                val colors = arr[i].split(",")


            }
        }

    }
}