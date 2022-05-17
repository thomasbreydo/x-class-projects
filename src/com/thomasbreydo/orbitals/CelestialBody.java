package com.thomasbreydo.orbitals;

import com.thomasbreydo.physics.FreeBody;
import com.thomasbreydo.physics.Vector2D;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display.Trail;

import java.awt.*;
import java.util.ArrayList;

public class CelestialBody extends FreeBody implements Drawable {
  private static final double G = 6.673889E-11;
  Circle circle;
  boolean showTrail;
  Trail trail;
  CelestialSystem system;
  ArrayList<Vector2D> forces;

  CelestialBody(Vector2D position, Vector2D velocity, double mass, CelestialSystem system) {
    this(position, velocity, mass, system, true);
  }

  CelestialBody(
      Vector2D position,
      Vector2D velocity,
      double mass,
      CelestialSystem system,
      boolean showTrail) {
    super(position, velocity, mass);
    this.system = system;
    this.showTrail = showTrail;

    circle = new Circle(this.getX(), this.getY(), 5);
    forces = new ArrayList<>();
    netForce = new Vector2D(0, 0);
    if (showTrail) {
      trail = new Trail();
    }
  }

  public Vector2D forceExertedBy(CelestialBody other) {
    double r = Vector2D.distanceBetween(this.getPosition(), other.getPosition());
    double magnitude = G * this.getMass() * other.getMass() / (r * r);
    return other.getPosition().minus(this.getPosition()).scaledToNorm(magnitude);
  }

  public void clearForces() {
    forces.clear();
    netForce = new Vector2D(0, 0);
  }

  public void addForce(Vector2D force) {
    forces.add(force);
    netForce.add(force);
  }

  @Override
  public void draw(DrawingPanel drawingPanel, Graphics graphics) {
    circle.setXY(getX(), getY());
    circle.draw(drawingPanel, graphics);
    if (showTrail) {
      trail.addPoint(getX(), getY());
      trail.draw(drawingPanel, graphics);
    }
  }

  @Override
  public void step(double deltaTime) {
    super.step(deltaTime);
  }
}
