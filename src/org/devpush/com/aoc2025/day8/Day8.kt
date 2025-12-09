package org.devpush.com.aoc2025.day8

import org.devpush.com.aoc2025.readInput

fun main() {

    data class Point3D(val x: Int, val y: Int, val z: Int)

    data class Edge(val u: Int, val v: Int, val distSq: Long)

    class DSU(size: Int) {
        val parent = IntArray(size) { it }
        val componentSize = IntArray(size) { 1 }

        fun find(i: Int): Int {
            if (parent[i] != i) {
                parent[i] = find(parent[i])
            }
            return parent[i]
        }

        fun union(i: Int, j: Int) {
            val rootI = find(i)
            val rootJ = find(j)
            if (rootI != rootJ) {
                if (componentSize[rootI] < componentSize[rootJ]) {
                    parent[rootI] = rootJ
                    componentSize[rootJ] += componentSize[rootI]
                } else {
                    parent[rootJ] = rootI
                    componentSize[rootI] += componentSize[rootJ]
                }
            }
        }

        fun getComponentSizes(): List<Int> {
            val sizes = mutableMapOf<Int, Int>()
            for (i in parent.indices) {
                val root = find(i)
                sizes[root] = componentSize[root]
            }
            return sizes.values.toList()
        }

    }

    class DSU1(val size: Int) {
        private val parent = IntArray(size) { it }

        // Track how many isolated clusters exist
        var numComponents = size

        fun find(i: Int): Int {
            if (parent[i] != i) {
                parent[i] = find(parent[i])
            }
            return parent[i]
        }

        // Returns true if a merge happened, false if they were already connected
        fun union(i: Int, j: Int): Boolean {
            val rootI = find(i)
            val rootJ = find(j)
            if (rootI != rootJ) {
                parent[rootI] = rootJ
                numComponents-- // We merged two sets, so total sets decrease by 1
                return true
            }
            return false
        }
    }

    fun part1(): Long {

        val input = readInput(false, 8, false)
        val points = input.map { line ->
            val parts = line.split(",").map { it.trim().toInt() }
            Point3D(parts[0], parts[1], parts[2])
        }
        val edges = ArrayList<Edge>()
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val dx = p1.x - p2.x
                val dy = p1.y - p2.y
                val dz = p1.z - p2.z
                val distSq = dx.toLong() * dx + dy.toLong() * dy + dz.toLong() * dz
                edges.add(Edge(i, j, distSq))
            }
        }

        edges.sortBy { it.distSq }

        val dsu = DSU(points.size)
        val connectionsToMake = 1000

        for (k in 0 until minOf(connectionsToMake, edges.size)) {
            val edge = edges[k]
            dsu.union(edge.u, edge.v)
        }

        val sizes = dsu.getComponentSizes().sortedDescending()
        val result = sizes.take(3).fold(1L) { acc, size -> acc * size }

        return result

    }

    fun part2() {
        val input = readInput(false, 8, true)
        val points = input.map { line ->
            val parts = line.split(",").map { it.trim().toInt() }
            Point3D(parts[0], parts[1], parts[2])

        }
        val edges = ArrayList<Edge>()
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val p1 = points[i]
                val p2 = points[j]
                val dx = p1.x - p2.x
                val dy = p1.y - p2.y
                val dz = p1.z - p2.z
                val distSq = dx.toLong() * dx + dy.toLong() * dy + dz.toLong() * dz
                edges.add(Edge(i, j, distSq))
            }
        }

        edges.sortBy { it.distSq }

        val dsu = DSU1(points.size)

        for (edge in edges) {
            if (dsu.union(edge.u, edge.v)) {
                if (dsu.numComponents == 1) {
                    // "Network fully connected!"

                    // Retrieve the specific points that formed this last connection
                    val p1 = points[edge.u]
                    val p2 = points[edge.v]

                    // Calculate the answer based on the specific Part 2 question
                    // (Example: Multiply their X coordinates)
                    val result = p1.x.toLong() * p2.x.toLong()
                    println("Result: $result")

                    break // Stop immediately
                }
            }
        }
    }

    println(part1())
    part2()
}