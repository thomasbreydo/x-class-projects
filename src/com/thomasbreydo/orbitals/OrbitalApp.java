package com.thomasbreydo.orbitals;

import com.thomasbreydo.physics.Vector2D;
import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

public class OrbitalApp extends AbstractSimulation {
  static final double DELTA_TIME = 0.1; // (s)
  PlotFrame plotFrame;
  CelestialSystem system;

  public static void main(String[] args) {
    SimulationControl.createApp(new OrbitalApp());
  }

  @Override
  public void initialize() {
    super.initialize();
    double sunMass = control.getDouble("Sun mass (kg)");
    double earthMass = control.getDouble("Earth mass (kg)");
    double radius = control.getDouble("Distance (m)");

    plotFrame = new PlotFrame("x", "y", "Orbitals");
    plotFrame.setVisible(true);
    plotFrame.setPreferredMinMax(-1.1 * radius, 1.1 * radius, -1.1 * radius, 1.1 * radius);
    plotFrame.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    plotFrame.clearDrawables();

    system = new CelestialSystem();
    plotFrame.addDrawable(system);

    Vector2D zero = new Vector2D(0, 0);
    CelestialBody sun = new CelestialBody(zero, zero, sunMass, system);
    CelestialBody earth = new CelestialBody(new Vector2D(radius, 0), zero, earthMass, system);

    system.addBody(sun);
    system.addBody(earth);
  }

  @Override
  public void reset() {
    super.reset();

    control.setValue("Sun mass (kg)", 2e30);
    control.setValue("Earth mass (kg)", 6e24);
    control.setValue("Distance (m)", 1.5e11); // 29831.3 m/s vt
  }

  @Override
  protected void doStep() {
    system.step(DELTA_TIME);
  }
}
