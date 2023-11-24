package org.lb.lb2;

import java.math.BigDecimal;

public class DualSimplexMethod {

    public enum Constraint {
        LEQ, // <=
        GEQ  // >=
    }


    public static BigDecimal[] solveLinearProgram(double[][] A, double[] b, double[] c, Constraint[] constraints) {
        int m = A.length;
        int n = A[0].length;
        BigDecimal[][] tableau = new BigDecimal[m + 1][m + n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= m + n; j++) {
                tableau[i][j] = BigDecimal.ZERO;
            }
        }

        // Заполняем начальную симплекс-таблицу
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tableau[i][j] = BigDecimal.valueOf(A[i][j]);
            }
            tableau[i][n + i] = BigDecimal.ONE;
            tableau[i][m + n] = BigDecimal.valueOf(b[i]);
        }
        for (int j = 0; j < n; j++) {
            tableau[m][j] = BigDecimal.valueOf(-c[j]);
        }

        int[] basis = new int[m];
        for (int i = 0; i < m; i++) {
            basis[i] = n + i;
        }

        int iteration = 0;
        while (true) {
            System.out.println("Iteration " + iteration + ":");
            printTableau(tableau, basis);

            int col = 0;
            for (int j = 1; j < m + n; j++) {
                if (tableau[m][j].compareTo(tableau[m][col]) > 0) {
                    col = j;
                }
            }
            if (tableau[m][col].compareTo(BigDecimal.ZERO) <= 0) {
                BigDecimal optimalSolution = tableau[m][m + n].negate();
                BigDecimal[] basicSolution = new BigDecimal[n];
                for (int i = 0; i < m; i++) {
                    if (basis[i] < n) {
                        basicSolution[basis[i]] = tableau[i][m + n];
                    }
                }
                BigDecimal[] result = new BigDecimal[n + 1];
                result[0] = optimalSolution;
                System.arraycopy(basicSolution, 0, result, 1, n);
                return result;
            }

            Constraint[] rowConstraints = new Constraint[m];
            for (int i = 0; i < m; i++) {
                rowConstraints[i] = constraints[i];
            }

            BigDecimal minRatio = BigDecimal.valueOf(Double.MAX_VALUE);
            int row = -1;
            for (int i = 0; i < m; i++) {
                if ((tableau[i][col].compareTo(BigDecimal.ZERO) > 0 && rowConstraints[i] == Constraint.LEQ) ||
                        (tableau[i][col].compareTo(BigDecimal.ZERO) < 0 && rowConstraints[i] == Constraint.GEQ)) {
                    BigDecimal ratio = tableau[i][m + n].divide(tableau[i][col], 10, BigDecimal.ROUND_HALF_EVEN);
                    if (ratio.compareTo(minRatio) < 0) {
                        minRatio = ratio;
                        row = i;
                    }
                }
            }

            if (row == -1) {
                // No eligible row found, the linear program is unbounded
                return null;
            }

            basis[row] = col;

            BigDecimal pivot = tableau[row][col];
            for (int j = 0; j <= m + n; j++) {
                tableau[row][j] = tableau[row][j].divide(pivot, 10, BigDecimal.ROUND_HALF_EVEN);
            }
            for (int i = 0; i <= m; i++) {
                if (i != row) {
                    BigDecimal factor = tableau[i][col];
                    for (int j = 0; j <= m + n; j++) {
                        tableau[i][j] = tableau[i][j].subtract(factor.multiply(tableau[row][j]));
                    }
                }
            }

            iteration++;
        }
    }

    private static void printTableau(BigDecimal[][] tableau, int[] basis) {
        System.out.printf("%-10s", "Basis");
        for (int j = 0; j < tableau[0].length - 1; j++) {
            System.out.printf("%-10s", "x_" + (j + 1));
        }
        System.out.println();

        for (int i = 0; i < tableau.length; i++) {
            if (i < basis.length) {
                System.out.printf("%-10s", "x_" + (basis[i] + 1));
            } else {
                System.out.printf("%-10s", "c_" + (i - basis.length + 1));
            }
            for (int j = 0; j < tableau[0].length; j++) {
                System.out.printf("%-10s", tableau[i][j].toPlainString());
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double[][] A = {
                {2, 1},
                {-4, -5}
        };

        double[] b = {-20, 10};
        double[] c = {-3, -4};
        Constraint[] constraints = {Constraint.LEQ, Constraint.GEQ};

        BigDecimal[] result = solveLinearProgram(A, b, c, constraints);

        if (result == null) {
            System.out.println("Линейная программа неограниченна");
        } else {
            System.out.println("Оптимальное значение: " + result[0].toPlainString());
            System.out.println("Оптимальные значения переменных:");
            for (int i = 1; i < result.length; i++) {
                System.out.println("x_" + i + ": " + result[i].toPlainString());
            }
        }
    }

}
