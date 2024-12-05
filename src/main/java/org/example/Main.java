package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int safeReportsCount = 0;

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/input.txt"));
//            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/sample.txt"));
            String line = reader.readLine();

            while (line != null) {

                int[] numbers = convertToIntArray(line);

                boolean validLine = checkLine(numbers);
                if (validLine) {
                    safeReportsCount++;
                } else {
                    // remove items by one and checkLine again. If valid, increment safeReportsCount
                    int i = 0;
                    boolean valid = false;
                    while (!valid && i < numbers.length) {

                        int[] newNumbers = new int[numbers.length - 1];
                        int index = 0;
                        for (int j = 0; j < numbers.length; j++) {
                            if (j != i) {
                                newNumbers[index] = numbers[j];
                                index++;
                            }
                        }
                        if (checkLine(newNumbers)) {
                            valid = true;
                        }
                        i++;
                    }
                    if (valid) {
                        safeReportsCount++;
                    }
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Safe reports count: " + safeReportsCount);

    }

    private static int[] convertToIntArray(String input) {
        // Split the input string by spaces
        String[] parts = input.trim().split("\\s+");

        // Convert each part to an integer and store it in an array
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }

    private static boolean checkLine(int[] line) {
        boolean valid = true;

        int currentValue = line[0];
        int nextValue;
        boolean firstTime = true;
        boolean sortAsc = false;
        boolean sortDesc = false;
        int i = 1;
        while (valid && i < line.length) {
            nextValue = line[i];
            if (firstTime) {
                if (currentValue < nextValue) {
                    sortAsc = true;
                } else {
                    sortDesc = true;
                }
                firstTime = false;
            }

            if (currentValue < nextValue) {
                valid = sortAsc && (nextValue - currentValue >= 1) && (nextValue - currentValue <= 3);
            } else if (currentValue > nextValue) {
                valid = sortDesc && (currentValue - nextValue >= 1) && (currentValue - nextValue <= 3);
            } else {
                valid = false;
            }

            // prepare for next value
            currentValue = nextValue;
            i++;
        }
        return valid;
    }

}