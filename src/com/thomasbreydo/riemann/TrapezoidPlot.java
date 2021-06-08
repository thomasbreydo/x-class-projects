package com.thomasbreydo.riemann;

import org.opensourcephysics.frames.PlotFrame;

public class TrapezoidPlot extends AbstractRiemann {
  public TrapezoidPlot(
      Function func, PlotFrame plotFrame, double xLower, double xUpper, int subintervals) {
    super(func, plotFrame, xLower, xUpper, subintervals);
  }

  @Override
  public double getSubintervalArea(double leftBorder, double rightBorder) {
    return (rightBorder - leftBorder) * (func.eval(leftBorder) + func.eval(rightBorder)) / 2;
  }

  @Override
  public void drawSlice(double leftBorder, double rightBorder) {
    drawPath(
        new double[] {leftBorder, leftBorder, rightBorder, rightBorder},
        new double[] {0, func.eval(leftBorder), func.eval(rightBorder), 0});
  }
}
