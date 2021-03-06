import org.dalton.polyfun.Polynomial;

import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.Color;

/** Introduction to Polynomials, Open Source Physics and JUnit test. */
public class GettingStarted {
  public static void main(String[] args) {
    // Intro to Polynomials
    Polynomial gx = new Polynomial(new double[] {5, 0, 0, 0, 4}); // const -> x^4
    Polynomial vx = new Polynomial(new double[] {1, 2, 3}); // const -> x^2
    Polynomial fx = new Polynomial(new double[] {0, 0, 1}); // const -> x^2
    System.out.println("1. g(x) = " + gx);
    System.out.println("2. v(2) = " + vx.eval(2));
    System.out.println("3. f(x) + v(x) = " + fx.plus(vx));
    System.out.println("4. " + vx.getCoefficientAtTerm(1));
    System.out.println("5. Coefficients for v(x):");
    for (double c : vx.getCoefficientArray()) {
      System.out.println(c);
    }

    // Intro to OSP
    PlotFrame plotFrame = new PlotFrame("x", "y", "Getting Started");
    plotFrame.setVisible(true); // need this to show the graph, it is false by default
    plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    plotFrame.setPreferredMinMax(0, 10, 0, 15); // x and y ranges
    plotFrame.setConnected(true);
    plotFrame.setLineColor(0, Color.RED);
    plotFrame.setLineColor(1, Color.GREEN);
    Trail gTrail = new Trail();
    gTrail.color = Color.ORANGE;
    plotFrame.addDrawable(gTrail);
    for (int x = 0; x <= 10; ++x) {
      plotFrame.append(0, x, fx.eval(x));
      plotFrame.append(1, x, vx.eval(x));
      gTrail.addPoint(x, gx.eval(x));
    }
  }
}
