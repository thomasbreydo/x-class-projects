package com.thomasbreydo.projectile;

import org.opensourcephysics.display.Circle;

public class Particle2D extends Circle {
  private static final double DRAG_CONSTANT = 0.02;
  private static final double GRAVITY = -9.81;
  private final double mass;
  private final double weight;
  private final double crossSectionalArea;
  private final double[] acceleration;
  private final double[] velocity;

  public Particle2D(double radius, double mass, double initialX, double initialY) {
    this(radius, mass, initialX, initialY, new double[] {0, 0}, new double[] {0, GRAVITY});
  }

  public Particle2D(
      double radius, double mass, double initialX, double initialY, double[] initialVelocity) {
    this(radius, mass, initialX, initialY, initialVelocity, new double[] {0, GRAVITY});
  }

  public Particle2D(
      double radius,
      double mass,
      double initialX,
      double initialHeight,
      double[] initialVelocity,
      double[] initialAcceleration) {
    super(initialX, initialHeight);

    if (initialVelocity.length != initialAcceleration.length)
      throw new IllegalArgumentException(
          "velocity and acceleration vectors have different number of dimensions");

    this.mass = mass;
    velocity = initialVelocity;
    acceleration = initialAcceleration;

    weight = mass * GRAVITY;
    crossSectionalArea = Math.PI * radius * radius;
  }

  void step(double deltaTime) {
    if (y < 0) return;
    updateHeight(deltaTime);
    updateVelocity(deltaTime);
  }

  void step(double deltaTime, double pressure) {
    if (y < 0) return;
    updateHeight(deltaTime);
    updateVelocity(deltaTime);
    updateAcceleration(pressure);
  }

  void updateVelocity(double deltaTime) {
    for (int i = 0; i < velocity.length; ++i) velocity[i] += deltaTime * acceleration[i];
  }

  void updateHeight(double deltaTime) {
    x += deltaTime * velocity[0];
    y += deltaTime * velocity[1];
  }

  void updateAcceleration(double pressure) {
    double drag, totalNetForce;
    for (int i = 0; i < velocity.length; ++i) {
      drag = pressure * DRAG_CONSTANT * crossSectionalArea * velocity[i] * velocity[i] / (mass * 2);
      totalNetForce = weight + drag;
      acceleration[i] = totalNetForce / mass;
    }
  }

  public double[] getAcceleration() {
    return acceleration;
  }

  public double[] getVelocity() {
    return velocity;
  }
}
