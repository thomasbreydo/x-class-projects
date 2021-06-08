package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.junit.Test;
import org.opensourcephysics.frames.PlotFrame;

import static org.junit.Assert.assertEquals;

public class TrapezoidPlotTest {
  static final double DELTA = 1e-5; // tolerance for float checks

  @Test
  public void getSubintervalArea() {
    Function func = new Function(new Polynomial(new double[] {0, 0, 1})); // x^2
    PlotFrame plotFrame = new PlotFrame("x", "y", "Left Hand Rule");
    TrapezoidPlot trapezoidPlot = new TrapezoidPlot(func, plotFrame, 0, 2, 10);
    assertEquals(trapezoidPlot.getIntervalArea(), 2.68, DELTA);
  }
}
