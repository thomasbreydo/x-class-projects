package com.thomasbreydo.riemann;

import org.opensourcephysics.frames.PlotFrame;

public class AppxPi {
  static final Function func = new Function((x) -> Math.sqrt(1 - x * x));
  static final double YMIN = -1;
  static final double YMAX = 1;
  static final double L = -1;
  static final double R = 1;
  static final int subintervals = 10000;
  static final PlotFrame trapFrame = new PlotFrame("x", "y", "Trapezoid rule");
  static final TrapezoidPlot trapPlot = new TrapezoidPlot(func, trapFrame, L, R, subintervals);

  public static void main(String[] args) {
    trapPlot.configPlotFrame(YMIN, YMAX);
    trapPlot.drawRiemannSlices();
    trapPlot.plotPolynomial();
    trapPlot.plotAccFunc();
    System.out.println("Function: sqrt(1 - X^2)");
    System.out.println("Interval: " + L + " to " + R);
    System.out.println("Subintervals: " + subintervals);
    double appx = trapPlot.getIntervalArea();
    System.out.println("Trapezoid rule est. area: " + String.format("%.2f", appx));
    System.out.println("Approximation of π: " + 2 * appx);
  }
}
