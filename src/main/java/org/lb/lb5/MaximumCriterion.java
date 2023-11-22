package org.lb.lb5;

import java.util.ArrayList;
import java.util.List;

public class MaximumCriterion {

    private MaximumCriterion() {

    }

    public static int checkMaximumCriterion(int[][] strategies) {
        //Пустой список для поиска максимума в строке
        List<Integer> maximumOfLine = new ArrayList<>();

        for (int[] strategy : strategies) {
            int maximalValue = Integer.MIN_VALUE;
            for (int element : strategy) {
                if (element > maximalValue) {
                    maximalValue = element;
                }
            }
            maximumOfLine.add(maximalValue);
        }

        //Вывод результатов поиска
        System.out.println("Максимум строки");
        for (int i = 0; i < maximumOfLine.size(); i++) {
            i++;
            System.out.print("Стратегия а" + i + ": ");
            i--;
            System.out.println(maximumOfLine.get(i));
        }

        //Создаем переменные для индекса максимального значения и максимума
        double maxValue = Double.MIN_VALUE;
        int indexOfMax = -1;

        for (int i = 0; i < maximumOfLine.size(); i++) {
            if (maximumOfLine.get(i) > maxValue) {
                maxValue = maximumOfLine.get(i);
                indexOfMax = i;
            }
        }

        return indexOfMax;
    }
}
