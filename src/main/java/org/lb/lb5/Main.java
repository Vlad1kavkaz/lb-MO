package org.lb.lb5;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private Main() {

    }

    //Для цветного вывода
    private final static String GREEN = "\u001B[32m";
    private final static String ORANGE = "\u001B[33m";
    private final static String RESET = "\u001B[0m";

    private static void runNatureGames(int[][] strategies, double alpha) {

        //Получаем результат выполнения критерия бернулли
        System.out.println(GREEN + "Критерий Бернулли" + RESET);
        int indexFromBernoulli = BernoulliCriterion.checkBernoulliCriterion(strategies);
        System.out.println(ORANGE + "Результат выполнения критерия Бернулли: " + RESET +  "стратегия a"
                + (indexFromBernoulli + 1) + "\n");


        //Получаем результат выполнения критерия Вальда
        System.out.println(GREEN + "Критерий Вальда" + RESET);
        int indexFromWald = WaldCriterion.checkWaldCriterion(strategies);
        System.out.println(ORANGE + "Результат выполнения критерия Вальда: " + RESET +  "стратегия a"
                + (indexFromWald + 1) + "\n");

        //Получаем результат выполнения критерия максимума
        System.out.println(GREEN + "Критерий максимума" + RESET);
        int indexFromMax = MaximumCriterion.checkMaximumCriterion(strategies);
        System.out.println(ORANGE + "Результат выполнения критерия максимума: " + RESET + "стратегия a"
                + (indexFromMax + 1) + "\n");

        //Получаем результат выполнения критерия максимума
        System.out.println(GREEN + "Критерий Гурвица" + RESET);
        int indexFromHurwicz = HurwiczCriterion.checkHurwiczCriterion(strategies, alpha);
        System.out.println(ORANGE + "Результат выполнения критерия Гурвица: " + RESET + "стратегия a"
                + (indexFromHurwicz + 1) + "\n");

        //Получаем результат выполнения критерия Сэвиджа
        System.out.println(GREEN + "Критерий Сэвиджа" + RESET);
        int indexFromSavage = SavageCriterion.checkSavageCriterion(strategies);
        System.out.println(ORANGE + "Результат выполнения критерия Сэвиджа: " + RESET + "стратегия a"
                + (indexFromSavage + 1) + "\n");

        // Объединяем результаты в список
        List<Integer> results = List.of(indexFromBernoulli, indexFromWald, indexFromMax, indexFromHurwicz, indexFromSavage);

        // Получаем наиболее встречающиеся индексы
        List<Integer> mostFrequentIndexes = findMostFrequentIndexes(results);

        // Выводим результаты
        System.out.println("\n" + GREEN + "Результат работы программы" + RESET);
        if (mostFrequentIndexes.size() == 1) {
            System.out.println("Рекомендуемая стратегия: a" + (mostFrequentIndexes.get(0) + 1));
        } else {
            System.out.print("Рекомендуемые стратегии: ");
            for (int i = 0; i < mostFrequentIndexes.size(); i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print("a" + (mostFrequentIndexes.get(i) + 1));
            }
            System.out.println();
        }
    }

    // Метод для нахождения наиболее встречающихся индексов в списке
    private static List<Integer> findMostFrequentIndexes(List<Integer> indexes) {
        Map<Integer, Integer> countMap = new HashMap<>();

        // Считаем количество вхождений каждого индекса
        for (Integer index : indexes) {
            countMap.put(index, countMap.getOrDefault(index, 0) + 1);
        }

        // Находим максимальное количество вхождений
        int maxCount = 0;
        for (int count : countMap.values()) {
            maxCount = Math.max(maxCount, count);
        }

        // Формируем список индексов с максимальным количеством вхождений
        List<Integer> mostFrequentIndexes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostFrequentIndexes.add(entry.getKey());
            }
        }

        return mostFrequentIndexes;
    }

    public static void main(String[] args) {
        int[][] strategies = {
                {12, 5, 4, 9},
                {16, 0, 12, 0},
                {5, 13, 6, 7},
                {10, 10, 3, 16},
                {1, 11, 19, 1},
        };

        double alpha = 0.5;

        runNatureGames(strategies, alpha);
    }
}
