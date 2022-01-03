package com.thomasbreydo.matrix;

import java.util.Arrays;

public class Matrix {
  private final double[][] matrix;

  /**
   * Initialize this {@code Matrix} with {@code matrix}.
   *
   * @param matrix initial values
   * @throws IllegalArgumentException if {@code matrix} is degenerate (0 rows or 0 columns)
   */
  public Matrix(double[][] matrix) throws IllegalArgumentException {
    if (matrix.length == 0 || matrix[0].length == 0)
      throw new IllegalArgumentException("rowCount and colCount must be positive");
    this.matrix = matrix;
  }

  /**
   * Initialize this {@code Matrix} as a {@code rowCount x colCount} matrix filled with zeros.
   *
   * @param rowCount number of rows
   * @param colCount number of columns
   * @throws IllegalArgumentException if {@code rowCount} or {@code colCount} are zero.
   */
  public Matrix(int rowCount, int colCount) throws IllegalArgumentException {
    this(new double[rowCount][colCount]);
  }

  /**
   * Initialize this {@code Matrix} as a {@code rowCount x colCount} matrix filled with {@code
   * fillValue}.
   *
   * @param rowCount number of rows
   * @param colCount number of columns
   * @param fillValue value to fill with
   * @throws IllegalArgumentException if {@code rowCount} or {@code colCount} are zero.
   */
  public Matrix(int rowCount, int colCount, double fillValue) throws IllegalArgumentException {
    this(rowCount, colCount);
    for (double[] row : matrix) Arrays.fill(row, fillValue);
  }

  /** @return {@code true} if {@code a} is within {@code 1e-8} of zero. */
  private static boolean basicallyZero(double a) {
    return Math.abs(a) < 1e-8;
  }

  public double[][] getMatrix() {
    return matrix;
  }

  public double[] getRow(int row) {
    return matrix[row];
  }

  public int getRowCount() {
    return matrix.length;
  }

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
    for (int row = 0; row < getRowCount(); ++row) {
      System.arraycopy(matrix[row], 0, output[row], 0, getColumnCount());
    }
    return output;
  }

  /**
   * Add this {@code Matrix} with {@code other}.
   *
   * @return the sum as a new {@code Matrix}
   * @throws IllegalArgumentException if the dimensions of {@code other} don't match this {@code
   *     Matrix}
   */
  public Matrix plus(Matrix other) throws IllegalArgumentException {
    if (other.getRowCount() != getRowCount() || other.getColumnCount() != getColumnCount())
      throw new IllegalArgumentException("dimension mismatch");

    double[][] output = cloneOfInternalArray();
    for (int row = 0; row < getRowCount(); ++row)
      for (int col = 0; col < getColumnCount(); ++col)
        output[row][col] += other.getValueAt(row, col);

    return new Matrix(output);
  }

  /** @return the product of row {@code row} of this {@code Matrix} by {@code scalar}. */
  public double[] scalarTimesRow(double scalar, int row) {
    double[] output = getRow(row).clone();
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
      output[row1][col] = getValueAt(row2, col);
      output[row2][col] = getValueAt(row1, col);
    }
    return new Matrix(output);
  }

  /**
   * Linear combine two rows.
   *
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
    System.out.println();
    return stringBuilder.toString();
  }

  /** Format {@code getValueAt(row, col)} as {@code %8.2g} for printing. */
  public String getStringOfValueAt(int row, int col) {
    double elem = getValueAt(row, col);
    return "%8.2g".formatted(basicallyZero(elem) ? 0 : elem);
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
          if (basicallyZero(getValueAt(row, col))) continue;
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

      divideRowInPlace(curPivotRow, getValueAt(curPivotRow, curPivotCol)); // make pivot 1
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
   * Ensure {@code getValueAt(row, col)} is zero by combining {@code row} with {@code auxiliaryRow}
   * if not yet zero.
   */
  private void makeElemZeroInPlace(int row, int col, int auxiliaryRow) {
    double elem = getValueAt(row, col);
    if (basicallyZero(elem)) return;

    // make matrix[auxiliaryRow][col] == -elem, then add.
    multiplyRowInPlace(auxiliaryRow, -elem / getValueAt(auxiliaryRow, col));
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
