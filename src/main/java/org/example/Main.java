package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int total = 0;
        int currentValue;
        int nextValue;

        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        BufferedReader reader = null;
        try {
//            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/sample-day1.txt"));
            reader = new BufferedReader(new FileReader("/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/input-day1.txt"));
            String line = reader.readLine();

            while (line != null) {

                // Use Scanner to process the line
                Scanner scanner = new Scanner(line);

                if (!scanner.hasNext()) {
                    return;
                }

                currentValue = Integer.parseInt(scanner.next());
                list.add(currentValue);
                nextValue = Integer.parseInt(scanner.next());
                list2.add(nextValue);

                scanner.close();

                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int item : list) {
            // count the number of times this value appears in the list2
            int numberOfTimes = list2.stream().filter(x -> x == item).mapToInt(x -> 1).sum();

            int difference = item * numberOfTimes;
            total += difference;
        }

        System.out.println("Total distance: " + total);

    }


}