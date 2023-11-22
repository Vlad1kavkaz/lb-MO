package org.lb.lb5;

import java.util.ArrayList;
import java.util.List;

public class HurwiczCriterion {
    private HurwiczCriterion() {

    }

    public static int checkHurwiczCriterion (int[][] strategies, double alpha) {
        //Пустой список для поиска минимума в строке
        List<Integer> maximumOfLine = new ArrayList<>();

        for (int[] strategy : strategies) {
            int maximalValue = Integer.MIN_VALUE;
            for (int elem : strategy) {
                if (elem > maximalValue) {
                    maximalValue = elem;
                }
            }
            maximumOfLine.add(maximalValue);
        }

        //Пустой список для поиска минимума в строке
        List<Integer> minimalOfLine = new ArrayList<>();

        for (int[] strategy : strategies) {
            int minimalValue = Integer.MAX_VALUE;
            for (int elem : strategy) {
                if (elem < minimalValue) {
                    minimalValue = elem;
                }
            }
            minimalOfLine.add(minimalValue);
        }

        List<Double> hurwiczOLine = new ArrayList<>();
        for (int i = 0; i < minimalOfLine.size(); i++) {
            double element = ((double) maximumOfLine.get(i) * alpha) + ((double) minimalOfLine.get(i) * (1 - alpha));
            hurwiczOLine.add(element);
        }

        System.out.println("Критерий Гурвица для строк");
        for (int i = 0; i < hurwiczOLine.size(); i++) {
            i++;
            System.out.print("Стратегия а" + i + ": ");
            i--;
            System.out.println(hurwiczOLine.get(i));
        }

        //Создаем переменные для индекса максимального значения и максимума
        double maxValue = Double.MIN_VALUE;
        int indexOfMax = -1;

        for (int i = 0; i < hurwiczOLine.size(); i++) {
            if (hurwiczOLine.get(i) > maxValue) {
                maxValue = hurwiczOLine.get(i);
                indexOfMax = i;
            }
        }

        return indexOfMax;
    }
}
