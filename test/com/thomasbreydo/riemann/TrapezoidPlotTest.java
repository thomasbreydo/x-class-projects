package com.thomasbreydo.riemann;

import org.dalton.polyfun.Polynomial;
import org.junit.Test;
import org.opensourcephysics.frames.PlotFrame;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LeftHandPlotTest {
  @Test
  public void getSubintervalArea() {
    Polynomial poly = new Polynomial(new double[] {0, 0, 1}); // x^2
    PlotFrame plotFrame = new PlotFrame("x", "y", "Left Hand Rule");
    LeftHandPlot leftHandPlot = new LeftHandPlot(poly, plotFrame, 0, 2, 10);
    assertThat(leftHandPlot.getIntervalArea(), is(3.08));
  }
}
