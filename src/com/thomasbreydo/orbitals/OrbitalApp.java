package com.thomasbreydo.orbitals;

import com.thomasbreydo.physics.Vector2D;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

public class OrbitalApp extends AbstractSimulation {
  static final double HOURS = 3600; // (s)
  static final double DELTA_TIME = 12 * HOURS;
  PlotFrame plotFrame;
  CelestialSystem system;
  CelestialBody earth;
  CelestialBody sun;

  public static void main(String[] args) {
    SimulationControl.createApp(new OrbitalApp());
  }

  public static Vector2D vec(double x, double y) {
    return new Vector2D(x, y);
  }

  @Override
  public void initialize() {
    super.initialize();

    int fps = 90;
    setDelayTime(1000 / fps);

    double sunMass = control.getDouble("Sun mass (kg)");
    double radius = control.getDouble("Distance (m)");
    double earthMass = control.getDouble("Earth mass (kg)");
    double earthVx = control.getDouble("Earth Vx (m/s)");
    double earthVy = control.getDouble("Earth Vy (m/s)");

    plotFrame = new PlotFrame("x", "y", "Orbitals");
    plotFrame.setVisible(true);
    plotFrame.setPreferredMinMax(-1.5 * radius, 1.5 * radius, -1.5 * radius, 1.5 * radius);
    plotFrame.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    plotFrame.clearDrawables();

    system = new CelestialSystem();
    plotFrame.addDrawable(system);

    sun = new CelestialBody(vec(0, 0), vec(0, 0), sunMass, system);
    earth = new CelestialBody(vec(radius, 0), vec(earthVx, earthVy), earthMass, system);

    system.addBody(sun);
    system.addBody(earth);
  }

  @Override
  public void reset() {
    super.reset();

    control.setValue("Sun mass (kg)", 1.989E30);
    control.setValue("Earth mass (kg)", 5.972E24);
    control.setValue("Distance (m)", 1.513E11);
    control.setValue("Earth Vx (m/s)", 0.0);
    control.setValue("Earth Vy (m/s)", -29831.3);
  }

  @Override
  protected void doStep() {
    system.step(DELTA_TIME);
  }
}
