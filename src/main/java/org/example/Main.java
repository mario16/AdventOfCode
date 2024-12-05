package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static char[][] alphabetSoup;
    private static int total = 0;

    public static void main(String[] args) {

        List<List<Character>> rows = new ArrayList<>();

        BufferedReader reader = null;
        try {
//            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/sample-day4.txt"));
            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/input-day4.txt"));
            String line = reader.readLine();

            while (line != null) {

                List<Character> columns = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    columns.add(c);
                }
                rows.add(columns);

                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // the alphabet must be the characters read from the input
        alphabetSoup = Utils.convertToCharArray(rows);

        isPresent();

    }

    public static char[][] getSubMatrix(char[][] matrix, int startRow, int startCol, int subRows, int subCols) {
        // Initialize the submatrix
        char[][] subMatrix = new char[subRows][subCols];

        // Copy elements into the submatrix
        for (int i = 0; i < subRows; i++) {
            for (int j = 0; j < subCols; j++) {
                subMatrix[i][j] = matrix[startRow + i][startCol + j];
            }
        }

        return subMatrix;
    }

    public static int isPresent() {
        int count = 0;
        int iter = 0;
        int countMatrix = 0;
        for (int i = 0; i < alphabetSoup.length; i++) {
            for (int j = 0; j < alphabetSoup[0].length; j++) {
                iter++;

                if (i + 3 <= alphabetSoup.length && (j + 3 <= alphabetSoup[0].length)) {
                    countMatrix++;
                    char[][] matrix = getSubMatrix(alphabetSoup, i, j, 3, 3);
                    if ((searchWordFromPositionWithMatrix(0, 0, "MAS", 0, DirectionEnum.DOWN_RIGHT, matrix) &&
                        searchWordFromPositionWithMatrix(2, 0, "SAM", 0, DirectionEnum.DOWN_LEFT, matrix))
                        ||
                        (searchWordFromPositionWithMatrix(0, 0, "SAM", 0, DirectionEnum.DOWN_RIGHT, matrix) &&
                            searchWordFromPositionWithMatrix(2, 0, "MAS", 0, DirectionEnum.DOWN_LEFT, matrix))
                        ||
                        (searchWordFromPositionWithMatrix(0, 0, "MAS", 0, DirectionEnum.DOWN_RIGHT, matrix) &&
                            searchWordFromPositionWithMatrix(2, 0, "MAS", 0, DirectionEnum.DOWN_LEFT, matrix))
                        ||
                        (searchWordFromPositionWithMatrix(0, 0, "SAM", 0, DirectionEnum.DOWN_RIGHT, matrix) &&
                            searchWordFromPositionWithMatrix(2, 0, "SAM", 0, DirectionEnum.DOWN_LEFT, matrix))
                        ) {
                        count++;
                    }
                }

            }
        }
        System.out.println("Iterations: " + iter);
        System.out.println("Count Matrix: " + countMatrix);
        System.out.println("Count: " + count);
        return count;
    }

    private static boolean searchWordFromPositionWithMatrix(int x, int y, String word, int index, DirectionEnum direction,
        char[][] matrix) {

        if (index == word.length()) {
            return true;
        }

        if (x < 0 || y < 0 || x >= matrix.length || y >= matrix[0].length || matrix[x][y] != word.charAt(index)) {
            return false;
        }

        switch (direction) {
            case DOWN_RIGHT -> {
                return searchWordFromPositionWithMatrix(x + 1, y + 1, word, index + 1, DirectionEnum.DOWN_RIGHT, matrix);
            }
            case DOWN_LEFT -> {
                return searchWordFromPositionWithMatrix(x - 1, y + 1, word, index + 1, DirectionEnum.DOWN_LEFT, matrix);
            }
            default -> throw new RuntimeException("Invalid direction");
        }
    }

}