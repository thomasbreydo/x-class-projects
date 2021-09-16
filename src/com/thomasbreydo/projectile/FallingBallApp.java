package com.thomasbreydo.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class FallingBallApp extends AbstractSimulation {
  static final int RADIUS = 10; // (m)
  static final int MASS = 7; // (kg)
  static final int INITIAL_X_1 = -5;
  static final int INITIAL_X_2 = 5;
  static final int INITIAL_Y = 100; // (m)
  static final double DELTA_TIME = 0.1; // (s)
  static final double AIR_PRESSURE = 1.225; // at sea level, 15ºC (kg/m³)
  final PlotFrame simulationFrame = new PlotFrame("x", "y", "Falling Ball Simulation");
  final PlotFrame heightFrame = new PlotFrame("seconds", "height (m)", "Height over Time");
  final PlotFrame velocityFrame = new PlotFrame("seconds", "velocity (m/s)", "Velocity over Time");
  final PlotFrame accFrame =
      new PlotFrame("seconds", "acceleration (m/s²)", "Acceleration over Time");
  final PlotFrame[] plotFrames = {simulationFrame, heightFrame, velocityFrame, accFrame};
  Particle particle1;
  Particle particle2;
  Trail height1;
  Trail height2;
  Trail velocity1;
  Trail velocity2;
  Trail acc1;
  Trail acc2;
  Particle[] particles;
  double elapsedTime;

  public static void main(String[] args) {
    SimulationControl.createApp(new FallingBallApp());
  }

  @Override
  public void initialize() {
    elapsedTime = 0;

    for (PlotFrame plotFrame : plotFrames) {
      plotFrame.clearDrawables();
      plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      plotFrame.setVisible(true);
    }

    particle1 = new Particle(RADIUS, MASS, INITIAL_X_1, INITIAL_Y);
    particle2 = new Particle(RADIUS, MASS, INITIAL_X_2, INITIAL_Y);
    particle1.color = Color.RED;
    particle2.color = Color.GREEN;
    particles = new Particle[] {particle1, particle2};

    for (Particle particle : particles) simulationFrame.addDrawable(particle);

    height1 = new Trail();
    height2 = new Trail();
    height1.color = Color.RED;
    height2.color = Color.GREEN;
    heightFrame.addDrawable(height1);
    heightFrame.addDrawable(height2);

    velocity1 = new Trail();
    velocity2 = new Trail();
    velocity1.color = Color.RED;
    velocity2.color = Color.GREEN;
    velocityFrame.addDrawable(velocity1);
    velocityFrame.addDrawable(velocity2);

    acc1 = new Trail();
    acc2 = new Trail();
    acc1.color = Color.RED;
    acc2.color = Color.GREEN;
    accFrame.addDrawable(acc1);
    accFrame.addDrawable(acc2);

    Trail[] trails = {height1, height2, acc1, acc2, velocity1, velocity2};
    for (Trail trail : trails) {
      trail.setStroke(new BasicStroke(3));
    }

    simulationFrame.setPreferredMinMax(-7, 7, 0, 100);
    heightFrame.setPreferredMinMax(0, 7, 0, 100);
    velocityFrame.setPreferredMinMax(0, 7, -50, 0);
    accFrame.setPreferredMinMax(0, 7, -10, 0);
  }

  @Override
  public void reset() {}

  public void doStep() {
    height1.addPoint(elapsedTime, particle1.getY());
    height2.addPoint(elapsedTime, particle2.getY());
    velocity1.addPoint(elapsedTime, particle1.getVelocity());
    velocity2.addPoint(elapsedTime, particle2.getVelocity());
    acc1.addPoint(elapsedTime, particle1.getAcceleration());
    acc2.addPoint(elapsedTime, particle2.getAcceleration());
    elapsedTime += DELTA_TIME;

    particle1.step(DELTA_TIME);
    particle2.step(DELTA_TIME, AIR_PRESSURE);
  }
}
