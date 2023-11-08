package org.lb.lb3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class CriteriaCombination {


    public static String runCriteriaCombination(double[][] A,
                                                Map<String, Double> markCriteria,
                                                String[] alternative) {
        //Нормализуем матрицу
        double[][] normalizeMatrix = normalizeMatrix(A);
        System.out.println("Нормализованная матрица А:");
        for (double[] doubles : normalizeMatrix) {
            System.out.println(Arrays.toString(doubles));
        }
        int columns = A[0].length;

        //Получаем αᵢ для всего
        ArrayList<Double> weight = getWeight(columns, markCriteria);
        System.out.println("Вектор α: " + weight);
        //Нормализуем этот вектор
        ArrayList<Double> normalizeWeight = normalizeWeight(weight);
        System.out.println("Нормализованный вектор α: " + normalizeWeight);


        //Перемножаем полученные нормализованные матрицы
        double[] multiplyResult = multiplyMatrixAndWeight(normalizeMatrix, normalizeWeight);
        System.out.println("Произведение нормализованных матриц: " + Arrays.toString(multiplyResult));

        //Ищем максимальный индекс
        int indexMax = findMaxIndex(multiplyResult);
        System.out.println("Индекс максимального элемента: " + indexMax);

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
    public static void main(String [] args) {
        //Карта для γ
        Map<String, Double> markCritery = new HashMap<>();
        markCritery.put("12", 0.5);
        markCritery.put("13", 1.0);
        markCritery.put("14", 1.0);
        markCritery.put("21", 0.5);
        markCritery.put("23", 1.0);
        markCritery.put("24", 1.0);
        markCritery.put("31", 0.0);
        markCritery.put("32", 0.0);
        markCritery.put("34", 1.0);
        markCritery.put("41", 0.0);
        markCritery.put("42", 0.0);
        markCritery.put("43", 0.0);

        //Рейтинг альтернатив по критериям
        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };

        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};


        String result = runCriteriaCombination(A, markCritery, alternative);

        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        System.out.println(GREEN + "Результат работы программы." + RESET);
        System.out.println("Лучший выбор: "+result);

    }
}