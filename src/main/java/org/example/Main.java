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

        list.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);

        for (int i = 0; i < list.size(); i++) {
            int item = list.get(i);
            int item2 = list2.get(i);
            int difference = 0;
            if (item > item2) {
                difference = item - item2;
            } else if (item < item2) {
                difference = item2 - item;
            }
            total += difference;
        }

        System.out.println("Total distance: " + total);

    }


}