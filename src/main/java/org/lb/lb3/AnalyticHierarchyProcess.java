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

        ArrayList<ArrayList<Double>> matrix = getMatrix(Apoln, Bpoln, Cpoln, Dpoln);

        ArrayList<Double> critMatrix = new ArrayList<>();

        for (ArrayList<Double> doubles : critPoln) {
            critMatrix.add(doubles.get(doubles.size() - 1));
        }

        ArrayList<Double> resultVector = multiplyMatrixAndVector(matrix, critMatrix);




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
}
