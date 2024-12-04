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

        int count = isPresent("XMAS");

        System.out.println("Total words: " + total);
        System.out.println("Count: " + count);

    }
    
    public static int isPresent(String word) {
        int count = 0;
        int iter = 0;
        for (int i = 0; i < alphabetSoup.length; i++) {
            for (int j = 0; j < alphabetSoup[0].length; j++) {
                iter++;
                if (searchWordFromPosition(i, j, word, 0, true, DirectionEnum.FORWARD)) {
                    count++;
                }
            }
        }
        System.out.println("Iterations: " + iter);
        return count;
    }

    private static boolean searchWordFromPosition(int x, int y, String word, int index, boolean firstTime, DirectionEnum direction) {
        if (index == word.length()) {
            return true;
        }

        if (x < 0 || y < 0 || x >= alphabetSoup.length || y >= alphabetSoup[0].length || alphabetSoup[x][y] != word.charAt(index)) {
            return false;
        }

        boolean result;
        if (firstTime) {
            boolean a = searchWordFromPosition(x + 1, y, word, index + 1, false, DirectionEnum.FORWARD);
            boolean b = searchWordFromPosition(x - 1, y, word, index + 1, false, DirectionEnum.BACKWARD);
            boolean c = searchWordFromPosition(x, y + 1, word, index + 1, false, DirectionEnum.UP);
            boolean d = searchWordFromPosition(x, y - 1, word, index + 1, false, DirectionEnum.DOWN);

            boolean e = searchWordFromPosition(x + 1, y + 1, word, index + 1, false, DirectionEnum.UP_RIGHT);
            boolean f = searchWordFromPosition(x - 1, y - 1, word, index + 1, false, DirectionEnum.DOWN_LEFT);
            boolean g = searchWordFromPosition(x - 1, y + 1, word, index + 1, false, DirectionEnum.UP_LEFT);
            boolean h = searchWordFromPosition(x + 1, y - 1, word, index + 1, false, DirectionEnum.DOWN_RIGHT);

            if (a) total++;
            if (b) total++;
            if (c) total++;
            if (d) total++;
            if (e) total++;
            if (f) total++;
            if (g) total++;
            if (h) total++;

            result = a || b || c || d || e || f || g || h;
        } else {
            result = switch (direction) {
                case FORWARD -> searchWordFromPosition(x + 1, y, word, index + 1, false, DirectionEnum.FORWARD);
                case BACKWARD -> searchWordFromPosition(x - 1, y, word, index + 1, false, DirectionEnum.BACKWARD);
                case UP -> searchWordFromPosition(x, y + 1, word, index + 1, false, DirectionEnum.UP);
                case DOWN -> searchWordFromPosition(x, y - 1, word, index + 1, false, DirectionEnum.DOWN);

                case UP_RIGHT -> searchWordFromPosition(x + 1, y + 1, word, index + 1, false, DirectionEnum.UP_RIGHT);
                case DOWN_LEFT -> searchWordFromPosition(x - 1, y - 1, word, index + 1, false, DirectionEnum.DOWN_LEFT);
                case DOWN_RIGHT -> searchWordFromPosition(x + 1, y - 1, word, index + 1, false, DirectionEnum.DOWN_RIGHT);
                case UP_LEFT -> searchWordFromPosition(x - 1, y + 1, word, index + 1, false, DirectionEnum.UP_LEFT);
                default -> throw new RuntimeException("Invalid direction");
            };

        }
        return result;
    }

}