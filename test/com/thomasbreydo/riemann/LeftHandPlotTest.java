package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.junit.Test;
import org.opensourcephysics.frames.PlotFrame;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeftHandPlotTest {
  static final double DELTA = 1e-5; // tolerance for float checks

  @Test
  public void getSubintervalArea() {
    Function func = new Function(new Polynomial(new double[] {0, 0, 1})); // x^2
    PlotFrame plotFrame = new PlotFrame("x", "y", "Left Hand Rule");
    LeftHandPlot leftHandPlot = new LeftHandPlot(func, plotFrame, 0, 2, 10);
    assertEquals(leftHandPlot.getIntervalArea(), 2.28, DELTA);
  }
}
