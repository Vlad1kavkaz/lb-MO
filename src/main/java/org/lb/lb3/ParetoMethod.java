package org.lb.lb3;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;



public class ParetoMethod extends JFrame{

    private ParetoMethod() {

    }

    //Класс для удобства работы с точками

    public static final class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ";" + y + ")";
        }
    }

    public static void displayParetoGraph(ArrayList<Point> setPareto, Point utopiaPoint) {
        // Нахождение максимальных и минимальных значений координат точек
        double minX = setPareto.stream().mapToDouble(point -> point.x).min().orElse(0);
        double minY = setPareto.stream().mapToDouble(point -> point.y).min().orElse(0);
        double maxX = setPareto.stream().mapToDouble(point -> point.x).max().orElse(0);
        double maxY = setPareto.stream().mapToDouble(point -> point.y).max().orElse(0);

        minX = Math.min(minX, utopiaPoint.x);
        minY = Math.min(minY, utopiaPoint.y);
        maxX = Math.max(maxX, utopiaPoint.x);
        maxY = Math.max(maxY, utopiaPoint.y);

        double scale = 50;
        int padding = 100;
        int width = (int) ((maxX - minX) * scale) + 3 * padding;
        int height = (int) ((maxY - minY) * scale) + 3 * padding;

        JFrame frame = new JFrame("Множество Парето");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        double finalMinX = minX - 1;
        double finalMinY = minY - 1;
        double finalMaxY = maxY + 1;
        double finalMaxX = maxX + 1;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.LIGHT_GRAY);
                for (int x = (int) Math.ceil(finalMinX); x <= (int) Math.floor(finalMaxX); x++) {
                    int xPos = (int) ((x - finalMinX) * scale) + padding;
                    g.drawLine(xPos, padding, xPos, height - padding);
                    g.drawString(String.valueOf(x), xPos - 5, height - padding + 15);
                }
                for (int y = (int) Math.ceil(finalMinY); y <= (int) Math.floor(finalMaxY); y++) {
                    int yPos = (int) ((finalMaxY - y) * scale) + padding;
                    g.drawLine(padding, yPos, width - padding, yPos);
                    g.drawString(String.valueOf(y), padding - 35, yPos + 5);
                }

                for (Point point : setPareto) {
                    int x = (int) ((point.x - finalMinX) * scale) + padding;
                    int y = (int) ((finalMaxY - point.y) * scale) + padding;
                    g.setColor(Color.BLUE);
                    g.fillOval(x - 2, y - 2, 5, 5);
                }

                int x = (int) ((utopiaPoint.x - finalMinX) * scale) + padding;
                int y = (int) ((finalMaxY - utopiaPoint.y) * scale) + padding;
                g.setColor(Color.RED);
                g.fillOval(x - 3, y - 3, 7, 7);

                g.setColor(Color.BLACK);
                g.drawString("Точка утопии", x, y - 10);

                // Подписываем ось x
                g.drawString("y", padding - 50, height / 2);

                // Подписываем ось y
                g.drawString("x", width / 2, height - padding + 40);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }



    public static double manhettenLength(Point p1, Point p2) {
        return (p1.x-p2.x + p1.y-p2.y);
    }

    public static String runPareto (double [][] A,
                                    String[] alternative, int ind1, int ind2) {

        /*
         * Создаем точку утопии с координатами (5,5) так как оба критерия
         * Необходимо максимизировать*/
        Point utopyPoint = new Point(5.0, 5.0);

        //Инициализируем множество Парето
        ArrayList<Point> setPareto = new ArrayList<>();

        //Добавляем в него элементы
        for (double[] doubles : A) {
            Point P = new Point(doubles[ind1], doubles[ind2]);
            setPareto.add(P);
        }
        System.out.print("Множество Парето: ");
        for (Point p : setPareto) {
            System.out.print(p.toString()+ " ");
        }
        System.out.println();

        displayParetoGraph(setPareto, utopyPoint);

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

    public static void main(String[] args) {
        //Альтеранитивы санаториев
        String[] alternative = {"Липецк", "Сосновый бор", "Лесная жемчужина", "Сосны"};
        //Оценка первых двух критерив по пятибальной шкале
        double[][] A = {
                {5, 2},
                {2, 5},
                {3, 1},
                {4, 4}
        };
        String result = ParetoMethod.runPareto(A, alternative, 0, 1);
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";

        System.out.println(GREEN + "Результат работы программы." + RESET);
        System.out.println("Лучший выбор: "+result);
    }
}