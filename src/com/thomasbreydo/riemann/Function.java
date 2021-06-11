package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;

import java.util.function.DoubleUnaryOperator;

public class Function {
  Polynomial p;
  DoubleUnaryOperator o;

  Function(Polynomial polynomial) {
    p = polynomial;
  }

  Function(DoubleUnaryOperator operator) {
    o = operator;
  }

  public double eval(double x) {
    if ((o != null) == (p != null)) throw new RuntimeException();
    if (o != null) {
      return o.applyAsDouble(x);
    }
    return p.eval(x);
  }

  public String toString() {
    if ((o != null) == (p != null)) throw new RuntimeException();
    if (o != null) {
      return o.toString();
    }
    return p.toString();
  }
}
