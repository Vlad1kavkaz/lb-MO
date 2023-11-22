package org.lb.lb5;

import java.util.ArrayList;
import java.util.List;

public class WaldCriterion {

    private WaldCriterion() {

    }

    public static int checkWaldCriterion(int[][] strategies) {
        //Пустой список для поиска минимума в строке
        List<Integer> minimalOfLine = new ArrayList<>();

        for (int[] strategy : strategies) {
            int minimalValue = Integer.MAX_VALUE;
            for (int element : strategy) {
                if (element < minimalValue) {
                    minimalValue = element;
                }
            }
            minimalOfLine.add(minimalValue);
        }

        //Вывод результатов поиска
        System.out.println("Минимум строки");
        for (int i = 0; i < minimalOfLine.size(); i++) {
            i++;
            System.out.print("Стратегия а" + i + ": ");
            i--;
            System.out.println(minimalOfLine.get(i));
        }

        //Создаем переменные для индекса максимального значения и максимума
        double maxValue = Double.MIN_VALUE;
        int indexOfMax = -1;

        for (int i = 0; i < minimalOfLine.size(); i++) {
            if (minimalOfLine.get(i) > maxValue) {
                maxValue = minimalOfLine.get(i);
                indexOfMax = i;
            }
        }

        return indexOfMax;
    }
}
