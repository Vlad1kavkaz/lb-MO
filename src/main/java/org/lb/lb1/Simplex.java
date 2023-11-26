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
}
