package com.thomasbreydo.matrix;

public class MatrixApp {
  public static void main(String[] args) {
    double[][] array2d =
        new double[][] {
          {1, 0, 0},
          {0, 2, 0},
          {0, 0, 3},
        };

    Matrix original = new Matrix(array2d);
    System.out.println("Original:");
    System.out.println(original);

    Matrix inverted = original.invert();
    System.out.println("Inverted:");
    System.out.println(inverted);
  }
}
