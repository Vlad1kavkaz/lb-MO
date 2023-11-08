package org.lb.lb3;

import java.util.ArrayList;

public class AnalyticHierarchyProcess {

    public static String runAnalyticHierarchyProcess(String[] alternative,
                                                     double[][] A,
                                                     double[][] B,
                                                     double[][] C,
                                                     double[][] D,
                                                     double[][] critery) {
        ArrayList<ArrayList<Double>> Apoln = getPoln(A);
        ArrayList<ArrayList<Double>> Bpoln = getPoln(B);
        ArrayList<ArrayList<Double>> Cpoln = getPoln(C);
        ArrayList<ArrayList<Double>> Dpoln = getPoln(D);
        ArrayList<ArrayList<Double>> critPoln = getPoln(critery);

        System.out.println("Дополненная матрица А:");
        for (ArrayList<Double> item : Apoln) {
            System.out.println(item);
        }

        System.out.println("Дополненная матрица B:");
        for (ArrayList<Double> value : Bpoln) {
            System.out.println(value);
        }

        System.out.println("Дополненная матрица C:");
        for (ArrayList<Double> list : Cpoln) {
            System.out.println(list);
        }

        System.out.println("Дополненная матрица D:");
        for (ArrayList<Double> arrayList : Dpoln) {
            System.out.println(arrayList);
        }

        System.out.println("Дополненная матрица критериев:");
        for (ArrayList<Double> doubleArrayList : critPoln) {
            System.out.println(doubleArrayList);
        }

        //Получаем новую матрицу из столбцов нормализованных сумм
        System.out.println("Матрица из столбцов нормализованных сумм: ");
        ArrayList<ArrayList<Double>> matrix = getMatrix(Apoln, Bpoln, Cpoln, Dpoln);
        for (ArrayList<Double> arrayList : matrix) {
            System.out.println(arrayList);
        }

        //Получаем еще одну новую матрицу из нормализованной суммы критериев
        System.out.println("Матрица нормализованных сумм критериев: ");
        ArrayList<Double> critMatrix = new ArrayList<>();

        for (ArrayList<Double> doubles : critPoln) {
            critMatrix.add(doubles.get(doubles.size() - 1));
        }
        System.out.println(critMatrix);


        //Перемножаем две новые матрицы
        ArrayList<Double> resultVector = multiplyMatrixAndVector(matrix, critMatrix);
        System.out.println("Результат перемножения матриц: ");
        System.out.println(resultVector);

        //Возвращаем строку с индексом максимума из результата перемножения
        return alternative[findMaxIndex(resultVector)];
    }

    public static int findMaxIndex(ArrayList<Double> vector) {
        double max = Double.NEGATIVE_INFINITY;
        int maxIndex = -1;

        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) > max) {
                max = vector.get(i);
                maxIndex = i;
            }
        }

        return maxIndex;
    }


    public static ArrayList<Double> multiplyMatrixAndVector(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> vector) {
        int numColsA = matrix.get(0).size();
        int numRowsB = vector.size();

        if (numColsA != numRowsB) {
            throw new IllegalArgumentException("Несоответствие размеров матрицы и вектора");
        }

        ArrayList<Double> result = new ArrayList<>();

        for (ArrayList<Double> doubles : matrix) {
            double sum = 0.0;
            for (int j = 0; j < numColsA; j++) {
                sum += doubles.get(j) * vector.get(j);
            }
            result.add(sum);
        }

        return result;
    }



    public static ArrayList<ArrayList<Double>> getMatrix(ArrayList<ArrayList<Double>> A,
                                                         ArrayList<ArrayList<Double>> B,
                                                         ArrayList<ArrayList<Double>> C,
                                                         ArrayList<ArrayList<Double>> D) {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();

        for (int i = 0; i < A.size(); i++) {
            ArrayList<Double> resLine = new ArrayList<>();
            resLine.add(A.get(i).get(A.get(i).size() - 1));
            resLine.add(B.get(i).get(B.get(i).size() - 1));
            resLine.add(C.get(i).get(C.get(i).size() - 1));
            resLine.add(D.get(i).get(D.get(i).size() - 1));
            res.add(resLine);
        }

        return res;
    }

    public static ArrayList<ArrayList<Double>> getPoln(double[][] matrix) {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for (double[] row : matrix) {
            double sum = 0;
            ArrayList<Double> resLine = new ArrayList<>();
            for (double value : row) {
                resLine.add(value);
                sum += value;
            }
            resLine.add(sum);
            res.add(resLine);
        }

        // Вычисляем нормализованные суммы
        for (ArrayList<Double> row : res) {
            double normalizedSum = row.get(row.size() - 1) / sumColumn(res, row.size() - 1);
            row.add(normalizedSum);
        }

        return res;
    }

    public static double sumColumn(ArrayList<ArrayList<Double>> matrix, int columnIndex) {
        double sum = 0;
        for (ArrayList<Double> row : matrix) {
            sum += row.get(columnIndex);
        }
        return sum;
    }

    public static void main(String [] args) {

        //Сравнение альтернатив для качетсва лечения
        double[][] A = {
                {1, 3, 5, 7},
                {0.33333, 1, 0.33333, 0.2},
                {0.2, 3, 1, 0.33333},
                {0.14268, 5, 3, 1}
        };

        //Сравнение альтернатив для уровня сервиса
        double[][] B = {
                {1, 7, 1, 0.5},
                {0.14268, 1, 0.33333, 0.14268},
                {1, 3, 1, 0.2},
                {5, 7, 5, 1}
        };

        //Сравнение альтернатив для качетсва питания
        double[][] C = {
                {1, 1, 0.2, 0.14268},
                {1, 1, 0.2, 0.14},
                {5, 5, 1, 0.33333},
                {7, 7, 3, 1}
        };

        //Сравнение альтернатив для удаленности от Москвы
        double[][] D = {
                {1, 3, 3, 5},
                {0.33333, 1, 1, 3},
                {0.33333, 1, 1, 3},
                {0.2, 0.33333, 0.33333, 1}
        };

        //Оценка приоритетов критериев
        double[][] critery = {
                {1, 3, 5, 7},
                {0.33333, 1, 3, 5},
                {0.2, 0.33333, 1, 3},
                {0.14286, 0.2, 0.33333, 1}
        };
        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};


        String result = runAnalyticHierarchyProcess(alternative, A, B, C, D, critery);
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        System.out.println(GREEN + "Результат работы программы." + RESET);
        System.out.println("Лучший выбор: " + result);
    }
}
