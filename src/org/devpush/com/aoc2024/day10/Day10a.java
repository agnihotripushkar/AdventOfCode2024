package org.devpush.com.aoc2024.day10;

import org.devpush.com.aoc2024.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10a {
    public static void main(String[] args) throws Exception {
        List<String> lines = Utils.readInput(false, 10, false);

        int height = lines.size();
        int width = lines.get(0).length();

        int[][] map = Utils.convertListToIntMatrix(lines);

        long count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Set<Point> set = countTrails(map, x, y, width, height, 0);
                count += set.size();
            }
        }
        System.out.println(count);
    }

    private static Set<Point> countTrails(int[][] map, int x, int y, int width, int height, int val) {
        if (isSafe(map, x, y)) {
            if (map[x][y] != val) return new HashSet<>();
            if (val == 9) {
                Set<Point> newSet = new HashSet();
                newSet.add(new Point(9, x, y));
                return newSet;
            }
            Set<Point> newSet = new HashSet<>();
            newSet.addAll(countTrails(map, x + 1, y, width, height, val + 1));
            newSet.addAll(countTrails(map, x - 1, y, width, height, val + 1));
            newSet.addAll(countTrails(map, x, y + 1, width, height, val + 1));
            newSet.addAll(countTrails(map, x, y - 1, width, height, val + 1));
            return newSet;
        } else return new HashSet<>();

    }

    private static boolean isSafe(int[][] map, int x, int y) {
        int height = map.length;
        int width = map[0].length;
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    //1561 too high
    // Point.hashCode() method was the culprit

}
