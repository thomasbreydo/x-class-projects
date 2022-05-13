package com.thomasbreydo.physics;

public class FreeBody {
  protected final double mass;
  protected final Vector2D velocity;
  protected Vector2D position;
  protected Vector2D acceleration;
  protected Vector2D netForce;

  public FreeBody(Vector2D position, Vector2D velocity, double mass) {
    this.position = position;
    this.velocity = velocity;
    this.mass = mass;
  }

  public FreeBody(Vector2D position, double mass) {
    this(position, new Vector2D(0, 0), mass);
  }

  public static double distanceBetween(FreeBody a, FreeBody b) {
    return Vector2D.distanceBetween(a.position, b.position);
  }

  public double getMass() {
    return mass;
  }

  public double getX() {
    return position.getX();
  }

  public double getY() {
    return position.getY();
  }

  public Vector2D getPosition() {
    return position;
  }

  public void setPosition(Vector2D position) {
    this.position = position;
  }

  public void setNetForce(Vector2D netForce) {
    this.netForce = netForce;
  }

  private void updateAcceleration() {
    acceleration = netForce.scaledBy(1 / mass);
  }

  private void updateVelocity(double deltaTime) {
    velocity.add(acceleration.scaledBy(deltaTime));
  }

  private void updatePosition(double deltaTime) {
    position.add(velocity.scaledBy(deltaTime));
  }

  public void step(double deltaTime) {
    updateAcceleration();
    updateVelocity(deltaTime);
    updatePosition(deltaTime);
  }
}
