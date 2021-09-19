package com.thomasbreydo.projectile;

import org.opensourcephysics.display.Circle;

public class Particle extends Circle {
  private static final double DRAG_CONSTANT = 0.02;
  private static final double GRAVITY = -9.81;
  private final double mass;
  private final double weight;
  private final double crossSectionalArea;
  private double acceleration;
  private double velocity;

  public Particle(double radius, double mass, double initialX, double initialHeight) {
    this(radius, mass, initialX, initialHeight, 0, GRAVITY);
  }

  public Particle(
      double radius,
      double mass,
      double initialX,
      double initialHeight,
      double initialVelocity,
      double initialAcceleration) {
    super(initialX, initialHeight);

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
    velocity += deltaTime * acceleration;
  }

  void updateHeight(double deltaTime) {
    y += deltaTime * velocity;
  }

  void updateAcceleration(double pressure) {
    double drag = pressure * DRAG_CONSTANT * crossSectionalArea * velocity * velocity / (mass * 2);
    double totalNetForce = weight + drag;
    acceleration = totalNetForce / mass;
  }

  public double getAcceleration() {
    return acceleration;
  }

  public double getVelocity() {
    return velocity;
  }
}
