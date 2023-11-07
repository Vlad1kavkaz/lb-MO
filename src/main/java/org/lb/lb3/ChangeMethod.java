package org.lb.lb3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeMethod {
    private ChangeMethod() {

    }

    public static String runChengeMethod(double[][] A,
                                      int[] weigth,
                                      double[] minimalValue,
                                      String[] alternative) {

        double[] normalWeigth = normalizeWeigth(weigth);
        int columns = A[0].length;

        int index = -1;
        for (int i = 0; i < minimalValue.length; i++) {
            if (minimalValue[i] == 1) {
                index = i;
                break;
            }
        }


        double[] maxFound = new double[A.length];
        for (int j = 0; j < columns; j++) {
            maxFound[j] = foundMax(A, j);
        }
        double[] minFound = new double[A.length];
        for (int j = 0; j < columns; j++) {
            minFound[j] = foundMin(A, j);
        }


        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < columns; j++) {
                if (j != index) {
                    A[i][j] = (A[i][j] - minFound[j])/(maxFound[j] - minFound[j]);
                }
            }
        }


        for (int j = 0; j < columns; j++) {
            maxFound[j] = foundMax(A, j);
        }
        for (int j = 0; j < columns; j++) {
            minFound[j] = foundMin(A, j);
        }

        ArrayList<Integer> indexes = new ArrayList<>();
        //проверим минимаьльное значение для условий
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == maxFound[j] && A[i][j] >= maxFound[j] * minimalValue[j]) {
                    indexes.add(i);
                    break;
                }
            }
        }

        //теперь в indexes есть интексы строк, которые удоавлетворяют минимальным критериям
        //дальше надо посчитать значение на нормализованный вес и отдать максимум

        Map<Integer, Double> value = new HashMap<>();
        for (int i : indexes) {
            double val = 0;
            boolean hasZero = false; // Флаг для проверки наличия нулевых элементов
            for (int j = 0; j < columns; j++) {
                if (j != index) {
                    if (A[i][j] == 0) {
                        hasZero = true; // Если есть нулевой элемент, устанавливаем флаг
                        break;
                    }
                    val += A[i][j] * normalWeigth[j];
                }
            }

            if (!hasZero) {
                value.put(i, val); // Добавляем в карту только если нет нулевых элементов
            }
        }

        // Найти максимальное значение и соответствующий индекс
        double maxVal = Double.NEGATIVE_INFINITY;
        int maxIndex = -1;
        for (Map.Entry<Integer, Double> entry : value.entrySet()) {
            if (entry.getValue() > maxVal) {
                maxVal = entry.getValue();
                maxIndex = entry.getKey();
            }
        }

        return alternative[maxIndex];
    }


    public static double foundMax(double[][] A, int j) {
        double max = Double.NEGATIVE_INFINITY; // использование Double.NEGATIVE_INFINITY вместо Integer.MIN_VALUE для вещественных чисел
        for (double[] doubles : A) {
            if (doubles[j] > max) {
                max = doubles[j];
            }
        }
        return max;
    }

    public static double foundMin(double[][] A, int j) {
        double min = Double.POSITIVE_INFINITY; // использование Double.POSITIVE_INFINITY вместо Integer.MAX_VALUE для вещественных чисел
        for (double[] doubles : A) {
            if (doubles[j] < min) {
                min = doubles[j];
            }
        }
        return min;
    }


    public static double[] normalizeWeigth (int[] weigth) {
        double sum = 0.0;
        for (double num : weigth) {
            sum += num;
        }

        // Создать массив для нормализованных весов
        double[] normalizedWeights = new double[weigth.length];

        // Нормализовать веса
        for (int i = 0; i < weigth.length; i++) {
            normalizedWeights[i] = (double) weigth[i] / sum;
        }

        return normalizedWeights;
    }
}
