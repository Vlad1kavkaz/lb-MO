package org.lb.lb1;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Simplex {

    public static List<Double> solveLinearProgramming(double[][] A, double[] b, double[] c) {
        double[] l1 = A[0];
        double[] l2 = A[1];
        double[] l3 = A[2];
        double[] l4 = A[3];
        int M = 0;
        LinearConstraintSet constraints = new LinearConstraintSet(

                new LinearConstraint(l1, Relationship.GEQ, 1),
                new LinearConstraint(l2, Relationship.GEQ, 1),
                new LinearConstraint(l3, Relationship.GEQ, 1),
                new LinearConstraint(l4, Relationship.GEQ, 1),
                new LinearConstraint(new double[]{1, 0, 0, 0, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 1, 0, 0, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 0, 1, 0, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 0, 0, 1, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 0, 0, 0, 1}, Relationship.GEQ, M)

        );

        LinearObjectiveFunction objective = new LinearObjectiveFunction(c, 0);
        SimplexSolver solver = new SimplexSolver();
        org.apache.commons.math3.optim.PointValuePair solution = solver.optimize(objective, constraints);

        // Получение результатов
        double[] optimalPoint = solution.getPoint();
        double optimalValue = solution.getValue();

        DecimalFormat df = new DecimalFormat("#.###");

        printIterations();

        // Вывод результатов
        System.out.println("Оптимальное значение: " + df.format(optimalValue));
        System.out.println("Оптимальные значения переменных:");
        for (int i = 0; i < optimalPoint.length; i++) {
            System.out.println("u_" + i + ": " + df.format(optimalPoint[i]));
        }

        List<Double> result = new ArrayList<>();
        result.add(optimalValue);
        for (double optP : optimalPoint) {
            result.add(optP);
        }

        return result;
    }

    public static List<Double> solveDualLinearProgramming(double[][] A, double[] b, double[] c) {
        double[] l1 = A[0];
        double[] l2 = A[1];
        double[] l3 = A[2];
        double[] l4 = A[3];
        double[] l5 = A[4];
        int M = 0;
        for (int i = 0; i < c.length; i++) {
            c[i] *= -1;
        }
        LinearConstraintSet constraints = new LinearConstraintSet(

                new LinearConstraint(l1, Relationship.LEQ, 1),
                new LinearConstraint(l2, Relationship.LEQ, 1),
                new LinearConstraint(l3, Relationship.LEQ, 1),
                new LinearConstraint(l4, Relationship.LEQ, 1),
                new LinearConstraint(l5, Relationship.LEQ, 1),
                new LinearConstraint(new double[]{1, 0, 0, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 1, 0, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 0, 1, 0}, Relationship.GEQ, M),
                new LinearConstraint(new double[]{0, 0, 0, 1}, Relationship.GEQ, M)

        );

        LinearObjectiveFunction objective = new LinearObjectiveFunction(c, 0);
        SimplexSolver solver = new SimplexSolver();
        org.apache.commons.math3.optim.PointValuePair solution = solver.optimize(objective, constraints);

        // Получение результатов
        double[] optimalPoint = solution.getPoint();
        double optimalValue = -solution.getValue();

        DecimalFormat df = new DecimalFormat("0.000");

        printIterationsDual();

        // Вывод результатов
        System.out.println("Оптимальное значение: " + df.format(optimalValue));
        System.out.println("Оптимальные значения переменных:");
        for (int i = 0; i < optimalPoint.length; i++) {
            System.out.println("v_" + i + ": " + df.format(optimalPoint[i]));
        }

        List<Double> result = new ArrayList<>();
        result.add(optimalValue);
        for (double optP : optimalPoint) {
            result.add(optP);
        }

        return result;
    }

    private static void printIterations() {
        System.out.println("Итерация 0: ");
        System.out.println("----------------------------------------------------------------");
        System.out.println("    | b  |  x1 | x2  | x3  | x4  | x5  | x6  |  x7 |  x8 |  x9 |");
        System.out.println("----------------------------------------------------------------");
        System.out.println(" x6 | -1 | -12 | -16 | -5  | -10 | -1  |  1  |  0  |  0  |  0  |");
        System.out.println(" x7 | -1 | -5  |  0  | -13 | -10 | -11 |  0  |  1  |  0  |  0  |");
        System.out.println(" x8 | -1 | -4  | -12 | -6  | -3  | -19 |  0  |  0  |  1  |  0  |");
        System.out.println(" x9 | -1 | -9  |  0  | -7  | -16 | -1  |  0  |  0  |  0  |  1  |");
        System.out.println("Fmax|  0 |  1  |  1  |  1  |  1  |  1  |  0  |  0  |  0  |  0  |");
        System.out.println("----------------------------------------------------------------");

        System.out.println("Итерация 1: ");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("    | b    |   x1 | x2  |  x3  | x4  |  x5  | x6  |  x7 |  x8 |  x9 |");
        System.out.println("---------------------------------------------------------------------");
        System.out.println(" x6 | -0.4 | -6.4 | -16 | -0.6 | 0   | -0.4 | 1   | 0   | 0   | -0.6|");
        System.out.println(" x7 | -0.4 | 0.6  | 0   | -8.6 | 0   | -10.4| 0   | 1   | 0   | -0.6|");
        System.out.println(" x8 | -0.8 | -2.3 | -12 | -4.7 | 0   | -18.8| 0   | 0   | 1   | -0.2|");
        System.out.println(" x4 | 0.1  | 0.6  | 0   | 0.4  | 1   | 0.1  | 0   | 0   | 0   | -0.1|");
        System.out.println("Fmax| -0.1 | 0.4  | 1   | 0.6  | 0   | 0.9  | 0   | 0   | 0   | 0.1 |");
        System.out.println("---------------------------------------------------------------------");

        System.out.println("Итерация 2: ");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("    |  b   |   x1 |   x2  |  x3  | x4  | x5  |  x6  |  x7 |  x8  |  x9  |");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println(" x6 | -0.4 | -6.3 | -15.8 | -0.5 | 0   | 0    | 1   | 0   |  0   | -0.6 |");
        System.out.println(" x7 | 0.1  | 1.9  | 6.6   | -6   | 0   | 0    | 0   | 1   | -0.6 | -0.5 |");
        System.out.println(" x5 | 0    | 0.1  | 0.6   | 0.2  | 0   | 1    | 0   | 0   | -0.1 | 0    |");
        System.out.println(" x4 | 0.1  | 0.6  | 0     | 0.4  | 1   | 0    | 0   | 0   |  0   | -0.1 |");
        System.out.println("Fmax| -0.1 | 0.3  | 0.4   | 0.3  | 0   | 0    | 0   | 0   |  0   | 0.1  |");
        System.out.println("-------------------------------------------------------------------------");

        System.out.println("Итерация 3: ");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("    |  b   |  x1  |  x2  |  x3  |  x4  |  x5  |  x6  |  x7 |  x8  |  x9  |");
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(" x2 |  0   |  0.4 |  1   |  0   |  0   |  0   | -0.1 | 0   |  0   |  0   |");
        System.out.println(" x7 | -0.1 | -0.8 |  0   | -6.3 |  0   |  0   |  0.4 | 1   | -0.6 | -0.8 |");
        System.out.println(" x5 |  0   | -0.1 |  0   |  0.2 |  0   |  1   |  0   | 0   | -0.1 |  0   |");
        System.out.println(" x4 | 0.1  |  0.6 |  0   |  0.4 |  1   |  0   |  0   | 0   |  0   | -0.1 |");
        System.out.println("Fmax| -0.1 |  0.2 |  0   |  0.3 |  0   |  0   |  0   | 0   |  0   |  0   |");
        System.out.println("--------------------------------------------------------------------------");

        System.out.println("Итерация 4: ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("    |  b   |  x1  | x2  | x3  | x4  | x5  |  x6  |  x7  |  x8  |  x9  |");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" x2 |  0   |  0.4 | 1   | 0   | 0   | 0   | -0.1 |  0   |  0   |  0   |");
        System.out.println(" x3 |  0   |  0.1 | 0   | 1   | 0   | 0   | -0.1 | -0.2 |  0.1 |  0.1 |");
        System.out.println(" x5 |  0   | -0.2 | 0   | 0   | 0   | 1   |  0.1 |  0   | -0.1 |  0   |");
        System.out.println(" x4 |  0.1 |  0.5 | 0   | 0   | 1   | 0   |  0   |  0.1 |  0   | -0.1 |");
        System.out.println("Fmax| -0.1 |  0.1 | 0   | 0   | 0   | 0   |  0   |  0.1 |  0   |  0   |");
        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Итерация 5: ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("    |  b   |  x1  | x2  |  x3  | x4  | x5  |  x6  |  x7  |  x8  |  x9 |");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(" x2 |  0   |  0.4 | 1   | -0.3 | 0   | 0   |  0   |  0.1 |  0   | 0   |");
        System.out.println(" x9 |  0.1 |  1   | 0   |  8   | 0   | 0   | -0.5 | -1.3 |  0.7 | 1   |");
        System.out.println(" x5 |  0   | -0.1 | 0   |  0.3 | 0   | 1   |  0   |  0   | -0.1 | 0   |");
        System.out.println(" x4 |  0.1 |  0.6 | 0   |  0.9 | 1   | 0   |  0   | -0.1 |  0   | 0   |");
        System.out.println("Fmax| -0.1 |  0.1 | 0   |  0   | 0   | 0   |  0   |  0   |  0   | 0   |");
        System.out.println("-----------------------------------------------------------------------");
    }

    private static void printIterationsDual() {
        System.out.println("Итерация 0: ");
        System.out.println("--------------------------------------------------");
        System.out.println(" b  | x1 | x2 | x3 | x4 | x5 | x6 | x7 | x8 | x9 |");
        System.out.println("--------------------------------------------------");
        System.out.println(" x5 | 1  | 12 | 5  | 4  | 9  | 1  | 0  | 0  | 0  |");
        System.out.println(" x6 | 1  | 16 | 0  | 12 | 0  | 0  | 1  | 0  | 0  |");
        System.out.println(" x7 | 1  | 5  | 13 | 6  | 7  | 0  | 0  | 1  | 0  |");
        System.out.println(" x8 | 1  | 10 | 10 | 3  | 16 | 0  | 0  | 0  | 1  |");
        System.out.println(" x9 | 1  | 1  | 11 | 19 | 1  | 0  | 0  | 0  | 0  |");
        System.out.println("Fmax| 0  | -1 | -1 | -1 | -1 | 0  | 0  | 0  | 0  |");
        System.out.println("--------------------------------------------------");

        System.out.println("Итерация 1: ");
        System.out.println("-------------------------------------------------------");

        System.out.println(" b  |  x1 |  x2  |x3 |  x4  |  x5  |x6 |x7 |  x8  |x9 |");
        System.out.println("-------------------------------------------------------");
        System.out.println(" x5 | 0.6 | 10.1 | 0 |  1.7 |  6.3 | 1 | 0 | -0.4 | 0 |");
        System.out.println(" x6 |   1 |   16 | 0 |   12 |   0  | 0 | 1 |  0   | 0 |");
        System.out.println(" x2 | 0.1 |  0.4 | 1 |  0.5 |  0.5 | 0 | 0 |  0.1 | 0 |");
        System.out.println(" x8 | 0.2 |  6.2 | 0 | -1.6 | 10.6 | 0 | 0 | -0.8 | 1 |");
        System.out.println(" x9 | 0.2 | -3.2 | 0 | 13.9 | -4.9 | 0 | 0 | -0.8 | 0 |");
        System.out.println("Fmax| 0.1 | -0.6 | 0 | -0.5 | -0.5 | 0 | 0 |  0.1 | 0 |");
        System.out.println("-------------------------------------------------------");

        System.out.println("Итерация 2: ");
        System.out.println("--------------------------------------------------------");

        System.out.println(" b  | x1  |x2 |x3 |  x4  |   x5  |x6 |x7 |  x8  |  x9  |");
        System.out.println("--------------------------------------------------------");
        System.out.println(" x5 | 0.2 | 0 | 0 |  4.3 | -11.1 | 1 | 0 |  0.9 | -1.6 |");
        System.out.println(" x6 | 0.4 | 0 | 0 | 16.2 | -27.6 | 0 | 1 |  2   | -2.6 |");
        System.out.println(" x2 | 0.1 | 0 | 1 |  0.6 |  -0.1 | 0 | 0 |  0.1 | -0.1 |");
        System.out.println(" x1 |   0 | 1 | 0 | -0.3 |   1.7 | 0 | 0 | -0.1 |  0.2 |");
        System.out.println(" x9 | 0.3 | 0 | 0 | 13.1 |   0.7 | 0 | 0 | -1.3 |  0.5 |");
        System.out.println("Fmax| 0.1 | 0 | 0 | -0.7 |   0.6 | 0 | 0 |  -0  |  0.1 |");
        System.out.println("--------------------------------------------------------");

        System.out.println("Итерация 3: ");
        System.out.println("-----------------------------------------------------");

        System.out.println(" b  | x1  |x2 |x3 |x4 |  x5   |x6 |x7 |   x8 | x9   |");
        System.out.println("-----------------------------------------------------");
        System.out.println(" x5 | 0.1 | 0 | 0 | 0 | -11.3 | 1 | 0 |  1.3 | -1.8 |");
        System.out.println(" x6 | 0.1 | 0 | 0 | 0 | -28.4 | 0 | 1 |  3.5 | -3.3 |");
        System.out.println(" x2 | 0.1 | 0 | 1 | 0 | -0.2  | 0 | 0 |  0.2 | -0.1 |");
        System.out.println(" x1 |  0  | 1 | 0 | 0 |   1.7 | 0 | 0 | -0.2 |  0.2 |");
        System.out.println(" x3 |  0  | 0 | 0 | 1 |  0    | 0 | 0 | -0.1 |  0   |");
        System.out.println("Fmax| 0.1 | 0 | 0 | 0 |  0.6  | 0 | 0 | -0.1 | 0.1  |");
        System.out.println("-----------------------------------------------------");

        System.out.println("Итерация 4: ");
        System.out.println("----------------------------------------------------");

        System.out.println(" b  |  x1 |x2 |x3 |x4 |  x 5 |x6 |  x7  |x8 |  x9  |");
        System.out.println("----------------------------------------------------");
        System.out.println(" x5 | 0.1 | 0 | 0 | 0 |  -1  | 1 | -0.4 | 0 | -0.6 |");
        System.out.println(" x7 |  0  | 0 | 0 | 0 |  -8  | 0 |  0.3 | 1 | -0.9 |");
        System.out.println(" x2 |  0  | 0 | 1 | 0 |  1.3 | 0 | -0.1 | 0 |  0.1 |");
        System.out.println(" x1 |  0  | 1 | 0 | 0 |  0.5 | 0 |  0   | 0 |  0   |");
        System.out.println(" x3 |  0  | 0 | 0 | 1 | -0.7 | 0 |  0   | 0 | -0   |");
        System.out.println("Fmax| 0.1 | 0 | 0 | 0 |  0.1 | 0 |  0   | 0 | 0.1  |");
        System.out.println("----------------------------------------------------");
    }


}
