package com.thomasbreydo.springs;

public class Vector2D {
  private double x;
  private double y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static double distanceBetween(Vector2D a, Vector2D b) {
    return a.minus(b).norm();
  }

  // Vector2D.sum(new Vector[]{a, b, c, d, e})
  public static Vector2D sum(Vector2D... vectors) {
    Vector2D total = new Vector2D(0, 0);
    for (Vector2D v : vectors) {
      total.add(v);
    }
    return total;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setXY(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void add(Vector2D other) {
    x += other.x;
    y += other.y;
  }

  public Vector2D plus(Vector2D other) {
    return new Vector2D(x + other.x, y + other.y);
  }

  public Vector2D minus(Vector2D other) {
    return new Vector2D(x - other.x, y - other.y);
  }

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  public void scaleBy(double scalar) {
    x *= scalar;
    y *= scalar;
  }

  public Vector2D scaledBy(double scalar) {
    return new Vector2D(x * scalar, y * scalar);
  }

  public void scaleToNorm(double targetNorm) {
    this.scaleBy(targetNorm / norm());
  }

  public Vector2D scaledToNorm(double targetNorm) {
    return this.scaledBy(targetNorm / norm());
  }

  public double arg() {
    return Math.atan2(y, x);
  }
}
