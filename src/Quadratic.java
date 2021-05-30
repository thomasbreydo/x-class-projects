/** Second-degree polynomial in the form ax^2 + bx + c. */
public class Quadratic {
  private final double a, b, c, discriminant, vertexX, vertexY;

  /**
   * @param a quadratic coefficient
   * @param b linear coefficient
   * @param c constant coefficient
   */
  Quadratic(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
    discriminant = b * b - 4 * a * c;
    vertexX = -b / (2 * a);
    vertexY = evaluateWith(vertexX);
  }

  public double getA() {
    return a;
  }

  public double getB() {
    return b;
  }

  public double getC() {
    return c;
  }

  /**
   * Checks if this quadratic has real roots.
   *
   * @return {@code true} if this {@code Quadratic} has real roots
   */
  public boolean hasRealRoots() {
    return discriminant >= 0;
  }

  /**
   * Returns the number of roots that this quadratic has.
   *
   * @return the number of roots that this quadratic has
   */
  public int numberOfRoots() {
    if (discriminant > 0) return 2;
    if (discriminant == 0) return 1;
    return 0;
  }
  /**
   * Returns the roots of this quadratic as an array of length 0, 1, or 2.
   *
   * @return the roots of this quadratic
   */
  public double[] getRootArray() {
    if (discriminant < 0) return new double[] {};
    if (discriminant == 0) return new double[] {vertexX};
    double d = Math.sqrt(discriminant) / (2 * a);
    return new double[] {vertexX - d, vertexX + d};
  }

  /**
   * Returns the x-coordinate of this quadratic's vertex
   *
   * @return the x-coordinate of this quadratic's vertex
   */
  public double getAxisOfSymmetry() {
    return vertexX;
  }

  /**
   * Returns the y-coordinate of this quadratic's vertex
   *
   * @return the y-coordinate of this quadratic's vertex
   */
  public double getExtremeValue() {
    return vertexY;
  }

  /**
   * Checks whether the extreme value of this quadratic is an absolute maximum. If not, it's an
   * absolute minimum.
   *
   * @return {@code true} if the extreme value of this quadratic is an absolute maximum.
   */
  public boolean isMax() {
    return a < 0;
  }

  /**
   * Evaluates this quadratic at {@code x}.
   *
   * @param x the input at which to evaluate
   * @return ax^2 + bx + c
   */
  public double evaluateWith(double x) {
    return x * (a * x + b) + c;
  }
}
