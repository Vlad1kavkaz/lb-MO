package org.lb.lb3;

import java.util.ArrayList;

public class ParetoMethod {

    private ParetoMethod() {

    }

    public static final class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double manhettenLength(Point p1, Point p2) {
        return (p1.x-p2.x + p1.y-p2.y);
    }

    public static String runPareto (double [][] A,
                                    String[] alternative, int ind1, int ind2) {
        Point utopyPoint = new Point(5.0, 5.0);

        ArrayList<Point> setPareto = new ArrayList<>();

        for (double[] doubles : A) {
            Point P = new Point(doubles[ind1], doubles[ind2]);
            setPareto.add(P);
        }

        Point bestPoint = setPareto.get(0);
        for (Point point : setPareto) {
            if (manhettenLength(utopyPoint, point) < manhettenLength(utopyPoint, bestPoint)) {
                bestPoint = point;
            }
        }

        int index = 0;

        for (int i = 0; i < A.length; i++) {
            if (A[i][ind1] == bestPoint.x && A[i][ind2] == bestPoint.y) {
                index = i;
            }
        }

        return alternative[index];
    }
}
