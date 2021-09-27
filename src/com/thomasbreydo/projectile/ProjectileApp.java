package com.thomasbreydo.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class ProjectileApp extends AbstractSimulation {
  static final int RADIUS = 5; // (m)
  static final int MASS = 10; // (kg)
  static final int INITIAL_X = 0; // (m)
  static final int INITIAL_Y = 0; // (m)
  static final double DELTA_TIME = 0.05; // (s)
  static final double AIR_PRESSURE = 1.225; // at sea level, 15ºC (kg/m³)
  static final double[] INITIAL_VELOCITY = {49, 9};
  final PlotFrame plotFrame = new PlotFrame("x", "y", "Ball Simulation");
  Particle2D particle1;
  Particle2D particle2;
  Particle2D[] particles;

  public static void main(String[] args) {
    SimulationControl.createApp(new ProjectileApp());
  }

  @Override
  public void initialize() {
    plotFrame.clearDrawables();
    plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    plotFrame.setVisible(true);

    particle1 = new Particle2D(RADIUS, MASS, INITIAL_X, INITIAL_Y, INITIAL_VELOCITY);
    particle2 = new Particle2D(RADIUS, MASS, INITIAL_X, INITIAL_Y, INITIAL_VELOCITY);
    particle1.color = Color.RED;
    particle2.color = Color.GREEN;
    particles = new Particle2D[] {particle1, particle2};

    for (Particle2D particle : particles) plotFrame.addDrawable(particle);

    plotFrame.setPreferredMinMax(0, 100, 0, 10);
  }

  @Override
  public void reset() {}

  public void doStep() {
    if (particle1.getVelocityY() >= 0 || particle1.getY() > 0) particle1.step(DELTA_TIME);
    if (particle2.getVelocityY() >= 0 || particle2.getY() > 0)
      particle2.step(DELTA_TIME, AIR_PRESSURE);
  }
}
