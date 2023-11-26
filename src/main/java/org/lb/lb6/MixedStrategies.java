package org.lb.lb6;

import org.lb.lb1.Simplex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class MixedStrategies {
    // Цвета для вывода в консоль
    private final static String GREEN = "\u001B[32m";
    private final static String ORANGE = "\u001B[33m";
    private final static String RESET = "\u001B[0m";
    private final static String RED = "\u001B[31m";

    private MixedStrategies() {
        // Приватный конструктор, чтобы предотвратить создание экземпляра класса
    }

    public static void main(String[] args) {
        double[][] matrix = {
                {12, 5, 4, 9},
                {16, 0, 12, 0},
                {5, 13, 6, 7},
                {10, 10, 3, 16},
                {1, 11, 19, 1}
        };
        runAntagonistPlay(matrix);
    }

    private static void runAntagonistPlay(double[][] matrix) {
        double[][] A = new double[matrix[0].length][matrix.length];
        double[] b = {1, 1, 1, 1};
        double[] c = {1, 1, 1, 1, 1};

        int rowsMatrix = matrix.length;
        int colsMatrix = matrix[0].length;

        // Транспонирование матрицы
        for (int j = 0; j < colsMatrix; j++) {
            for (int i = 0; i < rowsMatrix; i++) {
                A[j][i] = matrix[i][j];
            }
        }

        // Найдем верхнюю и нижнюю цены игры
        double lowCost = findLowCostPlay(matrix);
        double upCost = findUpCostPlay(matrix);

        System.out.println("Нижняя цена игры: " + lowCost + "\nВерхняя цена игры: " + upCost);

        System.out.println(ORANGE + "Вычисление симплекс-методом смешанной стратегии для игрока А" + RESET);

        List<Double> resultSimplexForA = Simplex.solveLinearProgramming(A, b, c);
        double[] resultStrategyA = new double[resultSimplexForA.size() - 1];
        BigDecimal sumA = BigDecimal.ZERO;
        DecimalFormat df = new DecimalFormat("0.000");

        // Проверка удовлетвлетворению ценам игры
        if ((1 / resultSimplexForA.get(0)) >= lowCost && (1 / resultSimplexForA.get(0)) <= upCost) {
            System.out.println("Результат симплекса для игрока А удовлетвряет условию ценам игры");
            System.out.println(lowCost + " <= " + df.format(1 / resultSimplexForA.get(0)) + " <= " + upCost);
        }

        for (int i = 1; i < resultSimplexForA.size(); i++) {
            BigDecimal value = BigDecimal.valueOf(resultSimplexForA.get(i))
                    .divide(BigDecimal.valueOf(resultSimplexForA.get(0)), 10, RoundingMode.HALF_UP);
            resultStrategyA[i - 1] = value.doubleValue();
            sumA = sumA.add(value);
        }

        System.out.println(ORANGE + "Вычисление симплекс-методом смешанной стратегии для игрока B" + RESET);

        List<Double> resultSimplexForB = Simplex.solveDualLinearProgramming(matrix, c, b);
        double[] resultStrategyB = new double[resultSimplexForB.size() - 1];
        BigDecimal sumB = BigDecimal.ZERO;

        // Проверка удовлетвлетворению ценам игры
        if ((1 / resultSimplexForB.get(0)) >= lowCost && (1 / resultSimplexForB.get(0)) <= upCost) {
            System.out.println("Результат симплекса для игрока А удовлетвряет условию ценам игры");
            System.out.println(lowCost + " <= " + df.format(1 / resultSimplexForB.get(0)) + " <= " + upCost);
        }

        for (int i = 1; i < resultSimplexForB.size(); i++) {
            BigDecimal value = BigDecimal.valueOf(resultSimplexForB.get(i))
                    .divide(BigDecimal.valueOf(resultSimplexForB.get(0)), 10, RoundingMode.HALF_UP);
            resultStrategyB[i - 1] = value.doubleValue();
            sumB = sumB.add(value);
        }

        System.out.println(GREEN + "Смешанная стратегия для игрока А:" + RESET);
        System.out.print("(");
        for (double v : resultStrategyA) {
            System.out.print(df.format(v));
            if (v != resultStrategyA[resultStrategyA.length - 1]) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        System.out.println();

        System.out.println(GREEN + "Смешанная стратегия для игрока В:" + RESET);
        System.out.print("(");
        for (double v : resultStrategyB) {
            System.out.print(df.format(v));
            if (v != resultStrategyB[resultStrategyB.length - 1]) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        System.out.println();

        System.out.println("Проверка удовлетворению условию суммы вероятностей");
        checkStrategy(sumA.doubleValue(), sumB.doubleValue());
    }

    private static double findLowCostPlay(double[][] matrix) {
        int rows = matrix.length;
        double[] minValuesInRows = new double[rows];

        // Найти минимумы в каждой строке
        for (int i = 0; i < rows; i++) {
            double minInRow = Double.MAX_VALUE;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < minInRow) {
                    minInRow = matrix[i][j];
                }
            }
            minValuesInRows[i] = minInRow;
        }

        // Найти максимум среди минимумов в строках
        double maxMinValue = Double.MIN_VALUE;
        for (double minValue : minValuesInRows) {
            if (minValue > maxMinValue) {
                maxMinValue = minValue;
            }
        }

        return maxMinValue;
    }

    private static double findUpCostPlay(double[][] matrix) {
        int rows = matrix.length;
        double[] maxValuesInRows = new double[rows];

        // Найти максимумы в каждой строке
        for (int i = 0; i < rows; i++) {
            double maxInRow = Double.MIN_VALUE;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > maxInRow) {
                    maxInRow = matrix[i][j];
                }
            }
            maxValuesInRows[i] = maxInRow;
        }

        // Найти минимум среди максимумов в строках
        double minMaxValue = Double.MAX_VALUE;
        for (double maxValue : maxValuesInRows) {
            if (maxValue < minMaxValue) {
                minMaxValue = maxValue;
            }
        }

        return minMaxValue;
    }

    private static void checkStrategy(double sumA, double sumB) {
        if (sumA == 1.0 && sumB == 1.0) {
            System.out.println(GREEN + "Cумма вероятностей для игроков равна 1" + RESET);
        } else if (sumA != 1.0) {
            System.out.println(RED + "Сумма вероятностей для игрока А не равна 1" + RESET + ": " + sumA);
        } else {
            System.out.println(RED + "Сумма вероятностей для игрока B не равна 1" + RESET + ": " + sumB);
        }
    }
}
