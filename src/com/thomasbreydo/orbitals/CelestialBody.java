package com.thomasbreydo.orbitals;

import com.thomasbreydo.physics.FreeBody;
import com.thomasbreydo.physics.Vector2D;
import org.opensourcephysics.display.*;

import java.awt.*;
import java.util.ArrayList;

public class CelestialBody extends FreeBody implements Drawable {
  private static final double G = 6.673889E-11;
  Circle circle;
  boolean showTrail;
  boolean showForces;
  Trail trail;
  CelestialSystem system;
  ArrayList<Vector2D> forces;

  CelestialBody(Vector2D position, Vector2D velocity, double mass, CelestialSystem system) {
    this(position, velocity, mass, system, true, true);
  }

  CelestialBody(
      Vector2D position,
      Vector2D velocity,
      double mass,
      CelestialSystem system,
      boolean showTrail,
      boolean showForces) {
    super(position, velocity, mass);
    this.system = system;
    this.showTrail = showTrail;
    this.showForces = showForces;

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
    if (showForces) {
      for (Vector2D force : forces) {
        Vector2D scaled = force.scaledBy(1e-12);
        (new Arrow(getX(), getY(), scaled.getX(), scaled.getY())).draw(drawingPanel, graphics);
      }
    }
  }

  @Override
  public void step(double deltaTime) {
    super.step(deltaTime);
  }
}
