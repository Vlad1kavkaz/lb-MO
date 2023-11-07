package org.lb.lb2;

public class DualSimplexMethod {

    private DualSimplexMethod() {

    }

    public static double[] solveDualLinearProgram(double[][] A, double[] b, double[] c) {
        int m = A.length;
        int n = A[0].length;
        double[][] tableau = new double[m + 1][n + m + 1];

        // Инвертируем знаки коэффициентов целевой функции для минимизации
        double[] invertedC = new double[c.length];
        for (int i = 0; i < c.length; i++) {
            invertedC[i] = -c[i];
        }

        // Заполняем начальную двойственную симплекс-таблицу
        for (int i = 0; i < m; i++) {
            System.arraycopy(A[i], 0, tableau[i], 0, n);
            tableau[i][n + i] = 1.0;
            tableau[i][n + m] = b[i];
        }
        System.arraycopy(invertedC, 0, tableau[m], 0, n); // Используем инвертированные коэффициенты

        int[] basis = new int[m];
        for (int i = 0; i < m; i++) {
            basis[i] = n + i;
        }

        while (true) {
            // Находим входящий разрешенный столбец
            int col = -1;
            for (int j = 0; j < n + m; j++) {
                if (tableau[m][j] > 0) {
                    col = j;
                    break;
                }
            }
            if (col == -1) {
                // Если все элементы в последней строке не положительны, то оптимум достигнут
                double optimalSolution = -tableau[m][n + m]; // Инвертируем для минимизации
                double[] basicSolution = new double[n];
                for (int i = 0; i < m; i++) {
                    if (basis[i] < n) {
                        basicSolution[basis[i]] = tableau[i][n + m];
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
                    double ratio = tableau[i][n + m] / tableau[i][col];
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
            for (int j = 0; j <= n + m; j++) {
                tableau[row][j] /= pivot;
            }
            for (int i = 0; i <= m; i++) {
                if (i != row) {
                    double factor = tableau[i][col];
                    for (int j = 0; j <= n + m; j++) {
                        tableau[i][j] -= factor * tableau[row][j];
                    }
                }
            }
        }
    }
}
