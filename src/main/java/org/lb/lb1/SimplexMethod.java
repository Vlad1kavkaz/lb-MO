package org.lb.lb1;

public class SimplexMethod {

    private SimplexMethod() {

    }

    public static double[] solveLinearProgram(double[][] A, double[] b, double[] c) {
        int m = A.length;
        int n = A[0].length;
        double[][] tableau = new double[m + 1][m + n + 1];

        // Заполняем начальную симплекс-таблицу
        for (int i = 0; i < m; i++) {
            System.arraycopy(A[i], 0, tableau[i], 0, n);
            tableau[i][n + i] = 1.0;
            tableau[i][m + n] = b[i];
        }
        for (int j = 0; j < n; j++) {
            tableau[m][j] = -c[j];
        }

        int[] basis = new int[m];
        for (int i = 0; i < m; i++) {
            basis[i] = n + i;
        }

        while (true) {
            // Находим входящий разрешенный столбец
            int col = 0;
            for (int j = 1; j < m + n; j++) {
                if (tableau[m][j] > tableau[m][col]) {
                    col = j;
                }
            }
            if (tableau[m][col] <= 0) {
                // Если все элементы в последней строке не положительны, то оптимум достигнут
                double optimalSolution = -tableau[m][m + n];
                double[] basicSolution = new double[n];
                for (int i = 0; i < m; i++) {
                    if (basis[i] < n) {
                        basicSolution[basis[i]] = tableau[i][m + n];
                    }
                }
                double[] result = new double[n + 1];
                result[0] = optimalSolution;
                System.arraycopy(basicSolution, 0, result, 1, n);
                return result;
            }

            // Находим исходящую разрешенную строку
            double minRatio = Double.MAX_VALUE;
            int row = -1;
            for (int i = 0; i < m; i++) {
                if (tableau[i][col] > 0) {
                    double ratio = tableau[i][m + n] / tableau[i][col];
                    if (ratio < minRatio) {
                        minRatio = ratio;
                        row = i;
                    }
                }
            }

            // Обновляем базис
            basis[row] = col;

            // Пересчитываем симплекс-таблицу
            double pivot = tableau[row][col];
            for (int j = 0; j <= m + n; j++) {
                tableau[row][j] /= pivot;
            }
            for (int i = 0; i <= m; i++) {
                if (i != row) {
                    double factor = tableau[i][col];
                    for (int j = 0; j <= m + n; j++) {
                        tableau[i][j] -= factor * tableau[row][j];
                    }
                }
            }
        }
    }
}
