package org.lb.lb6;

import org.lb.lb1.Simplex;

import java.text.DecimalFormat;
import java.util.List;

public class MixedStrategies {
    private MixedStrategies() {

    }

    private final static String GREEN = "\u001B[32m";
    private final static String ORANGE = "\u001B[33m";
    private final static String RESET = "\u001B[0m";
    private final static String RED = "\u001B[31m";

    public static void main(String[] args) {
        double[][] matrix =  {
                {12, 5, 4, 9},
                {16, 0, 12, 0},
                {5, 13, 6, 7},
                {10, 10, 3, 16},
                {1, 11, 19, 1}
        };
        runAntogonistPlay(matrix);
    }

    private static void runAntogonistPlay(double[][] matrix) {
        // Вычисление смешанной стратегии игрока А
        double[][] A = new double[matrix[0].length][matrix.length];
        double[] b = {1, 1, 1, 1};
        double[] c = {1, 1, 1, 1, 1};

        // Переменные для размеров матриц
        int rowsMatrix = matrix.length;
        int colsMatrix = matrix[0].length;

        for (int j = 0; j < colsMatrix; j++) {
            for (int i = 0; i < rowsMatrix; i++) {
                A[j][i] = matrix[i][j];
            }
        }

        System.out.println(ORANGE + "Вычисление симплекс-методом смешанной стратегии для игрока А" + RESET);
        // Вычисление стратегии игрока A
        List<Double> resultSimplexForA = Simplex.solveLinearProgramming(A, b, c);
        double[] resultStrategyA = new double[resultSimplexForA.size() - 1];
        double sumA = 0.0;
        for (int i = 1; i < resultSimplexForA.size(); i++) {
            resultStrategyA[i - 1] = resultSimplexForA.get(i);
            sumA += resultStrategyA[i - 1];
        }
        // Нормализация стратегии игрока A
        for (int i = 0; i < resultStrategyA.length; i++) {
            resultStrategyA[i] /= sumA;
        }

        System.out.println(ORANGE + "Вычисление симплекс-методом смешанной стратегии для игрока B" + RESET);
        // Вычисление стратегии игрока B
        List<Double> resultSimplexForB = Simplex.solveDualLinearProgramming(matrix, c, b);
        double[] resultStrategyB = new double[resultSimplexForB.size() - 1];
        double sumB = 0.0;
        for (int i = 1; i < resultSimplexForB.size(); i++) {
            resultStrategyB[i - 1] = resultSimplexForB.get(i);
            sumB += resultStrategyB[i - 1];
        }
        // Нормализация стратегии игрока B
        for (int i = 0; i < resultStrategyB.length; i++) {
            resultStrategyB[i] /= sumB;
        }

        DecimalFormat df = new DecimalFormat("0.000");

        // Вывод стратегии игрока A
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

        // Вывод стратегии игрока B
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
        checkStrategy(resultStrategyA, resultStrategyB);

    }

    private static void checkStrategy(double[] strategyA, double[] strategyB) {
        double sumA = 0;
        for (double element : strategyA) {
            sumA += element;
        }
        double sumB = 0;
        for (double element : strategyB) {
            sumB += element;
        }
        if (!(sumA < 0.9 || sumA > 1.1) && !(sumB < 0.9 || sumB > 1.1)) {
            System.out.println(GREEN + "Cумма вероятностей для игроков равна 1" + RESET);
        }
        else if ((sumA < 0.9 || sumA > 1.1)) {
            System.out.println(RED + "Сумма вероятностей для игрока А не равна 1" + RESET + ": " + sumA);
        }
        else {
            System.out.println(RED + "Сумма вероятностей для игрока B не равна 1" + RESET + ": " + sumB);
        }
    }
}
