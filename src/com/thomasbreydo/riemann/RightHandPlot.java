package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.frames.PlotFrame;

public class RightHandPlot extends AbstractRiemann {
  public RightHandPlot(
      Function func, PlotFrame plotFrame, double xLower, double xUpper, int subintervals) {
    super(func, plotFrame, xLower, xUpper, subintervals);
  }

  @Override
  public double getSubintervalArea(double leftBorder, double rightBorder) {
    return (rightBorder - leftBorder) * func.eval(rightBorder);
  }

  @Override
  public void drawSlice(double leftBorder, double rightBorder) {
    double height = func.eval(rightBorder);
    drawPath(
        new double[] {leftBorder, leftBorder, rightBorder, rightBorder},
        new double[] {0, height, height, 0});
  }
}
