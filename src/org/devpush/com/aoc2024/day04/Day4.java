package org.devpush.com.aoc2024.day04;

import org.devpush.com.aoc2024.Utils;
import java.io.IOException;
import java.util.List;

public class Day4 {

    public static void main(String[] args) throws IOException {
        firstStar();
        secondStar();
    }

    private static void firstStar() throws IOException {
        Helper hp = new Helper();
        List<String> lines = Utils.readInput(false, 4, false);
        int rows = lines.size();
        int columns = lines.get(0).length();
        int counter = 0;
        char[][] matrix = Utils.convertListToMatrix(lines);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 'X') {
                    int temp = hp.DFS(i, j, matrix);
                    counter += temp;
                }
            }
        }
        System.out.println(counter);
    }

    private static void secondStar() throws IOException {
        List<String> lines = Utils.readInput(false, 4, true);
        int rows = lines.size();
        int columns = lines.get(0).length();
        int counter = 0;
        Helper hp = new Helper();
        char[][] matrix = Utils.convertListToMatrix(lines);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 'A') { // Center of the pattern
                    if (hp.isPatternFound(i, j, matrix)) {
                        counter++;
                    }
                }
            }
        }
        System.out.println(counter);
    }

}
