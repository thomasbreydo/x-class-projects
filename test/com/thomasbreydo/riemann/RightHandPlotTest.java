package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.junit.Test;
import org.opensourcephysics.frames.PlotFrame;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class RightHandPlotTest {

  static final double DELTA = 1e-5; // tolerance for float checks

  @Test
  public void getSubintervalArea() {
    Function func = new Function(new Polynomial(new double[] {0, 0, 1})); // x^2
    PlotFrame plotFrame = new PlotFrame("x", "y", "Right Hand Rule");
    RightHandPlot rightHandPlot = new RightHandPlot(func, plotFrame, 0, 2, 10);
    assertEquals(rightHandPlot.getIntervalArea(), 3.08, DELTA);
  }
}
