package org.example;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String ABSOLUTE_PATH_FOLDER = "/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/";

    public static void main(String[] args) {

        BufferedReader reader = null;
        long total = 0;
        try {
            String fileName = "input-day3.txt";
//            String fileName = "sample-day3.txt";
            reader = new BufferedReader(new FileReader(ABSOLUTE_PATH_FOLDER + fileName));
            String line = reader.readLine();

            while (line != null) {

                total += scanLine(line);

                line = reader.readLine();
            }

            reader.close();

            System.out.println("total: " + total);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static long runTest(String regex, String text) {
        long total = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            long x = Long.parseLong(matcher.group(1)); // Capture X
            long y = Long.parseLong(matcher.group(2)); // Capture Y
            total += x * y;
        }
        return total;
    }

    private static long scanLine(String line) {
        String regex = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)";
        return runTest(regex, line);
    }

}