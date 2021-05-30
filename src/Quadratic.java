/** Second-degree polynomial in the form ax^2 + bx + c. */
public class Quadratic {
  private final double a, b, c, discriminant;

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
   * Returns the number of roots this quadratic has.
   *
   * @return the number of roots this quadratic has
   */
  public int numberOfRoots() {
    if (discriminant > 0) return 2;
    if (discriminant == 0) return 1;
    return 0;
  }
}
