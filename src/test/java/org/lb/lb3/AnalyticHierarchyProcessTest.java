package org.lb.lb3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AnalyticHierarchyProcessTest {

    @Test
    @DisplayName("Работа алгоритма")
    void runtest() {

        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};
        double[][] A = {
                {1, 3, 5, 7},
                {0.33333, 1, 0.33333, 0.2},
                {0.2, 3, 1, 0.33333},
                {0.14268, 5, 3, 1}
        };

        double[][] B = {
                {1, 7, 1, 0.5},
                {0.14268, 1, 0.33333, 0.14268},
                {1, 3, 1, 0.2},
                {5, 7, 5, 1}
        };

        double[][] C = {
                {1, 1, 0.2, 0.14268},
                {1, 1, 0.2, 0.2},
                {5, 5, 1, 0.33333},
                {7, 7, 3, 1}
        };

        double[][] D = {
                {1, 3, 3, 5},
                {0.33333, 1, 1, 3},
                {0.33333, 1, 1, 3},
                {0.2, 0.33333, 0.33333, 1}
        };

        double[][] critery = {
                {1, 3, 5, 7},
                {0.33333, 1, 3, 5},
                {0.2, 0.33333, 1, 3},
                {0.14286, 0.2, 0.33333, 1}
        };
        String b = AnalyticHierarchyProcess.runAnalyticHierarchyProcess(alternative, A, B, C, D, critery);
        assertEquals(b, "Сосны");
    }
}

