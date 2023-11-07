package org.lb.lb1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lb.lb1.SimplexMethod.solveLinearProgram;

public class LB1Test {

    @Test
    @DisplayName("Тест первой лабы")
    public void testLinearProgram() {
        double[][] A = {
                {2, 1},
                {1, 3}
        };
        double[] b = {6, 12};
        double[] c = {-4, -3};

        double[] result = solveLinearProgram(A, b, c);

        double optimalSolution = result[0];
        double[] basicSolution = new double[result.length - 1];
        System.arraycopy(result, 1, basicSolution, 0, result.length - 1);

        assertEquals(15.6, optimalSolution, 1e-5);
        assertArrayEquals(new double[]{1.2, 3.6}, basicSolution, 1e-5);
    }
}
