package com.thomasbreydo.projectile;

import org.opensourcephysics.display.Circle;

public class Particle2D extends Circle {
  private static final double DRAG_CONSTANT = 0.02;
  private static final double GRAVITY = -9.81;
  private final double mass;
  private final double weight;
  private final double crossSectionalArea;
  private double accelerationX;
  private double accelerationY;
  private double velocityX;
  private double velocityY;

  public Particle2D(double radius, double mass, double initialX, double initialY) {
    this(radius, mass, initialX, initialY, new double[] {0, 0});
  }

  public Particle2D(
      double radius, double mass, double initialX, double initialY, double[] initialVelocity) {
    this(radius, mass, initialX, initialY, initialVelocity, new double[] {0, GRAVITY});
  }

  public static double[] computeVelocity(double angle, double speed) {
    return new double[] {speed * Math.cos(angle), speed * Math.sin(angle)};
  }

  public Particle2D(
      double radius, double mass, double initialX, double initialY, double angle, double speed) {
    this(radius, mass, initialX, initialY, computeVelocity(angle, speed));
  }

  public Particle2D(
      double radius,
      double mass,
      double initialX,
      double initialHeight,
      double[] initialVelocity,
      double[] initialAcceleration) {
    super(initialX, initialHeight);

    this.mass = mass;
    velocityX = initialVelocity[0];
    velocityY = initialVelocity[1];
    accelerationX = initialAcceleration[0];
    accelerationY = initialAcceleration[1];

    weight = mass * GRAVITY;
    crossSectionalArea = Math.PI * radius * radius;
  }

  void step(double deltaTime) {
    if (y < 0) return;
    updateHeight(deltaTime);
    updateVelocity(deltaTime);
  }

  void step(double deltaTime, double pressure) {
    step(deltaTime);
    updateAcceleration(pressure);
  }

  void updateVelocity(double deltaTime) {
    velocityX += deltaTime * accelerationX;
    velocityY += deltaTime * accelerationY;
  }

  void updateHeight(double deltaTime) {
    x += deltaTime * velocityX;
    y += deltaTime * velocityY;
  }

  void updateAcceleration(double pressure) {
    double dragX, dragY, totalNetForceY, totalNetForceX;
    dragX = pressure * DRAG_CONSTANT * crossSectionalArea * velocityX * velocityX / (mass * 2);
    totalNetForceX = -dragX;
    accelerationX = totalNetForceX / mass;
    dragY = pressure * DRAG_CONSTANT * crossSectionalArea * velocityY * velocityY / (mass * 2);
    totalNetForceY = weight + dragY;
    accelerationY = totalNetForceY / mass;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public void bounceX() {
    velocityX *= -1;
  }
}
