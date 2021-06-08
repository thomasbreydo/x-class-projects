package com.thomasbreydo.riemann;

import org.opensourcephysics.frames.PlotFrame;

public class LeftHandPlot extends AbstractRiemann {

  public LeftHandPlot(
      Function func, PlotFrame plotFrame, double xLower, double xUpper, int subintervals) {
    super(func, plotFrame, xLower, xUpper, subintervals);
  }

  @Override
  public double getSubintervalArea(double leftBorder, double rightBorder) {
    return (rightBorder - leftBorder) * func.eval(leftBorder);
  }

  @Override
  public void drawSlice(double leftBorder, double rightBorder) {
    double height = func.eval(leftBorder);
    drawPath(
        new double[] {leftBorder, leftBorder, rightBorder, rightBorder},
        new double[] {0, height, height, 0});
  }
}
