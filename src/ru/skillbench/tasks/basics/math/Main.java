package ru.skillbench.tasks.basics.math;

public class Main {
    public static void main(String[] args) {
        ArrayVectorImpl test = new ArrayVectorImpl();
        double[] testArray = {3, 5, 6, 7, 1};

        test.set(testArray);
        test.sortAscending();
        for (int i = 0; i < test.getSize(); i++) {
            System.out.println("arr[" + i + "] = "
                    + test.get(i));
        }
        System.out.println(test.getMax());
        System.out.println(test.getMin());
        test.scalarMult(test);
        for (int i = 0; i < test.getSize(); i++) {
            System.out.println("arr[" + i + "] = "
                    + test.get(i));
        }
    }
}
