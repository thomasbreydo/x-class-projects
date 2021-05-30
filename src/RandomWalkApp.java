import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;

public class RandomWalkApp extends AbstractSimulation {
  static final int N_CIRCLES = 50;
  PlotFrame plotFrame = new PlotFrame("x", "y", "Random Walk");
  Circle[] circles = new Circle[N_CIRCLES];

  @Override
  public void reset() {
    control.setValue("Starting Y position", 0);
    control.setValue("Starting X position", 0);
  }

  @Override
  public void initialize() {
    double startingY = control.getDouble("Starting Y position");
    double startingX = control.getDouble("Starting X position");
    for (int i = 0; i < N_CIRCLES; ++i) {
      Circle circle = new Circle();
      circle.setY(startingY);
      circle.setX(startingX);
      plotFrame.addDrawable(circle);
      circles[i] = circle;
    }

    plotFrame.setPreferredMinMax(-25, 25, -25, 25);
    plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    plotFrame.setVisible(true);
  }

  public void doStep() {
    int dx, dy;
    for (Circle circle : circles) {
      dx = Math.random() < 0.5 ? 1 : -1;
      dy = Math.random() < 0.5 ? 1 : -1;
      circle.setY(circle.getY() + dy);
      circle.setX(circle.getX() + dx);
    }
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new RandomWalkApp());
  }
}
