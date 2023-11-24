package org.lb.lb2;

import java.util.Arrays;

public class BranchAndBound {

    private BranchAndBound() {

    }

    /*public static double[] branchAndBound(double[][] A, double[] b, double[] c, double[] currentSolution) {
        double[] bestIntegerSolution = currentSolution; // Для отслеживания наилучшего целочисленного решения

        // Начальное решение округляется до ближайшего целого
        for (int i = 1; i < currentSolution.length; i++) {
            currentSolution[i] = Math.round(currentSolution[i]);
        }

        BranchNode root = new BranchNode(A, b, c);

        while (root != null) {
            currentSolution = DualSimplexMethod.solveDualLinearProgram(root.A, root.b, root.c);

            if (isIntegerSolution(currentSolution) && currentSolution[0] < bestIntegerSolution[0]) {
                bestIntegerSolution = Arrays.copyOf(currentSolution, currentSolution.length);
            }


            int branchVar = selectBranchingVariable(currentSolution);

            BranchNode leftChild = root.createLeftChildNode(branchVar, currentSolution);
            BranchNode rightChild = root.createRightChildNode(branchVar, currentSolution);

            root = selectNextNodeToExpand(leftChild, rightChild, currentSolution, bestIntegerSolution);
        }
        bestIntegerSolution[0] = c[0]*-1*bestIntegerSolution[1] + c[1]*-1*bestIntegerSolution[2];

        return bestIntegerSolution;
    }

    private static boolean isIntegerSolution(double[] solution) {
        for (double value : solution) {
            if (Math.abs(value - Math.round(value)) > 1e-5) {
                return false;
            }
        }
        return true;
    }

    private static int selectBranchingVariable(double[] solution) {
        int branchingVar = -1;
        double maxFractionalPart = 0;

        for (int i = 1; i < solution.length; i++) {
            double fractionalPart = solution[i] - Math.floor(solution[i]);
            if (fractionalPart > maxFractionalPart) {
                maxFractionalPart = fractionalPart;
                branchingVar = i;
            }
        }

        return branchingVar;
    }

    private static BranchNode selectNextNodeToExpand(BranchNode leftChild, BranchNode rightChild, double[] currentSolution, double[] bestIntegerSolution) {
        if (leftChild.lowerBound >= rightChild.lowerBound && leftChild.lowerBound > Math.floor(currentSolution[0])) {
            return leftChild;
        } else if (rightChild.lowerBound >= leftChild.lowerBound && rightChild.lowerBound > Math.floor(currentSolution[0])) {
            return rightChild;
        } else if (bestIntegerSolution[0] > Math.floor(currentSolution[0])) {
            return null;
        } else {
            return null;
        }
    }

    private static class BranchNode {
        double[][] A;
        double[] b;
        double[] c;
        int branchingVar;
        double lowerBound;

        BranchNode(double[][] A, double[] b, double[] c) {
            this.A = A;
            this.b = b;
            this.c = c;
            this.branchingVar = -1;
            this.lowerBound = -1;
        }

        BranchNode createLeftChildNode(int branchingVar, double[] currentSolution) {
            double[][] leftChildA = Arrays.copyOf(A, A.length);
            double[] leftChildB = Arrays.copyOf(b, b.length);
            double[] leftChildC = Arrays.copyOf(c, c.length);

            leftChildB[branchingVar] = Math.floor(currentSolution[branchingVar]);
            this.branchingVar = branchingVar;

            return new BranchNode(leftChildA, leftChildB, leftChildC);
        }

        BranchNode createRightChildNode(int branchingVar, double[] currentSolution) {
            double[][] rightChildA = Arrays.copyOf(A, A.length);
            double[] rightChildB = Arrays.copyOf(b, b.length);
            double[] rightChildC = Arrays.copyOf(c, c.length);

            rightChildB[branchingVar] = Math.ceil(currentSolution[branchingVar]);
            this.branchingVar = branchingVar;

            return new BranchNode(rightChildA, rightChildB, rightChildC);
        }
    }*/
}
