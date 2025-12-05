package org.devpush.com.aoc2023.day02

import org.devpush.com.aoc2023.readInput
import kotlin.math.max

data class Game(val id: Int, val sets: List<CubeSet>)

data class CubeSet(val red: Int, val green: Int, val blue: Int)

fun main() {

    fun parseGame(line: String): Game {
        // Example Line: "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue"
        val record = line.split(":")
        val gameId = record.first().split(" ").last().toInt()
        val arr = record.last().split(";")
        val cubeSets = mutableListOf<CubeSet>()
        for (i in arr.indices) {
            val colors = arr[i].split(",")
            var red = 0
            var green = 0
            var blue = 0
            for (j in colors.indices) {
                val colorRecord = colors[j].trim().split(" ")
                val count = colorRecord.first().toInt()
                val color = colorRecord.last()
                when (color) {
                    "red" -> red += count
                    "green" -> green += count
                    "blue" -> blue += count
                }
            }
            cubeSets.add(CubeSet(red, green, blue))
        }
        return Game(gameId, cubeSets)
    }

    fun part1(games: List<Game>): Int {
        val maxRed = 12
        val maxGreen = 13
        val maxBlue = 14

        return games.filter {game ->
            game.sets.all {set ->
                set.red <= maxRed && set.green <= maxGreen && set.blue <= maxBlue
            }
        }.sumOf { it.id }

    }

    fun part2(games: List<Game>): Int {
        // Implement part 2 logic here
        var totalSum = 0
        games.forEach {game ->
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0
            game.sets.forEach { set ->
                // Process each cube set
                maxRed = max(maxRed,set.red)
                maxGreen = max(maxGreen,set.green)
                maxBlue = max(maxBlue,set.blue)
            }
            val prod = maxRed * maxGreen * maxBlue
            totalSum += prod
        }
        return totalSum
    }

    val input = readInput(false,2,false)
    val games = input.map { parseGame(it) }
    println(part1(games))
    println(part2(games))

}