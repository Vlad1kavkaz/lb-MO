package org.lb.lb3;

import java.util.ArrayList;
import java.util.Map;

public class CriteriaCombination {


    public static String runCriteriaCombination(double[][] A,
                                                Map<String, Double> markCriteria,
                                                String[] alternative) {
        double[][] normalizeMatrix = normalizeMatrix(A);
        int columns = A[0].length;

        ArrayList<Double> weight = getWeight(columns, markCriteria);
        ArrayList<Double> normalizeWeight = normalizeWeight(weight);

        double[] multiplyResult = multiplyMatrixAndWeight(normalizeMatrix, normalizeWeight);

        int indexMax = findMaxIndex(multiplyResult);

        return alternative[indexMax];
    }

    public static int findMaxIndex(double[] array) {
        double max = Double.NEGATIVE_INFINITY;
        int maxIndex = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }


    public static double[] multiplyMatrixAndWeight(double[][] matrix, ArrayList<Double> weight) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (cols != weight.size()) {
            throw new IllegalArgumentException("Несоответствие размеров матрицы и веса");
        }

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            double sum = 0.0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * weight.get(j);
            }
            result[i] = sum;
        }

        return result;
    }


    public static ArrayList<Double> normalizeWeight(ArrayList<Double> weight) {
        ArrayList<Double> normalize = new ArrayList<>();
        double sum = 0.0;
        for (double w : weight) {
            sum += w;
        }
        for (double w : weight) {
            normalize.add(w/sum);
        }
        return normalize;
    }

    public static ArrayList<Double> getWeight(int columns,
                                              Map<String, Double> markCriteria) {

        ArrayList<Double> weight = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            double a = 0;
            for (int j = 0; j < columns; j++) {
                if (i != j) {
                    String key = i + 1 + Integer.toString(j+1);
                    a += markCriteria.get(key);
                }
            }
            weight.add(a);
        }
        return weight;
    }

    public static double[][] normalizeMatrix(double[][] A) {
        int columns = A[0].length;

        for (int j = 0; j < columns; j++) {
            double sum = sumColumn(A, j);
            for (int i = 0; i < A.length; i++) {
                A[i][j] /= sum;
            }
        }

        return A;
    }

    public static double sumColumn(double[][] A, int j) {
        double sum = 0;
        for (double[] doubles : A) {
            sum += doubles[j];
        }
        return sum;
    }
}
