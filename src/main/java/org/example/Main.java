package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int safeReportsCount = 0, currentValue, nextValue;
        boolean sortAsc = false, sortDesc = false, firstTime = true, valid = true;

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/input.txt"));
//            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/sample2.txt"));
            String line = reader.readLine();

            while (line != null) {

                // Use Scanner to process the line
                Scanner scanner = new Scanner(line);

                if (!scanner.hasNext()) {
                    return;
                }

                currentValue = Integer.parseInt(scanner.next());
                while (valid && scanner.hasNext()) {
                    nextValue = Integer.parseInt(scanner.next());
                    if (firstTime) {
                        if (currentValue < nextValue) {
                            sortAsc = true;
                            sortDesc = false;
                        } else if (currentValue > nextValue) {
                            sortAsc = false;
                            sortDesc = true;
                        } else {
                            valid = false;
                        }
                        firstTime = false;
                    }

                    if (currentValue < nextValue) {
                        if (sortAsc && (nextValue-currentValue >= 1) && (nextValue-currentValue <= 3)) {
                            valid = true;
                        } else {
                            valid = false;
                        }
                    } else if (currentValue > nextValue) {
                        if (sortDesc && (currentValue-nextValue >= 1) && (currentValue-nextValue <= 3)) {
                            valid = true;
                        } else {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }

                    // prepare for next value
                    currentValue = nextValue;
                }
                if (valid) {
                    safeReportsCount++;
                } else {
                    valid = true;
                }
                // prepare for next line
                firstTime = true;

                scanner.close();

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Safe reports count: " + safeReportsCount);

    }


}