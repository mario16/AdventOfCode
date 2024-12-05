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

        List<String> rules = new ArrayList<>();
        List<RuleModel> rulesParsed = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        List<int[]> updatesParsed = new ArrayList<>();

        BufferedReader reader = null;
        try {
            String fileName = "input-day5.txt";
//            String fileName = "sample-day5.txt";
            reader = new BufferedReader(new FileReader(ABSOLUTH_PATH_FOLDER + fileName));
            String line = reader.readLine();

            while (line != null) {

                if (line.contains("|")) {
                    rules.add(line);
                    String[] parts = line.split("\\|");
                    RuleModel ruleModel = new RuleModel(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
                    rulesParsed.add(ruleModel);
                } else {
                    if (!line.isEmpty()) {
                        updates.add(line);
                        String[] parts = line.split(",");
                        int[] updateLine = Arrays.stream(parts).map(String::trim).mapToInt(Integer::parseInt).toArray();
                        updatesParsed.add(updateLine);
                    }
                }



                line = reader.readLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Rules: " + rules);
        System.out.println("RulesParsed: " + rulesParsed);
        System.out.println("Updates: " + updates);
        System.out.println("Updates Parsed: " + updatesParsed);

        // get a list of valid updates
        List<int[]> validUpdates = filterValidUpdates(rulesParsed, updatesParsed);
        System.out.println("valid Updates: " + validUpdates.size());

        int sum = 0;
        for(int[] update : validUpdates) {
            sum += getMiddleValue(update);
        }
        System.out.println("Sum: " + sum);
    }

    private static int getMiddleValue(int[] update) {
        int index = update.length / 2;
        return update[index];
    }

    private static List<int[]> filterValidUpdates(List<RuleModel> rulesParsed, List<int[]> updatesParsed) {

        List<int[]> validUpdates = new ArrayList<>();

        for (int[] update : updatesParsed) {
            if (isValidUpdate(rulesParsed, update)) {
                validUpdates.add(update);
            }
        }

        return validUpdates;
    }

    private static boolean isValidUpdate(List<RuleModel> rulesParsed, int[] update) {
        boolean result = true;
        List<Integer> updateList = Arrays.stream(update).boxed().toList();
        for (RuleModel rule : rulesParsed) {
            if (updateList.contains(rule.getX()) && updateList.contains(rule.getY())) {
                result = result && isBefore(updateList, rule.getX(), rule.getY());
            }
        }
        return result;
    }

    private static boolean isBefore(List<Integer> updateList, int x, int y) {
        return updateList.indexOf(x) < updateList.indexOf(y);
    }

}