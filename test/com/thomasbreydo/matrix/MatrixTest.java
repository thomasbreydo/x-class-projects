package com.thomasbreydo.matrix;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class MatrixTest {
  static final double DELTA = 1e-8; // tolerance for float checks

  public static void assertEquals(Matrix expected, Matrix actual) {
    Assert.assertEquals("row count differs", expected.getRowCount(), actual.getRowCount());
    Assert.assertEquals("column count differs", expected.getColumnCount(), actual.getColumnCount());
    for (int row = 0; row < expected.getRowCount(); row++) {
      assertArrayEquals("at row " + row, expected.getRow(row), actual.getRow(row), DELTA);
    }
  }

  @Test
  public void testRowReduce() {
    Matrix expected = new Matrix(new double[][] {{1, 0, 7.0 / 3.0}, {0, 1, -1.0 / 3.0}, {0, 0, 0}});
    Matrix m1 = new Matrix(new double[][] {{0, 9, -3}, {1, 1, 2}, {3, 0, 7}});
    Matrix same = new Matrix(new double[][] {{0, 9, -3}, {1, 1, 2}, {3, 0, 7}});

    Matrix actual = m1.rowReduce();
    assertEquals(expected, actual);
    assertEquals(same, m1);
  }

  @Test
  public void testInvertInvertible() {
    Matrix expected = new Matrix(new double[][] {{1, 0, 0}, {0, 1.0 / 2.0, 0}, {0, 0, 1.0 / 3.0}});
    Matrix m1 = new Matrix(new double[][] {{1, 0, 0}, {0, 2, 0}, {0, 0, 3}});
    Matrix m1copy = m1.copy();

    Matrix actual = m1.invert();
    assertEquals(expected, actual);
    assertEquals(m1copy, m1);
  }

  @Test
  public void testInvertNonInvertible() {
    Matrix m1 = new Matrix(new double[][] {{0, 0, 0}, {0, 2, 0}, {0, 0, 3}});
    assertThrows(RuntimeException.class, m1::invert);
    m1 = new Matrix(new double[][] {{9, -1.001, 3}, {0, 0, 0}, {5, 0, 3}});
    assertThrows(RuntimeException.class, m1::invert);
    m1 = new Matrix(new double[][] {{9, 0, 3}, {0, 0, 1.5333}, {2, 0, 1.0 / 7.0}});
    assertThrows(RuntimeException.class, m1::invert);
  }

  @Test
  public void testPlus() {
    Matrix expected =
        new Matrix(
            new double[][] {
              {1, -2},
              {0, 0.53},
            });
    Matrix m1 =
        new Matrix(
            new double[][] {
              {0, -1},
              {4, 0.3},
            });
    Matrix m2 =
        new Matrix(
            new double[][] {
              {1, -1},
              {-4, 0.23},
            });
    assertEquals(expected, m1.plus(m2));
    assertEquals(expected, m2.plus(m1));
  }

  @Test
  public void testSwitchRows() {
    Matrix expected =
        new Matrix(
            new double[][] {
              {4, 0.3},
              {0, -1},
            });
    Matrix m1 =
        new Matrix(
            new double[][] {
              {0, -1},
              {4, 0.3},
            });
    assertEquals(expected, m1.switchRows(0, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.switchRows(1, 2));
  }

  @Test
  public void testScalarTimesRow() {
    Matrix m1 =
        new Matrix(
            new double[][] {
              {0, -1},
              {4, 0.3},
            });
    Matrix m1copy = m1.copy();
    Assert.assertArrayEquals(new double[] {0, 0}, m1.scalarTimesRow(0, 0), DELTA);
    Assert.assertArrayEquals(new double[] {0, 1}, m1.scalarTimesRow(-1, 0), DELTA);
    Assert.assertArrayEquals(new double[] {1.2, 0.09}, m1.scalarTimesRow(0.3, 1), DELTA);
    assertThrows(IndexOutOfBoundsException.class, () -> m1.scalarTimesRow(0, 2));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.scalarTimesRow(1, -1));
    assertEquals(m1copy, m1);
  }

  @Test
  public void testLinearCombRows() {
    Matrix expected1 =
        new Matrix(
            new double[][] {
              {0, -1},
              {4, 1.8},
            });
    Matrix expected2 =
        new Matrix(
            new double[][] {
              {40, 2},
              {4, 0.3},
            });
    Matrix m1 =
        new Matrix(
            new double[][] {
              {0, -1},
              {4, 0.3},
            });
    Matrix m1copy = m1.copy();

    assertEquals(expected1, m1.linearCombRows(-1.5, 0, 1));
    assertEquals(expected2, m1.linearCombRows(10, 1, 0));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.linearCombRows(0.5, 1, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.linearCombRows(0.5, 1, 2));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.linearCombRows(0.5, -1, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.linearCombRows(0.5, 2, 2));
    assertEquals(m1copy, m1);
  }

  @Test
  public void testSetEntry() {
    Matrix m1 = new Matrix(1, 1);
    assertThrows(IndexOutOfBoundsException.class, () -> m1.setEntry(0, -1, 0.5));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.setEntry(1, -1, 0.5));
    assertThrows(IndexOutOfBoundsException.class, () -> m1.setEntry(-1, 2, 0.5));
    m1.setEntry(0, 0, -3);
    Assert.assertEquals(-3, m1.getValueAt(0, 0), DELTA);

    Matrix expected =
        new Matrix(
            new double[][] {
              {3.14, 15},
              {92.6, 5},
            });

    Matrix m2 = new Matrix(expected.getRowCount(), expected.getColumnCount());
    m2.setEntry(0, 0, 3.14);
    m2.setEntry(0, 1, 15);
    m2.setEntry(1, 0, 92.6);
    m2.setEntry(1, 1, 5);

    assertEquals(expected, m2);
  }
}
