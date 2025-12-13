package org.devpush.com.aoc2025.day10

import org.devpush.com.aoc2025.readInput
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {

    fun part1(): Int {
        var totalPresses = 0
        val input = readInput(false, 10, false)
        for (line in input) {
            // [.##.]
            var target = line.substringAfter("[").substringBefore("]")
            //[0,1,1,0]
            var state = target.map { if (it == '#') 1 else 0 }.toIntArray()
            // 4
            val numLights = state.size

            //(3) (1,3) (2) (2,3) (0,2) (0,1)
            val buttonsOptions = line.substringAfter("] ").substringBefore(" {")
            // \\( => (
            // \\) => )
            // ([^)]+) => capture everything except ) one or more times
            val buttons = Regex("\\(([^)]+)\\)").findAll(buttonsOptions).map { match ->
                match.groupValues[1].split(",").map { it.trim().toInt() }
            }.toList()

            val numButtons = buttons.size

            val matrix = Array(numLights) { IntArray(numButtons + 1) }

            for (bIndex in buttons.indices) {
                for (lightIndex in buttons[bIndex]) {
                    if (lightIndex < numLights) {
                        matrix[lightIndex][bIndex] = 1
                    }
                }
            }

            for (r in 0 until numLights) {
                matrix[r][numButtons] = state[r]
            }

            var pivotRow = 0
            val pivotColForRow = IntArray(numLights) { -1 } // Which column is the pivot for this row?
            val isFreeVar = BooleanArray(numButtons) { true }

            for (col in 0 until numButtons) {
                if (pivotRow >= numLights) break

                // Find a row with a 1 in this column (from pivotRow downwards)
                var selectedRow = -1
                for (row in pivotRow until numLights) {
                    if (matrix[row][col] == 1) {
                        selectedRow = row
                        break
                    }
                }
                if (selectedRow == -1) continue // No pivot in this column -> Free Variable

                // Swap rows to bring pivot to current position
                val temp = matrix[pivotRow]
                matrix[pivotRow] = matrix[selectedRow]
                matrix[selectedRow] = temp

                // Mark this column as a pivot (not free)
                isFreeVar[col] = false
                pivotColForRow[pivotRow] = col

                // Eliminate 1s in this column for all OTHER rows
                for (row in 0 until numLights) {
                    if (row != pivotRow && matrix[row][col] == 1) {
                        // Row operation: R_i = R_i XOR R_pivot
                        for (k in col..numButtons) {
                            matrix[row][k] = matrix[row][k] xor matrix[pivotRow][k]
                        }
                    }
                }
                pivotRow++
            }

            val freeCols = isFreeVar.indices.filter { isFreeVar[it] }
            val numFree = freeCols.size
            var minSolutionPresses = Int.MAX_VALUE

            // Iterate 2^numFree combinations
            val combinations = 1 shl numFree
            for (mask in 0 until combinations) {
                val currentSolution = IntArray(numButtons)

                // A. Set Free Variables based on the mask
                for (i in 0 until numFree) {
                    if ((mask shr i) and 1 == 1) {
                        currentSolution[freeCols[i]] = 1
                    }
                }
                var possible = true
                for (row in pivotRow - 1 downTo 0) {
                    val pCol = pivotColForRow[row]
                    var requiredVal = matrix[row][numButtons] // Start with target value

                    // XOR with all variables to the right (which we already determined)
                    for (c in pCol + 1 until numButtons) {
                        if (matrix[row][c] == 1) {
                            requiredVal = requiredVal xor currentSolution[c]
                        }
                    }
                    currentSolution[pCol] = requiredVal
                }

                // Check for inconsistency (0 = 1 in rows below pivots)
                for (row in pivotRow until numLights) {
                    if (matrix[row][numButtons] == 1) possible = false
                }

                if (possible) {
                    minSolutionPresses = min(minSolutionPresses, currentSolution.sum())
                }
            }
            if (minSolutionPresses != Int.MAX_VALUE) {
                totalPresses += minSolutionPresses
            }
        }
        return totalPresses

    }

    fun part2(): Long {
        var totalPresses = 0L
        val input = readInput(false, 10, true)

        for ((lineIndex, line) in input.withIndex()) {
            if (line.isBlank()) continue

            // 1. Parse Target
            val targetString = line.substringAfter("{").substringBefore("}")
            val target = targetString.split(",").map { it.trim().toLong() }.toLongArray()
            val numCounters = target.size

            // 2. Parse Buttons
            val buttonsOptions = line.substringAfter("] ").substringBefore(" {")
            val buttons = Regex("\\(([^)]+)\\)").findAll(buttonsOptions).map { match ->
                match.groupValues[1].split(",").map { it.trim().toInt() }
            }.toList()
            val numButtons = buttons.size

            // 3. Build Coefficient Matrix A
            // A[row][col] = 1 if button col affects counter row
            val A = Array(numCounters) { LongArray(numButtons) }
            for (buttonIdx in buttons.indices) {
                for (counterIdx in buttons[buttonIdx]) {
                    if (counterIdx < numCounters) {
                        A[counterIdx][buttonIdx] = 1L
                    }
                }
            }

            // 4. Calculate Upper Bounds for each button (Optimization)
            // A button cannot be pressed more than the limit of any counter it affects.
            // If it affects nothing, set bound to 0 (useless button).
            val buttonBounds = LongArray(numButtons)
            for (j in 0 until numButtons) {
                var minLimit = Long.MAX_VALUE
                var affectsAny = false
                for (i in 0 until numCounters) {
                    if (A[i][j] == 1L) {
                        affectsAny = true
                        if (target[i] < minLimit) {
                            minLimit = target[i]
                        }
                    }
                }
                buttonBounds[j] = if (affectsAny) minLimit else 0L
            }

            // 5. Gaussian Elimination (Integer Preserving)
            val augmented = Array(numCounters) { i ->
                LongArray(numButtons + 1) { j ->
                    if (j < numButtons) A[i][j] else target[i]
                }
            }

            var rank = 0
            val pivotCols = IntArray(numCounters) { -1 }

            for (col in 0 until numButtons) {
                if (rank >= numCounters) break

                var maxRow = rank
                for (row in rank + 1 until numCounters) {
                    if (augmented[row][col].absoluteValue > augmented[maxRow][col].absoluteValue) {
                        maxRow = row
                    }
                }

                if (augmented[maxRow][col] == 0L) continue

                val temp = augmented[rank]
                augmented[rank] = augmented[maxRow]
                augmented[maxRow] = temp

                pivotCols[rank] = col
                val pivotVal = augmented[rank][col]

                for (row in rank + 1 until numCounters) {
                    if (augmented[row][col] != 0L) {
                        val factor = augmented[row][col]
                        // Row operation: R_row = R_row * pivot - R_rank * factor
                        // This avoids floating point arithmetic
                        for (c in 0..numButtons) {
                            augmented[row][c] = augmented[row][c] * pivotVal - augmented[rank][c] * factor
                        }
                    }
                }
                rank++
            }

            // Consistency Check
            var consistent = true
            for (row in rank until numCounters) {
                if (augmented[row][numButtons] != 0L) {
                    consistent = false
                    break
                }
            }

            if (!consistent) {
                println("Warning: Inconsistent system for line $lineIndex")
                continue
            }

            // 6. Identify Free Variables
            val isFree = BooleanArray(numButtons) { true }
            for (i in 0 until rank) {
                if (pivotCols[i] >= 0) isFree[pivotCols[i]] = false
            }
            val freeVars = isFree.indices.filter { isFree[it] }

            var minPresses = Long.MAX_VALUE

            // 7. Recursive Search with Bounds
            fun tryAssignment(freeIdx: Int, assignment: LongArray) {
                // Pruning: if current partial sum exceeds best found, stop (simple optimization)
                if (freeIdx > 0 && assignment.sum() >= minPresses) return

                if (freeIdx == freeVars.size) {
                    val solution = assignment.copyOf()

                    // Back-Substitute for Pivots
                    for (row in rank - 1 downTo 0) {
                        val col = pivotCols[row]
                        if (col < 0) continue

                        var sum = augmented[row][numButtons]
                        for (c in col + 1 until numButtons) {
                            sum -= augmented[row][c] * solution[c]
                        }

                        // Check divisibility and sign
                        val pivotVal = augmented[row][col]
                        if (pivotVal == 0L) continue // Should not happen for pivots

                        if (sum % pivotVal != 0L) return // Not an integer
                        val res = sum / pivotVal

                        if (res < 0) return // Negative presses not allowed
                        solution[col] = res
                    }

                    val currentTotal = solution.sum()
                    if (currentTotal < minPresses) {
                        minPresses = currentTotal
                    }
                    return
                }

                val varIdx = freeVars[freeIdx]
                val limit = buttonBounds[varIdx] // Use the calculated limit!

                for (value in 0L..limit) {
                    assignment[varIdx] = value
                    tryAssignment(freeIdx + 1, assignment)
                }
            }

            val assignment = LongArray(numButtons)
            tryAssignment(0, assignment)

            if (minPresses != Long.MAX_VALUE) {
                totalPresses += minPresses
            } else {
                // This usually means no non-negative integer solution exists
                // println("Warning: No non-negative solution for line $lineIndex")
            }
        }

        return totalPresses
    }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}