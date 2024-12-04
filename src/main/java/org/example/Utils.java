package org.example;

import java.util.List;

public class Utils {

    public static char[][] convertToCharArray(List<List<Character>> charList) {
        // Initialize a 2D char array
        int rows = charList.size();
        int cols = charList.get(0).size();
        char[][] charArray = new char[rows][cols];

        // Populate the 2D char array
        for (int i = 0; i < rows; i++) {
            List<Character> row = charList.get(i);
            for (int j = 0; j < cols; j++) {
                charArray[i][j] = row.get(j);
            }
        }

        return charArray;
    }

}
