package com.thomasbreydo.riemann;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Iterator;

public abstract class AbstractRiemann {
  public Function func;
  public PlotFrame plotFrame;
  public double xLower;
  public double xUpper;
  public int subintervals; // number of subintervals
  final Trail polyTrail = new Trail();
  final Trail accTrail = new Trail();
  static final double YMIN = -25;
  static final double YMAX = 25;

  public AbstractRiemann(
      Function func, PlotFrame plotFrame, double xLower, double xUpper, int subintervals) {
    this.func = func;
    this.plotFrame = plotFrame;
    this.xLower = xLower;
    this.xUpper = xUpper;
    this.subintervals = subintervals;
  }

  public double calculateDeltaX() {
    return (xUpper - xLower) / subintervals;
  }

  public double getIntervalArea() {
    double area = 0;
    for (BorderPair bp : borderPairs()) area += getSubintervalArea(bp.L, bp.R);
    return area;
  }

  public void drawRiemannSlices() {
    for (BorderPair bp : borderPairs()) drawSlice(bp.L, bp.R);
  }

  public void configPlotFrame() {
    configPlotFrame(YMIN, YMAX);
  }

  public void configPlotFrame(double ymin, double ymax) {
    plotFrame.setPreferredMinMax(xLower, xUpper, ymin, ymax);
    plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    plotFrame.setVisible(true);
    plotFrame.setConnected(true);
    polyTrail.color = Color.BLUE;
    accTrail.color = Color.RED;
    plotFrame.addDrawable(polyTrail);
    plotFrame.addDrawable(accTrail);
  }

  public void plotPolynomial() {
    plotPolynomial(100);
  }

  public void plotPolynomial(int nPoints) {
    double dx = (xUpper - xLower) / (nPoints - 1);
    for (double x = xLower; x <= xUpper; x += dx) {
      polyTrail.addPoint(x, func.eval(x));
    }
  }

  public void plotAccFunc() {
    accTrail.addPoint(xLower, 0);
    double area = 0;
    for (BorderPair bp : borderPairs()) {
      area += getSubintervalArea(bp.L, bp.R);
      accTrail.addPoint(bp.R, area);
    }
  }

  public abstract double getSubintervalArea(double leftBorder, double rightBorder);

  public abstract void drawSlice(double leftBorder, double rightBorder);

  void drawPath(double[] xCoords, double[] yCoords) {
    if (xCoords.length != yCoords.length) {
      throw new IllegalArgumentException("xCoords.length != yCoords.length");
    }
    Path2D path = new Path2D.Double();
    path.moveTo(xCoords[0], yCoords[0]);
    for (int i = 1; i < xCoords.length; i++) {
      path.lineTo(xCoords[i], yCoords[i]);
    }
    path.closePath(); // draw back to start
    plotFrame.addDrawable(new DrawableShape(path, 0, 0));
  }

  Iterable<BorderPair> borderPairs() {
    return new BorderPairIterable(this);
  }

  static class BorderPair {
    public double L;
    public double R;
  }

  static class BorderPairIterator implements Iterator<BorderPair> {
    final double X_LOWER;
    final double DX;
    final int SUBINTERVALS;
    int i = 0;

    public BorderPairIterator(AbstractRiemann riemann) {
      X_LOWER = riemann.xLower;
      DX = riemann.calculateDeltaX();
      SUBINTERVALS = riemann.subintervals;
    }
    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code
     * true} if {@link #next} would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
      return i < SUBINTERVALS;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     */
    @Override
    public BorderPair next() {
      BorderPair bp = new BorderPair();
      bp.L = X_LOWER + DX * i;
      bp.R = bp.L + DX;
      ++i;
      return bp;
    }
  }

  static class BorderPairIterable implements Iterable<BorderPair> {
    final AbstractRiemann RIEMANN;

    public BorderPairIterable(AbstractRiemann riemann) {
      this.RIEMANN = riemann;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<BorderPair> iterator() {
      return new BorderPairIterator(RIEMANN);
    }
  }
}
