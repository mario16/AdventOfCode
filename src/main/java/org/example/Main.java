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


        List<int[]> invalidUpdates = filterInvalidUpdates(rulesParsed, updatesParsed);
        List<int[]> correctedUpdates = correctInvalidUpdates(rulesParsed, invalidUpdates);

        int sumCorrectedUpdates = 0;
        for(int[] update : correctedUpdates) {
            sumCorrectedUpdates += getMiddleValue(update);
        }
        System.out.println("sumCorrectedUpdates: " + sumCorrectedUpdates);
    }

    private static List<int[]> correctInvalidUpdates(List<RuleModel> rulesParsed, List<int[]> invalidUpdates) {
        List<int[]> correctedUpdates = new ArrayList<>();
        for (int[] update : invalidUpdates) {
            int[] correctedUpdate = correctUpdate(rulesParsed, update);
            correctedUpdates.add(correctedUpdate);
        }
        return correctedUpdates;
    }

    private static int[] correctUpdate(List<RuleModel> rulesParsed, int[] update) {
        // build a new int[]
        // iterate over rules
        // if x and y are in the list, put x before y in the corrected list
        // the problem to solve is to validate the new position again with the all the rules

        List<Integer> correctedUpdate = new ArrayList<>();
        List<Integer> originalUpdate = Arrays.stream(update).boxed().toList();

        for (int i = 0; i < rulesParsed.size(); i++) {
            RuleModel rule = rulesParsed.get(i);
            if (rule.getX() == 75 && rule.getY() == 61){
                System.out.println("here");
            }
            if (originalUpdate.contains(rule.getX()) && originalUpdate.contains(rule.getY())) {
                correctedUpdate = insertOrReorder(correctedUpdate, rule.getX(), rule.getY(), rulesParsed);
            }
        }

        return Utils.convertToIntArray(correctedUpdate);
    }

    private static List<Integer> insertOrReorder(List<Integer> correctedUpdate, int x, int y, List<RuleModel> rulesParsed) {
        if (!correctedUpdate.contains(x) && !correctedUpdate.contains(y)) {

            // look for the right index between index of 1 and the end
            int newIndexY = 0;
            for (int i = 0; i < correctedUpdate.size(); i++) {
                if (existRule(correctedUpdate.get(i), y, rulesParsed)) {
                    newIndexY = i;
                }
            }
            correctedUpdate.add(newIndexY, y);

            correctedUpdate.add(0, x);
        } else if (!correctedUpdate.contains(x)) {
            correctedUpdate.add(correctedUpdate.indexOf(y), x);
        } else if (!correctedUpdate.contains(y)) {
            correctedUpdate.add(correctedUpdate.indexOf(x)+1, y);
        } else {
            // reorder
            int xIndex = correctedUpdate.indexOf(x);
            int yIndex = correctedUpdate.indexOf(y);
            if (xIndex > yIndex) {
                correctedUpdate.remove(xIndex);

                // look for the right index between index of x and (index of y) -1
                int newIndexX = yIndex;
                for (int i = xIndex-1; i >= 0; i--) {
                    if (existRule(x, correctedUpdate.get(i), rulesParsed)) {
                        newIndexX = i;

                    }
                }
                correctedUpdate.add(newIndexX, x);
            }
        }

        // validate the rest of the rules
        return correctedUpdate;
    }

    private static boolean existRule(int x, int y, List<RuleModel> rulesParsed) {
        return rulesParsed.stream().anyMatch(rule -> rule.getX() == x && rule.getY() == y);
    }

    private static List<int[]> filterInvalidUpdates(List<RuleModel> rulesParsed, List<int[]> updatesParsed) {
        List<int[]> invalidUpdates = new ArrayList<>();
        for (int[] update : updatesParsed) {
            if (!isValidUpdate(rulesParsed, update)) {
                invalidUpdates.add(update);
            }
        }
        return invalidUpdates;
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