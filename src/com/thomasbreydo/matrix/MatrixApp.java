package com.thomasbreydo.matrix;

public class MatrixApp {
  public static void main(String[] args) {
    double[][] array2d =
        new double[][] {
          {1, 3},
          {2, 6},
        };

    Matrix original = new Matrix(array2d);
    System.out.println("Original:");
    System.out.println(original);

    Matrix inverted = original.invert();
    System.out.println("Inverted:");
    System.out.println(inverted);

    System.err.println(original.augment());
    System.err.println(original.augment().rowReduce());
  }
}
