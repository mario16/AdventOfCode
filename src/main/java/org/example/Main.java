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
            StringBuilder allLines = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                allLines.append(line);
                line = reader.readLine();
            }

            String allText = allLines.toString();

            total = scanLine(allText);

            System.out.println("total: " + total);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static long runTest(String regex, String text) {
        long total = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            long x = Long.parseLong(matcher.group(1)); // Capture X
            long y = Long.parseLong(matcher.group(2)); // Capture Y

            int xIndex = matcher.start(1); // Start index of X
            if (isEnabled(xIndex, text)) {
                total += x * y;
            }
        }
        return total;
    }

    private static boolean isEnabled(int xIndex, String text) {

            boolean isDisabled = false;

            // look for the word don't() followed by do() in the text
            String regex = "don't\\(\\)(.*?)do\\(\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            boolean foundRegex = false;
            while (matcher.find()) {
                foundRegex = true;
                int startDisabled = matcher.start(1);
                int endDisabled = matcher.end(1);
                isDisabled = xIndex > startDisabled && xIndex < endDisabled;
                if (isDisabled) {
                    return false;
                }
            }

            if (!foundRegex) {

                // look for the word don't() without a following do() in the text
                regex = "don't\\(\\)(.*)";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(text);

                while (matcher.find()) {
                    int startDisabled = matcher.start(1);
                    isDisabled = xIndex > startDisabled;
                    if (isDisabled) {
                        return false;
                    }
                }
            }
            return true;
    }

    private static long scanLine(String line) {
        String regex = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)";
        return runTest(regex, line);
    }

}