package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.frames.PlotFrame;

public class RiemannApp {
  // 7th-degree Maclaurin expansion of sin(x)
  static final Function func =
      new Function(new Polynomial(new double[] {0, 1, 0, -1.0 / 6, 0, 1.0 / 120, 0, -1.0 / 5040}));
  static final double YMIN = -2;
  static final double YMAX = 2;
  static final double L = -Math.PI;
  static final double R = Math.PI;
  static final int subintervals = 10;
  static final PlotFrame leftFrame = new PlotFrame("x", "y", "Left-hand rule");
  static final PlotFrame rightFrame = new PlotFrame("x", "y", "Right-hand rule");
  static final PlotFrame trapFrame = new PlotFrame("x", "y", "Trapezoid rule");
  static final LeftHandPlot leftPlot = new LeftHandPlot(func, leftFrame, L, R, subintervals);
  static final RightHandPlot rightPlot = new RightHandPlot(func, rightFrame, L, R, subintervals);
  static final TrapezoidPlot trapPlot = new TrapezoidPlot(func, trapFrame, L, R, subintervals);

  public static void main(String[] args) {
    leftPlot.configPlotFrame(YMIN, YMAX);
    rightPlot.configPlotFrame(YMIN, YMAX);
    trapPlot.configPlotFrame(YMIN, YMAX);
    leftPlot.drawRiemannSlices();
    rightPlot.drawRiemannSlices();
    trapPlot.drawRiemannSlices();
    leftPlot.plotPolynomial();
    rightPlot.plotPolynomial();
    trapPlot.plotPolynomial();
    leftPlot.plotAccFunc();
    rightPlot.plotAccFunc();
    trapPlot.plotAccFunc();
    System.out.println("Polynomial: " + func);
    System.out.println("Interval: " + L + " to " + R);
    System.out.println("Subintervals: " + subintervals);
    System.out.println(
        "Left-hand rule est. area: " + String.format("%.2f", leftPlot.getIntervalArea()));
    System.out.println(
        "Right-hand rule est. area: " + String.format("%.2f", rightPlot.getIntervalArea()));
    System.out.println(
        "Trapezoid rule est. area: " + String.format("%.2f", trapPlot.getIntervalArea()));
  }
}
