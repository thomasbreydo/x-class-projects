package com.thomasbreydo.matrix;

import org.dalton.polyfun.Polynomial;

import java.util.Scanner;

public class PolyInterpolationApp {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Enter non-negative polynomial degree: ");
    int degree = scan.nextInt();
    if (degree < 0) throw new RuntimeException("degree must be non-negative");

    Matrix m = new Matrix(degree + 1, degree + 2);
    for (int i = 0; i < degree + 1; ++i) {
      System.out.printf("Enter point %d/%d (space-separated): ", i + 1, degree + 1);
      double x = scan.nextDouble();
      double y = scan.nextDouble();
      double term = 1;
      for (int j = 0; j < degree + 1; ++j) {
        m.setEntry(i, j, term);
        term *= x;
      }
      m.setEntry(i, degree + 1, y);
    }

    System.out.print("\nP(X) = ");
    Polynomial p = new Polynomial(m.rowReduce().getColumn(degree + 1));
    System.out.println(p);
  }
}
