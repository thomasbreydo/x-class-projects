import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;

enum Direction {
  W,
  S,
  E,
  N,
}

enum SegmentPart {
  ONE,
  TWO,
}

public class SpiralTrailApp extends AbstractSimulation {
  PlotFrame plotFrame = new PlotFrame("x", "y", "Spiral Trail");
  Trail trail = new Trail();
  Direction[] directions = new Direction[] {Direction.W, Direction.S, Direction.E, Direction.N};
  private int directionIndex;
  private int x;
  private int y;
  private SegmentPart segmentPart;
  private int segmentLength;
  private int drawnSoFar;
  private int nRemaining;

  void move() {
    updateX();
    updateY();
    updateDirection();
  }

  boolean timeToChangeDirection() {
    if (++drawnSoFar != segmentLength) return false;
    drawnSoFar = 0;
    if (segmentPart == SegmentPart.TWO) {
      segmentPart = SegmentPart.ONE;
      ++segmentLength;
    } else {
      segmentPart = SegmentPart.TWO;
    }
    return true;
  }

  void updateDirection() {
    if (timeToChangeDirection()) directionIndex = (directionIndex + 1) % 4;
  }

  void updateX() {
    switch (directions[directionIndex]) {
      case W -> --x;
      case E -> ++x;
    }
  }

  void updateY() {
    switch (directions[directionIndex]) {
      case S -> --y;
      case N -> ++y;
    }
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new SpiralTrailApp());
  }

  @Override
  public void initialize() {
    DrawableShape rect = DrawableShape.createRectangle(3, 3, 4, 4);
    plotFrame.addDrawable(rect);

    for (int x = 1; x <= 5; ++x) {
      for (int y = 1; y <= 5; ++y) {
        plotFrame.addDrawable(new Circle(x, y));
      }
    }

    plotFrame.addDrawable(trail);

    plotFrame.setPreferredMinMax(0, 6, 0, 6);
    plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    plotFrame.setVisible(true);
  }

  @Override
  public void reset() {
     directionIndex = 0;
     x = 3;
     y = 3;
     segmentPart = SegmentPart.ONE;
     segmentLength = 1;
     drawnSoFar = 0;
     nRemaining = 25;
     trail.clear();
  }

  @Override
  protected void doStep() {
    trail.addPoint(x, y);
    move();
    if (nRemaining-- == 0) reset();
  }
}
