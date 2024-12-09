package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String ABSOLUTH_PATH_FOLDER = "/Users/mpaulsen/repos/lagarsoft/AdventOfCode/src/main/java/org/example/";

    private static char[][] alphabetSoup;
    private static int total = 0;


    public static void main(String[] args) {

        List<Long> totals = new ArrayList<>();
        List<List<Long>> operands = new ArrayList<>();


        BufferedReader reader = null;
        try {
            String fileName = "input-day7.txt";
//            String fileName = "sample-day7.txt";
            reader = new BufferedReader(new FileReader(ABSOLUTH_PATH_FOLDER + fileName));
            String line = reader.readLine();

            while (line != null) {

                var parts = line.split(":");
                totals.add(Long.parseLong(parts[0].trim()));

                var operandsList = new ArrayList<Long>();

                var parts2 = parts[1].split(" ");

                for (String part : parts2) {
                    if (!part.isEmpty()) {
                        operandsList.add(Long.parseLong(part.trim()));
                    }
                }
                operands.add(operandsList);

                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("totals: " + totals);
        System.out.println("operands: " + operands);

        // get a list of valid equations
        List<Long> validEquations = filterValidEquations(totals, operands);

        // sum of all the items of validEquations
        long sum = validEquations.stream().map(Long::longValue).reduce(0L, Long::sum);
        System.out.println("Sum: " + sum);

    }

    private static List<Long> filterValidEquations(List<Long> totals, List<List<Long>> operands) {
        List<Long> validEquations = new ArrayList<>();

        for (List<Long> operandList : operands) {
            int i = operands.indexOf(operandList);
            if (isValidEquation(totals.get(i), operandList, 0)) {
                validEquations.add(totals.get(i));
            }
        }
        return validEquations;
    }

    private static boolean isValidEquation(long total, List<Long> operands, long accumulator) {
        if (operands.isEmpty()) {
            return accumulator == total;
        }
        long accumulator2 = accumulator;
        accumulator += operands.get(0);
        boolean result =  isValidEquation(total, operands.subList(1, operands.size()), accumulator);

        accumulator2 = accumulator2 * operands.get(0);
        boolean result2 = isValidEquation(total, operands.subList(1, operands.size()), accumulator2);

        return result || result2;
    }

}