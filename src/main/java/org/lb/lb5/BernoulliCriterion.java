package org.lb.lb5;

import java.util.ArrayList;
import java.util.List;

public class BernoulliCriterion {

    private BernoulliCriterion() {

    }

    //Проверка критерия бернулли
    public static int checkBernoulliCriterion(int[][] strategies) {
        //Создаем массив для хранения математических ожиданий каждой стратегии игрока
        List<Double> mathWait = new ArrayList<>();
        //Длина строки матрицы
        int size = strategies[0].length;
        for (int[] strategy : strategies) {
            //Суммируем строку, начальная сумма 0
            int sum = 0;
            for (int element : strategy) {
                sum += element;
            }
            mathWait.add((double) sum/size);
        }

        System.out.println("Математическое ожидание для строк");
        for (int i = 0; i < mathWait.size(); i++) {
            i++;
            System.out.print("Стратегия а" + i + ": ");
            i--;
            System.out.println(mathWait.get(i));
        }

        //Создаем переменные для индекса максимального значения и максимума
        double maxValue = Double.MIN_VALUE;
        int indexOfMax = -1;

        for (int i = 0; i < mathWait.size(); i++) {
            if (mathWait.get(i) > maxValue) {
                maxValue = mathWait.get(i);
                indexOfMax = i;
            }
        }

        return indexOfMax;
    }
}
