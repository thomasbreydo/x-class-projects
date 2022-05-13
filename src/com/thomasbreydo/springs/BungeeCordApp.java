package com.thomasbreydo.springs;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

public class BungeeCordApp extends AbstractSimulation {
  static final double DELTA_TIME = 0.1; // (s)
  PlotFrame plotFrame;
  Spring2D cord;

  public static void main(String[] args) {
    SimulationControl.createApp(new BungeeCordApp());
  }

  @Override
  public void initialize() {
    super.initialize();
    double startX = control.getDouble("Start X");
    double startY = control.getDouble("Start Y");
    double endX = control.getDouble("End X");
    double endY = control.getDouble("End Y");
    double attachedMass = control.getDouble("Attached mass (kg)");
    double cordMass = control.getDouble("Cord mass (kg)");
    double springConstant = control.getDouble("Spring constant (N/m)");
    int nJoints = control.getInt("Number of joints");

    plotFrame = new PlotFrame("x", "y", "Part 1: Bungee Cord");
    plotFrame.setVisible(true);
    plotFrame.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    plotFrame.clearDrawables();
    plotFrame.setPreferredMinMax(-10, 10, 0, startY);

    Vector2D start = new Vector2D(startX, startY);
    Vector2D end = new Vector2D(endX, endY);

    cord = new Spring2D(springConstant, cordMass, attachedMass, nJoints, start, end);
    plotFrame.addDrawable(cord);
  }

  @Override
  public void reset() {
    super.reset();

    control.setValue("Start X", 0.0);
    control.setValue("Start Y", 200.0);
    control.setValue("End X", 5.0);
    control.setValue("End Y", 150.0);
    control.setValue("Attached mass (kg)", 50.0);
    control.setValue("Cord mass (kg)", 10.0);
    control.setValue("Spring constant (N/m)", 10.0);
    control.setValue("Number of joints", 10);
  }

  @Override
  protected void doStep() {
    cord.step(DELTA_TIME);
  }
}
