package org.example;

import java.io.*;
import java.util.*;

public class Main {

    private static final String ABSOLUTH_PATH_FOLDER = "/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/";

    private static final Map<Integer, Map<String, Long>> cache = new HashMap<>();

    public static void main(String[] args) {

        BufferedReader reader = null;
        try {
            String fileName = "input-day11.txt";
//            String fileName = "sample-day11.txt";
            reader = new BufferedReader(new FileReader(ABSOLUTH_PATH_FOLDER + fileName));
            String line = reader.readLine();
            reader.close();

            var stones = line.split(" ");

            int blinkCount = 75;
            long result = blinkOnce(Arrays.asList(stones), blinkCount);

            System.out.println("result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static long blinkOnce(List<String> stones, int pendingBlinks) {
        if (pendingBlinks == 0) {
            return stones.size();
        }
        long result = 0;

        if (!stones.isEmpty()) {
            String nextValue = stones.get(0);
            long value = getValue(pendingBlinks, nextValue);
            if (value != -1) {
                result += value;
            } else {
                List<String> newList = applyRules(nextValue);
                result += blinkOnce(newList, pendingBlinks - 1);
                saveValue(pendingBlinks, stones.get(0), result);
            }

            result += blinkOnce(stones.subList(1, stones.size()), pendingBlinks);
        }
        return result;

    }

    private static void saveValue(int pendingBlinks, String s, long result) {
        if (!cache.containsKey(pendingBlinks)) {
            cache.put(pendingBlinks, new HashMap<>());
        } else {
            if (cache.get(pendingBlinks).containsKey(s)) {
                return;
            }
        }
        cache.get(pendingBlinks).put(s, result);
    }

    private static long getValue(int pendingBlinks, String s) {
        if (!cache.containsKey(pendingBlinks)) {
            return -1;
        }
        if (!cache.get(pendingBlinks).containsKey(s)) {
            return -1;
        }
        return cache.get(pendingBlinks).get(s);
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