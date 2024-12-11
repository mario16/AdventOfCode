package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String ABSOLUTH_PATH_FOLDER = "/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/";

    private static List<String> newStones = new ArrayList<>();

    public static void main(String[] args) {

        BufferedReader reader = null;
        try {
            String fileName = "input-day11.txt";
//            String fileName = "sample-day11.txt";
            reader = new BufferedReader(new FileReader(ABSOLUTH_PATH_FOLDER + fileName));
            String line = reader.readLine();

            while (line != null) {

                var stones = line.split(" ");

                int blinkCount = 25;
                for (int i = 0; i < blinkCount; i++) {
                    System.out.println("blink #" + (i+1+""));
                    for (String part : stones) {
                        newStones.addAll(applyRules(part));
                    }
                    stones = newStones.toArray(new String[0]);
                    newStones.clear();
                    System.out.println("stones: " + Arrays.toString(stones));
                }

                System.out.println("# stones: " + stones.length);

                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<String> applyRules(String stone) {
        List<String> result = new ArrayList<>();
        if (stone.equals("0")) {
            result.add("1");
            return result;
        }
        int digitCount = stone.length();
        if (digitCount % 2 == 0) {
            var half = digitCount / 2;
            long split = Long.parseLong(stone.substring(0, half));
            long split2 = Long.parseLong(stone.substring(half, digitCount));
            result.add(split + "");
            result.add(split2 + "");
            return result;
        }
        long stoneNumber = Long.parseLong(stone);
        stoneNumber = stoneNumber * 2024;
        result.add(stoneNumber + "");
        return result;
    }


}