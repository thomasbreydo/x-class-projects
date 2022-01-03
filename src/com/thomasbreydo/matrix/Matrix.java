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
  public Matrix(double[][] matrix) {
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

  /** @return {@code true} if {@code a} is within {@code 1e-8} of zero. */
  private static boolean basicallyZero(double a) {
    return Math.abs(a) < 1e-8;
  }

  /**
   * Create a new {@code Matrix} with ones along the diagonal and zeros everywhere else.
   *
   * @param rowCount number of rows for the new {@code Matrix}
   * @param colCount number of rows for the new {@code Matrix}
   * @return a new {@code rowCount x colCount} {@code Matrix} with ones along the diagonal and zeros
   *     everywhere else
   */
  public static Matrix identity(int rowCount, int colCount) {
    Matrix output = new Matrix(rowCount, colCount);
    for (int i = 0; i < Math.min(rowCount, colCount); ++i) output.matrix[i][i] = 1;
    return output;
  }

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

  /**
   * Format {@code matrix[row][col]} as {@code %8.2g} for printing.
   *
   * @param row row index of element to format
   * @param col col index of element to format
   * @return {@code matrix[row][col]} as {@code %8.2g}
   */
  public String getStringOfValueAt(int row, int col) {
    double elem = matrix[row][col];
    return "%8.2g".formatted(basicallyZero(elem) ? 0 : elem);
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
  public double[][] getMatrix() {
    return cloneOfInternalArray();
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

  public double getValueAt(int row, int column) {
    return matrix[row][column];
  }

  public void setEntry(int row, int column, double value) {
    matrix[row][column] = value;
  }

  private double[][] cloneOfInternalArray() {
    double[][] output = new double[getRowCount()][getColumnCount()];
    for (int row = 0; row < getRowCount(); ++row)
      output[row] = getRow(row);
    return output;
  }

  /**
   * Add this {@code Matrix} with {@code other}.
   *
   * @param other the {@code Matrix} to add
   * @return the sum as a new {@code Matrix}
   */
  public Matrix plus(Matrix other) {
    checkRowCountMatches(other);
    checkColumnCountMatches(other);

    double[][] output = cloneOfInternalArray();
    for (int row = 0; row < getRowCount(); ++row)
      for (int col = 0; col < getColumnCount(); ++col) output[row][col] += other.matrix[row][col];

    return new Matrix(output);
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
    double[][] output = cloneOfInternalArray();
    for (int col = 0; col < getColumnCount(); ++col) {
      output[row1][col] = matrix[row2][col];
      output[row2][col] = matrix[row1][col];
    }
    return new Matrix(output);
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
    double[][] output = cloneOfInternalArray();
    for (int col = 0; col < getColumnCount(); ++col)
      output[destRow][col] += scalar * output[sourceRow][destRow];
    return new Matrix(output);
  }

  /** @return a {@code Matrix} that equals the reduced row-echelon form of this {@code Matrix} */
  public Matrix rowReduce() {
    Matrix output = new Matrix(cloneOfInternalArray());
    output.rowReduceInPlace();
    return output;
  }

  /**
   * Augment this {@code Matrix} with an identity matrix with the same row count.
   *
   * @return the result as a new {@code Matrix}
   */
  public Matrix augment() {
    Matrix output = new Matrix(cloneOfInternalArray());
    output.augmentInPlace();
    return output;
  }

  /**
   * Augment this {@code Matrix} by {@code other}.
   *
   * @param other {@code Matrix} to put to the right of this {@code Matrix}
   * @return the result as a new {@code Matrix}
   */
  public Matrix augment(Matrix other) {
    Matrix output = new Matrix(cloneOfInternalArray());
    output.augmentInPlace(other);
    return output;
  }

  public Matrix invert() {
    Matrix output = new Matrix(cloneOfInternalArray());
    output.invertInPlace();
    return output;
  }

  private void invertInPlace() {
    augmentInPlace();
    rowReduceInPlace();
  }

  private void augmentInPlace() {
    augmentInPlace(identity(getRowCount(), getRowCount()));
  }

  private void augmentInPlace(Matrix other) {
    checkRowCountMatches(other);
    double[][] newMatrix = new double[getRowCount()][getColumnCount() + other.getColumnCount()];
    for (int row = 0; row < getRowCount(); ++row) {
      System.arraycopy(matrix[row], 0, newMatrix[row], 0, getColumnCount());
      System.arraycopy(other.matrix[row], 0, newMatrix[row], getRowCount(), other.getColumnCount());
    }
    matrix = newMatrix;
  }

  // ############################################################

  //             Efficient, in-place row reduction.
  //                    © Thomas Breydo 2022

  // ############################################################

  private void rowReduceInPlace() {
    int curPivotCol = 0;

    for (int curPivotRow = 0; curPivotRow < getRowCount(); ++curPivotRow) {
      boolean foundNonZeroPivot = false;
      for (int col = curPivotCol; col < getColumnCount(); ++col) {
        for (int row = curPivotRow; row < getRowCount(); ++row) {
          if (basicallyZero(matrix[row][col])) continue;
          switchRowsInPlace(curPivotRow, row);
          curPivotCol = col;
          foundNonZeroPivot = true;
          break;
        }
        if (foundNonZeroPivot) break;
      }
      if (!foundNonZeroPivot) return; // we're done (no more pivots)

      // matrix[curPivotRow][curPivotCol] is the next pivot, let's zero out
      // elements above/below it in the column.
      for (int row = 0; row < getRowCount(); ++row) {
        if (row == curPivotRow) continue; // don't zero out the pivot
        makeElemZeroInPlace(row, curPivotCol, curPivotRow);
      }

      divideRowInPlace(curPivotRow, matrix[curPivotRow][curPivotCol]); // make pivot 1
      ++curPivotCol;
    }
  }

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
    if (basicallyZero(elem)) return;

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
}
