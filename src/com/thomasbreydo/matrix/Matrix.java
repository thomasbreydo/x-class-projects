package com.thomasbreydo.matrix;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/** Store a matrix and perform linear-algebraic operations. */
public class Matrix {
  private double[][] matrix;

  /**
   * Initialize this {@code Matrix} with {@code matrix}.
   *
   * @param matrix initial values
   */
  public Matrix(double @NotNull [] @NotNull [] matrix) {
    if (matrix.length == 0 || matrix[0].length == 0)
      throw new IllegalArgumentException("rowCount and colCount must be positive");
    this.matrix = matrix;
  }

  /**
   * Initialize this {@code Matrix} as a {@code rowCount x colCount} matrix filled with zeros.
   *
   * @param rowCount number of rows
   * @param colCount number of columns
   */
  public Matrix(int rowCount, int colCount) {
    this(new double[rowCount][colCount]);
  }

  /**
   * Initialize this {@code Matrix} as a {@code rowCount x colCount} matrix filled with {@code
   * fillValue}.
   *
   * @param rowCount number of rows
   * @param colCount number of columns
   * @param fillValue value to fill with
   */
  public Matrix(int rowCount, int colCount, double fillValue) {
    this(rowCount, colCount);
    for (double[] row : matrix) Arrays.fill(row, fillValue);
  }

  /** @return {@code true} if {@code a} is within {@code 1e-8} of {@code b}. */
  private static boolean basicallyEqual(double a, double b) {
    return Math.abs(a - b) < 1e-8;
  }

  /**
   * Create a new {@code n x n} {@code Matrix} with ones along the diagonal and zeros everywhere
   * else.
   *
   * @param n number of rows and number of columns for the new {@code Matrix}
   * @return a new {@code n x n} {@code Matrix} with ones along the diagonal and zeros everywhere
   *     else
   */
  public static @NotNull Matrix identity(int n) {
    return identity(n, n);
  }

  /**
   * Create a new {@code rowCount x colCount} {@code Matrix} with ones along the diagonal and zeros
   * everywhere else.
   *
   * @param rowCount number of rows for the new {@code Matrix}
   * @param colCount number of rows for the new {@code Matrix}
   * @return a new {@code rowCount x colCount} {@code Matrix} with ones along the diagonal and zeros
   *     everywhere else
   */
  public static @NotNull Matrix identity(int rowCount, int colCount) {
    Matrix output = new Matrix(rowCount, colCount);
    for (int i = 0; i < Math.min(rowCount, colCount); ++i) output.matrix[i][i] = 1;
    return output;
  }

  /**
   * Check if this {@code Matrix} equals {@code o}.
   *
   * @param o the object to compare to
   * @return {@code true} if this {@code Matrix} equals {@code o}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Matrix other = (Matrix) o;
    if (getRowCount() != other.getRowCount()) return false;
    if (getColumnCount() != other.getColumnCount()) return false;
    for (int row = 0; row < getRowCount(); ++row)
      for (int col = 0; col < getColumnCount(); ++col)
        if (!basicallyEqual(matrix[row][col], other.matrix[row][col])) return false;
    return true;
  }

  /**
   * Get the string representation of this {@code Matrix}.
   *
   * @return the string representation of this {@code Matrix}
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(10 * getRowCount() * getColumnCount());
    for (int row = 0; row < getRowCount(); ++row) {
      for (int col = 0; col < getColumnCount(); ++col) {
        stringBuilder.append(getStringOfValueAt(row, col));
        stringBuilder.append(' ');
      }
      stringBuilder.append('\n');
    }
    return stringBuilder.toString();
  }

  public Matrix copy() {
    return new Matrix(getMatrix());
  }

  /**
   * Format {@code matrix[row][col]} as {@code %8.2g} for printing.
   *
   * @param row row index of element to format
   * @param col col index of element to format
   * @return {@code matrix[row][col]} as {@code %8.2g}
   */
  public String getStringOfValueAt(int row, int col) {
    double elem = matrix[row][col];
    return "%8.2g".formatted(basicallyEqual(elem, 0) ? 0 : elem);
  }

  private void checkRowCountMatches(@NotNull Matrix other) {
    if (getRowCount() != other.getRowCount())
      throw new IllegalArgumentException("row count differs");
  }

  private void checkColumnCountMatches(@NotNull Matrix other) {
    if (getColumnCount() != other.getColumnCount())
      throw new IllegalArgumentException("column count differs");
  }

  /**
   * Get a copy of the underlying 2D array.
   *
   * @return a copy of the underlying 2D array
   */
  public double @NotNull [] @NotNull [] getMatrix() {
    double[][] output = new double[getRowCount()][getColumnCount()];
    for (int row = 0; row < getRowCount(); ++row) output[row] = getRow(row);
    return output;
  }

  /**
   * Get a copy of the row at index {@code row}.
   *
   * @param row index of row to access
   * @return a copy of the row at index {@code row}
   */
  public double[] getRow(int row) {
    return matrix[row].clone();
  }

  /**
   * Get the number of rows in this {@code Matrix}.
   *
   * @return the number of rows in this {@code Matrix}
   */
  public int getRowCount() {
    return matrix.length;
  }

  /**
   * Get the number of columns in this {@code Matrix}.
   *
   * @return the number of columns in this {@code Matrix}
   */
  public int getColumnCount() {
    return matrix[0].length;
  }

  /**
   * Get the element at row {@code row} and column {@code column}.
   *
   * @param row row index of element
   * @param column column index of element
   * @return the element at row {@code row} and column {@code column}
   */
  public double getValueAt(int row, int column) {
    return matrix[row][column];
  }

  /**
   * Set the element at row {@code row} and column {@code column} to {@code value}.
   *
   * @param row row index of element
   * @param column column index of element
   * @param value the new value for the element
   */
  public void setEntry(int row, int column, double value) {
    matrix[row][column] = value;
  }

  /**
   * Add this {@code Matrix} with {@code other}.
   *
   * @param other the {@code Matrix} to add
   * @return the sum as a new {@code Matrix}
   */
  public Matrix plus(@NotNull Matrix other) {
    Matrix output = copy();
    output.plusInPlace(other);
    return output;
  }

  /**
   * Multiply {@code row} by {@code scalar}.
   *
   * @param scalar scalar by which to multiply
   * @param row index of row whose elements to modify
   * @return the product of row {@code row} of this {@code Matrix} by {@code scalar}.
   */
  public double[] scalarTimesRow(double scalar, int row) {
    double[] output = getRow(row);
    for (int col = 0; col < getColumnCount(); ++col) output[col] *= scalar;
    return output;
  }

  /**
   * Switch rows {@code row1} and {@code row2} of this {@code Matrix}.
   *
   * @param row1 row index
   * @param row2 row index
   * @return the resulting {@code Matrix}.
   */
  public Matrix switchRows(int row1, int row2) {
    Matrix output = copy();
    output.switchRowsInPlace(row1, row2);
    return output;
  }

  /**
   * Linear combine two rows.
   *
   * @param scalar scalar by which to multiply elements of {@code sourceRow} before adding
   * @param sourceRow index of row from which to take elements
   * @param destRow index of row to modify
   * @return a {@code Matrix} where {@code destRow := destRow + (scalar * sourceRow)}
   */
  public Matrix linearCombRows(double scalar, int sourceRow, int destRow) {
    Matrix output = copy();
    for (int col = 0; col < getColumnCount(); ++col)
      output.matrix[destRow][col] += scalar * matrix[sourceRow][col];
    return output;
  }

  /**
   * Get a {@code Matrix} that equals the reduced row-echelon form of this {@code Matrix}.
   *
   * @return a {@code Matrix} that equals the reduced row-echelon form of this {@code Matrix}
   */
  public Matrix rowReduce() {
    Matrix output = copy();
    output.rowReduceInPlace();
    return output;
  }

  /**
   * Augment this {@code Matrix} with an identity matrix with the same row count.
   *
   * @return the result as a new {@code Matrix}
   */
  public Matrix augment() {
    Matrix output = copy();
    output.augmentInPlace();
    return output;
  }

  /**
   * Augment this {@code Matrix} by {@code other}.
   *
   * @param other {@code Matrix} to put to the right of this {@code Matrix}
   * @return the result as a new {@code Matrix}
   */
  public Matrix augment(@NotNull Matrix other) {
    Matrix output = copy();
    output.augmentInPlace(other);
    return output;
  }

  /**
   * Get the inverted version of this {@code Matrix}.
   *
   * @return the inverted version of this {@code Matrix}
   */
  public Matrix invert() {
    Matrix output = copy();
    output.invertInPlace();
    return output;
  }

  private void invertInPlace() {
    augmentInPlace();
    rowReduceInPlace();
    Matrix leftHalf = slice(0, getRowCount(), 0, getRowCount());
    if (!leftHalf.equals(identity(getRowCount())))
      throw new RuntimeException("matrix is not invertible");
    sliceInPlace(0, getRowCount(), getColumnCount() / 2, getColumnCount());
  }

  private void augmentInPlace() {
    augmentInPlace(identity(getRowCount()));
  }

  private void augmentInPlace(@NotNull Matrix other) {
    checkRowCountMatches(other);
    double[][] newMatrix = new double[getRowCount()][getColumnCount() + other.getColumnCount()];
    for (int row = 0; row < getRowCount(); ++row) {
      System.arraycopy(matrix[row], 0, newMatrix[row], 0, getColumnCount());
      System.arraycopy(other.matrix[row], 0, newMatrix[row], getRowCount(), other.getColumnCount());
    }
    matrix = newMatrix;
  }

  /**
   * Return a sliced version of this {@code Matrix}.
   *
   * @param rowStart index of first row to include
   * @param rowEnd index of first row to exclude
   * @param colStart index of first column to include
   * @param colEnd index of first row to exclude
   * @return a sliced version of this {@code Matrix}
   */
  public Matrix slice(int rowStart, int rowEnd, int colStart, int colEnd) {
    Matrix output = copy();
    output.sliceInPlace(rowStart, rowEnd, colStart, colEnd);
    return output;
  }

  private void sliceInPlace(int rowStart, int rowEnd, int colStart, int colEnd) {
    double[][] newMatrix = new double[rowEnd - rowStart][colEnd - colStart];
    for (int curRow = rowStart, newRow = 0; curRow < rowEnd; ++curRow, ++newRow)
      for (int curCol = colStart, newCol = 0; curCol < colEnd; ++curCol, ++newCol)
        newMatrix[newRow][newCol] = matrix[curRow][curCol];
    matrix = newMatrix;
  }

  // ######################################################################################

  //                          Efficient, in-place row reduction
  //                                 © Thomas Breydo 2022

  // ######################################################################################

  private void rowReduceInPlace() {
    int curPivotCol = 0;

    for (int curPivotRow = 0; curPivotRow < getRowCount(); ++curPivotRow) {
      boolean foundNonZeroPivot = false;
      for (int col = curPivotCol; col < getColumnCount(); ++col) {
        for (int row = curPivotRow; row < getRowCount(); ++row) {
          if (basicallyEqual(matrix[row][col], 0)) continue;
          switchRowsInPlace(curPivotRow, row);
          curPivotCol = col;
          foundNonZeroPivot = true;
          break;
        }
        if (foundNonZeroPivot) break;
      }
      if (!foundNonZeroPivot) return; // we're done (no more pivots)

      // matrix[curPivotRow][curPivotCol] is the next pivot, let's zero out the
      // elements above/below it in its column.
      for (int row = 0; row < getRowCount(); ++row) {
        if (row == curPivotRow) continue; // don't zero out the pivot
        makeElemZeroInPlace(row, curPivotCol, curPivotRow);
      }

      divideRowInPlace(curPivotRow, matrix[curPivotRow][curPivotCol]); // make pivot 1
      ++curPivotCol;
    }
  }

  // ######################################################################################

  private void switchRowsInPlace(int row1, int row2) {
    for (int col = 0; col < getColumnCount(); ++col) {
      double temp = matrix[row1][col];
      matrix[row1][col] = matrix[row2][col];
      matrix[row2][col] = temp;
    }
  }

  /**
   * Ensure {@code matrix[row][col]} is zero by combining {@code row} with {@code auxiliaryRow} if
   * not yet zero.
   */
  private void makeElemZeroInPlace(int row, int col, int auxiliaryRow) {
    double elem = matrix[row][col];
    if (basicallyEqual(elem, 0)) return;

    // make matrix[auxiliaryRow][col] == -elem, then add.
    multiplyRowInPlace(auxiliaryRow, -elem / matrix[auxiliaryRow][col]);
    addRowToRowInPlace(auxiliaryRow, row);
  }

  /** Multiply {@code row} by {@code scalar}. */
  private void multiplyRowInPlace(int row, double scalar) {
    for (int col = 0; col < getColumnCount(); col++) matrix[row][col] *= scalar;
  }

  /** Divide {@code row} by {@code scalar}. */
  private void divideRowInPlace(int row, double scalar) {
    for (int col = 0; col < getColumnCount(); col++) matrix[row][col] /= scalar;
  }

  /** Add {@code row1} to {@code row2} (updating {@code row2}). */
  private void addRowToRowInPlace(int row1, int row2) {
    for (int col = 0; col < getColumnCount(); col++) matrix[row2][col] += matrix[row1][col];
  }

  private void plusInPlace(@NotNull Matrix other) {
    checkRowCountMatches(other);
    checkColumnCountMatches(other);

    for (int row = 0; row < getRowCount(); ++row)
      for (int col = 0; col < getColumnCount(); ++col) matrix[row][col] += other.matrix[row][col];
  }
}
