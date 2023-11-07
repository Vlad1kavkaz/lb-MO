package org.lb.lb3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParetoMethodTest {

    @Test
    @DisplayName("Вычисление Манхэттенского расстояния")
    public void testManhattanLength() {
        ParetoMethod.Point p1 = new ParetoMethod.Point(3.0, 4.0);
        ParetoMethod.Point p2 = new ParetoMethod.Point(1.0, 2.0);
        double result = ParetoMethod.manhettenLength(p1, p2);
        assertEquals(4.0, result, 0.001); // Ожидаемое Манхэттенское расстояние
    }

    @Test
    @DisplayName("Поиск лучшей альтернативы из множества Парето")
    public void testRunPareto() {
        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};
        double[][] A = {
                {5, 2},
                {2, 5},
                {3, 1},
                {4, 4}
        };
        String result = ParetoMethod.runPareto(A, alternative, 0, 1);
        assertEquals("Сосны", result); // Ожидаемая лучшая альтернатива с учетом точки утопии
    }

}

