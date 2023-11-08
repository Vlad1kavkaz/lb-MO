package org.lb.lb3;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("Нормализованный вектор весов: "+Arrays.toString(normalWeigth));
        int columns = A[0].length;

        //Поиск индекса главного критерия
        int index = -1;
        for (int i = 0; i < minimalValue.length; i++) {
            if (minimalValue[i] == 1) {
                index = i;
                break;
            }
        }

        //Поиск максимума и минимума столбцов
        double[] maxFound = new double[A.length];
        for (int j = 0; j < columns; j++) {
            maxFound[j] = foundMax(A, j);
        }
        double[] minFound = new double[A.length];
        for (int j = 0; j < columns; j++) {
            minFound[j] = foundMin(A, j);
        }

        System.out.println("Максимальные и минимальные элементы столбцов:\n" +
                "Максимумы: " +
                Arrays.toString(maxFound) +
                "\nМинимумы: " +
                Arrays.toString(minFound));

        //Нормировка матрицы
        System.out.println("Нормированная матрица А:");
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < columns; j++) {
                if (j != index) {
                    A[i][j] = (A[i][j] - minFound[j]) / (maxFound[j] - minFound[j]);
                }

            }
        }

        for (double[] doubles : A) {
            System.out.println(Arrays.toString(doubles));
        }

        //Повторно ищем максимумы и минимумы столбцов чтоб затем провести проверку
        //на удовлетворение условий минимальности
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


    //Функция нормализации вектора весов
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
    public static void main(String[] args) {
        //Матрица оценок для альтернатив
        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };
        //Вектор весов
        int[] w = {6, 8, 4, 2};
        //Допустимые уровни для критериев, 1 потому что первый критерий главный
        double[] a = {1.0, 0.2, 0.5, 0.1};
        //Альтернативы санаториев
        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};
        String result = runChengeMethod(A, w, a, alternative);
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        System.out.println(GREEN + "Результат работы программы." + RESET);
        System.out.println("Лучший выбор: "+result);
    }
}
