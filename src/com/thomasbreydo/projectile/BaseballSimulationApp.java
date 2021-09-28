package com.thomasbreydo.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import java.awt.*;

public class BaseballSimulationApp extends AbstractSimulation {
  public static final int DISTANCE_TO_WALL = 100;
  public static final int WALL_HEIGHT = 10;
  PlotFrame plotFrame = new PlotFrame("x", "y", "PlotGraph Simulation");
  Trail trail;
  Particle2D ball;
  static final double DELTA_TIME = 0.1; // (s)
  static final double AIR_PRESSURE = 1.225; // at sea level, 15ºC (kg/m³)

  @Override
  protected void doStep() {
    if (ball.getX() >= DISTANCE_TO_WALL && ball.getY() <= WALL_HEIGHT) {
      ball.bounceX();
    }
    ball.step(DELTA_TIME, AIR_PRESSURE);
  }

  @Override
  public void reset() {
    control.setValue("Radius of ball (m)", 0.035);
    control.setValue("Mass of ball (kg)", 0.14);
    control.setValue("Angle (degrees)", 45);
    control.setValue("Speed (m/s)", 34);
  }

  @Override
  public void initialize() {
    plotFrame.setPreferredMinMax(0, DISTANCE_TO_WALL, 0, 35);
    plotFrame.setVisible(true);
    plotFrame.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    plotFrame.clearDrawables();

    trail = new Trail();
    plotFrame.addDrawable(trail);
    trail.color = Color.GREEN;
    trail.setStroke(new BasicStroke(5));
    trail.addPoint(0, 0);
    trail.addPoint(DISTANCE_TO_WALL, 0);
    trail.addPoint(DISTANCE_TO_WALL, WALL_HEIGHT);

    double radius = control.getDouble("Radius of ball (m)");
    double mass = control.getDouble("Mass of ball (kg)");
    double angle = Math.toRadians(control.getDouble("Angle (degrees)"));
    double speed = control.getDouble("Speed (m/s)");

    ball = new Particle2D(radius, mass, 0, 1, angle, speed);
    plotFrame.addDrawable(ball);
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new BaseballSimulationApp());
  }
}
