package org.lb.lb2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lb.lb2.BranchAndBound.branchAndBound;

import static org.lb.lb2.DualSimplexMethod.solveDualLinearProgram;

public class LB2Test {

    @Test
    @DisplayName("Двойственная задача")
    public void testDualLinearProgram() {
        double[][] A = {
                {2, 1},
                {1, 3}
        };
        double[] b = {4, 3};
        double[] c = {-6, -12};

        double[] result = solveDualLinearProgram(A, b, c);

        double optimalSolution = result[0];
        double[] basicSolution = new double[result.length - 1];
        System.arraycopy(result, 1, basicSolution, 0, result.length - 1);

        assertEquals(15.6, optimalSolution, 1e-5);
        assertArrayEquals(new double[]{1.8, 0.4}, basicSolution, 1e-5);
    }

    @Test
    @DisplayName("Целочисленное решение")
    public void testIntegerSolution() {
        double[][] A = {
                {2, 1},
                {1, 3}
        };
        double[] b = {4, 3};
        double[] c = {-6, -12};

        double[] solution = branchAndBound(A, b, c, solveDualLinearProgram(A, b, c));

        double optimalSolution = solution[0];
        double[] basicSolution = new double[solution.length - 1];
        System.arraycopy(solution, 1, basicSolution, 0, solution.length - 1);

        assertEquals(12.0, optimalSolution, 1e-5);
        assertArrayEquals(new double[]{2.0, 0.0}, basicSolution, 1e-5);
    }
}
