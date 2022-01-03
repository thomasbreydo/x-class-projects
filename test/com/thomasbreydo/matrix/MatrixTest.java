package com.thomasbreydo.matrix;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class MatrixTest {
  static final double DELTA = 1e-5; // tolerance for float checks
  Matrix matrix;

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
    matrix = new Matrix(new double[][] {{0, 9, -3}, {1, 1, 2}, {3, 0, 7}});
    Matrix same = new Matrix(new double[][] {{0, 9, -3}, {1, 1, 2}, {3, 0, 7}});

    Matrix actual = matrix.rowReduce();
    assertEquals(expected, actual);
    assertEquals(same, matrix);
  }

  @Test
  public void testInvertInvertible() {
    Matrix expected = new Matrix(new double[][] {{1, 0, 0}, {0, 1.0 / 2.0, 0}, {0, 0, 1.0 / 3.0}});
    matrix = new Matrix(new double[][] {{1, 0, 0}, {0, 2, 0}, {0, 0, 3}});
    Matrix same = new Matrix(new double[][] {{1, 0, 0}, {0, 2, 0}, {0, 0, 3}});

    Matrix actual = matrix.invert();
    assertEquals(expected, actual);
    assertEquals(same, matrix);
  }

  @Test
  public void testInvertNonInvertible() {
    matrix = new Matrix(new double[][] {{0, 0, 0}, {0, 2, 0}, {0, 0, 3}});
    assertThrows(RuntimeException.class, matrix::invert);
    matrix = new Matrix(new double[][] {{9, -1.001, 3}, {0, 0, 0}, {5, 0, 3}});
    assertThrows(RuntimeException.class, matrix::invert);
    matrix = new Matrix(new double[][] {{9, 0, 3}, {0, 0, 1.5333}, {2, 0, 1.0 / 7.0}});
    assertThrows(RuntimeException.class, matrix::invert);
  }
}
