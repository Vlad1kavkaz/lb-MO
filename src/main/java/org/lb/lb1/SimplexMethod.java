package org.lb.lb1;

public class SimplexMethod {

    public static void main(String[] args) {
        double[][] coefficients = {
                {12, 16, 5, 10, 1},
                {5, 0, 13, 10, 11},
                {4, 12, 6, 3, 19},
                {0, 0, 7, 16, 1}
        };

        double[] objectiveFunctionCoefficients = {1, 1, 1, 1, 1};

        double[] result = solveLinearProgramming(coefficients, objectiveFunctionCoefficients);

        System.out.println("Optimal solution:");
        for (int i = 0; i < result.length; i++) {
            System.out.println("x" + (i + 1) + ": " + result[i]);
        }
    }

    public static double[] solveLinearProgramming(double[][] coefficients, double[] objectiveFunctionCoefficients) {
        int m = coefficients.length; // количество ограничений
        int n = coefficients[0].length; // количество переменных

        // Добавляем искусственные переменные
        double[][] tableau = new double[m + 1][n + m + 1];
        for (int i = 0; i < m; i++) {
            System.arraycopy(coefficients[i], 0, tableau[i], 0, n);
            tableau[i][n + i] = 1;
            tableau[i][n + m] = 1; // искусственная переменная
        }

        System.arraycopy(objectiveFunctionCoefficients, 0, tableau[m], 0, n);
        tableau[m][n + m] = 0; // целевая функция

        while (hasNegative(tableau[m])) {
            int pivotColumn = findPivotColumn(tableau[m]);
            int pivotRow = findPivotRow(tableau, pivotColumn);

            // Пересчет таблицы
            pivot(tableau, pivotRow, pivotColumn);
        }

        double[] result = new double[n];
        for (int i = 0; i < m; i++) {
            int basicVariable = findBasicVariable(tableau, i);
            if (basicVariable != -1) {
                result[basicVariable] = tableau[i][n + m] / tableau[i][basicVariable];
            }
        }

        return result;
    }

    private static boolean hasNegative(double[] array) {
        for (double value : array) {
            if (value < 0) {
                return true;
            }
        }
        return false;
    }

    private static int findPivotColumn(double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < 0) {
                return i;
            }
        }
        return -1;
    }

    private static int findPivotRow(double[][] tableau, int pivotColumn) {
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 0; i < tableau.length - 1; i++) {
            if (tableau[i][pivotColumn] > 0) {
                double ratio = tableau[i][tableau[i].length - 1] / tableau[i][pivotColumn];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }

        return pivotRow;
    }

    private static void pivot(double[][] tableau, int pivotRow, int pivotColumn) {
        int m = tableau.length;
        int n = tableau[0].length;

        // Делим строку на значение в разрезе
        double pivotValue = tableau[pivotRow][pivotColumn];
        for (int i = 0; i < n; i++) {
            tableau[pivotRow][i] /= pivotValue;
        }

        // Обновляем таблицу
        for (int i = 0; i < m; i++) {
            if (i != pivotRow) {
                double ratio = tableau[i][pivotColumn];
                for (int j = 0; j < n; j++) {
                    tableau[i][j] -= ratio * tableau[pivotRow][j];
                }
            }
        }
    }

    private static int findBasicVariable(double[][] tableau, int row) {
        int m = tableau.length;
        int n = tableau[0].length;

        for (int i = n - m; i < n - 1; i++) {
            int count = 0;
            int basicVariable = -1;
            for (int j = 0; j < m; j++) {
                if (tableau[j][i] == 1) {
                    count++;
                    basicVariable = i;
                }
            }
            if (count == 1 && basicVariable != -1) {
                return basicVariable;
            }
        }

        return -1;
    }

}
