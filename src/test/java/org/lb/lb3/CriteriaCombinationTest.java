package org.lb.lb3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CriteriaCombinationTest {

    @Test
    @DisplayName("Поиск максимального индекса")
    void findMaxInd() {
        double[] array = {1.0, 3.0, -1.0};

        double res = CriteriaCombination.findMaxIndex(array);
        assertEquals(res, 1);
    }


    @Test
    @DisplayName("Перемножение матриц")
    void testMultiplyMatrix() {
        double[][] matrixB = {
                {2.0, 0.0, 1.0},
                {3.0, 0.0, 2.0},
                {1.0, 1.0, 0.0}
        };

        ArrayList<Double> weight = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0));

        double [] result = {5.0, 9.0, 3.0};

        double[] resultTest = CriteriaCombination.multiplyMatrixAndWeight(matrixB, weight);
        assertArrayEquals(resultTest, result, 0.001);
    }

    @Test
    @DisplayName("Тест нормализации веса")
    void testNormalizedWegth() {
        ArrayList<Double> w = new ArrayList<>();
        w.add(1.0);
        w.add(2.0);
        w.add(2.0);
        ArrayList<Double> res = new ArrayList<>();
        res.add(0.2);
        res.add(0.4);
        res.add(0.4);

        ArrayList<Double> result = CriteriaCombination.normalizeWeight(w);

        for (int i = 0; i < res.size(); i++) {
            assertEquals(res.get(i), result.get(i), 0.001); // Указываем допустимую погрешность
        }
    }

    @Test
    @DisplayName("Составление веса")
    void makeWeigthTest() {
        Map<String, Double> markCritery = new HashMap<>();
        markCritery.put("12", 0.5);
        markCritery.put("13", 1.0);
        markCritery.put("14", 1.0);
        markCritery.put("21", 0.5);
        markCritery.put("23", 1.0);
        markCritery.put("24", 1.0);
        markCritery.put("31", 0.0);
        markCritery.put("32", 0.0);
        markCritery.put("34", 1.0);
        markCritery.put("41", 0.0);
        markCritery.put("42", 0.0);
        markCritery.put("43", 0.0);

        int column = 4;

        ArrayList<Double> resultTest = CriteriaCombination.getWeight(column, markCritery);
        ArrayList<Double> equals = new ArrayList<>(List.of(2.5, 2.5, 1.0, 0.0));
        for (int i = 0; i < resultTest.size(); i++) {
            assertEquals(resultTest.get(i), equals.get(i), 0.001); // Указываем допустимую погрешность
        }

    }

    @Test
    @DisplayName("Тест алгоритма")
    void runAlgorithm() {
        Map<String, Double> markCritery = new HashMap<>();
        markCritery.put("12", 0.5);
        markCritery.put("13", 1.0);
        markCritery.put("14", 1.0);
        markCritery.put("21", 0.5);
        markCritery.put("23", 1.0);
        markCritery.put("24", 1.0);
        markCritery.put("31", 0.0);
        markCritery.put("32", 0.0);
        markCritery.put("34", 1.0);
        markCritery.put("41", 0.0);
        markCritery.put("42", 0.0);
        markCritery.put("43", 0.0);

        double[][] A = {
                {7, 2, 2, 5},
                {1, 6, 1, 3},
                {3, 1, 5, 3},
                {5, 5, 6, 1},
        };

        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};


        String res = CriteriaCombination.runCriteriaCombination(A, markCritery, alternative);
        assertEquals(res, "Сосны");
    }


}
