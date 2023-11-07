package org.lb.lb3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ChangeMethodTest {

    @Test
    @DisplayName("Поиск максимума в столбце")
    public void testFoundMax() {

        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };
        double max = ChangeMethod.foundMax(A, 1);
        assertEquals(6.0, max, 0.001); // Ожидаемое максимальное значение во втором столбце
    }

    @Test
    @DisplayName("Поиск минимума в столбце")
    public void testFoundMin() {
        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };

        double min = ChangeMethod.foundMin(A, 2);
        assertEquals(1.0, min, 0.001); // Ожидаемое минимальное значение в третьем столбце
    }

    @Test
    @DisplayName("Нормализация вектора")
    public void testNormalizeWeight() {
        int[] weight = {6, 8, 4, 2};
        double[] normalizedWeights = ChangeMethod.normalizeWeigth(weight);

        assertEquals(0.3, normalizedWeights[0], 0.001); // Ожидаемое нормализованное значение для первого веса
        assertEquals(0.4, normalizedWeights[1], 0.001); // Ожидаемое нормализованное значение для второго веса
    }

    @Test
    @DisplayName("Алгоритм оптимизации")
    public void testRunChangeMethod() {
        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };
        int[] w = {6, 8, 4, 2};
        double[] a = {1.0, 0.2, 0.5, 0.1};
        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};
        String relust = ChangeMethod.runChengeMethod(A, w, a, alternative);
        assertEquals("Липецк", relust); // Ожидаемый индекс максимального значения
    }
}
