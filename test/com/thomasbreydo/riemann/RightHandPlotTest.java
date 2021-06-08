import org.dalton.polyfun.Polynomial;
import org.junit.Test;
import org.opensourcephysics.frames.PlotFrame;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class RightHandPlotTest {
  @Test
  public void getSubintervalArea() {
    Polynomial poly = new Polynomial(new double[] {0, 0, 1}); // x^2
    RightHandPlot rightHandPlot =
        new RightHandPlot(poly, new PlotFrame("x", "y", "Right Hand Rule"), 0, 2, 10);
  }
}
