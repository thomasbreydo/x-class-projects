package com.thomasbreydo.orbitals;

import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawingPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CelestialSystem implements Drawable {
  List<CelestialBody> bodies;

  CelestialSystem() {
    this.bodies = new ArrayList<>();
  }

  CelestialSystem(List<CelestialBody> bodies) {
    this.bodies = bodies;
  }

  public void addBody(CelestialBody body) {
    bodies.add(body);
  }

  public List<CelestialBody> getBodies() {
    return bodies;
  }

  public void step(double deltaTime) {
    // clear forces
    for (CelestialBody body : bodies) {
      body.clearForces();
    }

    // add forces
    for (int i = 0; i < bodies.size(); i++) {
      for (int j = i + 1; j < bodies.size(); j++) {
        CelestialBody a = bodies.get(i);
        CelestialBody b = bodies.get(j);
        a.addForce(a.forceExertedBy(b));
        b.addForce(b.forceExertedBy(a));
      }
    }

    // step bodies
    for (CelestialBody body : bodies) {
      body.step(deltaTime);
    }
  }

  @Override
  public void draw(DrawingPanel drawingPanel, Graphics graphics) {
    for (CelestialBody body : bodies) {
      body.draw(drawingPanel, graphics);
    }
  }
}
