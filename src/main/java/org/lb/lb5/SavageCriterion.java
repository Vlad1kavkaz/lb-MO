package org.lb.lb5;

import java.util.ArrayList;
import java.util.List;

public class SavageCriterion {
    private SavageCriterion() {

    }

    public static int checkSavageCriterion(int[][] strategies) {
        //Создаем новую матрицу для оценки рисков
        int[][] matrixOfRisk = new int[strategies.length][strategies[0].length];

        for (int i = 0; i < strategies.length; i++) {
            for (int j = 0; j < strategies[0].length; j++) {
                matrixOfRisk[i][j] = findMaxInColumn(strategies, j) - strategies[i][j];
            }
        }

        printMatrixWithLabels(matrixOfRisk);

        //Пустой список для поиска максимума в строке
        List<Integer> maximumOfLine = new ArrayList<>();

        for (int[] lineOfRisk : matrixOfRisk) {
            int maximalValue = Integer.MIN_VALUE;
            for (int element : lineOfRisk) {
                if (element > maximalValue) {
                    maximalValue = element;
                }
            }
            maximumOfLine.add(maximalValue);
        }

        //Создаем переменные для индекса максимального значения и максимума
        int maxValue = Integer.MAX_VALUE;
        int indexOfMax = -1;

        for (int i = 0; i < maximumOfLine.size(); i++) {
            if (maximumOfLine.get(i) < maxValue) {
                maxValue = maximumOfLine.get(i);
                indexOfMax = i;
            }
        }

        return indexOfMax;
    }

    private static int findMaxInColumn(int[][] matrix, int columnIndex) {
        int maxInColumn = Integer.MIN_VALUE;

        for (int[] ints : matrix) {
            int value = ints[columnIndex];
            if (value > maxInColumn) {
                maxInColumn = value;
            }
        }

        return maxInColumn;
    }

    private static void printMatrixWithLabels(int[][] matrix) {
        System.out.println("------------------------------");
        System.out.printf("|    | %-3s | %-3s | %-3s | %-3s |\n", "b1", "b2", "b3", "b4");
        System.out.println("------------------------------");
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("| a%-1s |", i);
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf(" %-3s |", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }

}
