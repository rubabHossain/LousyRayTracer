package com.raytracer.lib.linalg;

import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {
    private final static double EPSILON = 1E-2; // threshold for float equality. f1==f1 <=> (f1-f2) < EPSILON

    private final List<List<Double>> rows;
    private int numRows, numCols;

    /* ------------------------ OOP Stuff ------------------------- */
    // public constructor
    public Matrix(List<List<Double>> listOfRows) {
        this.rows = new ArrayList<>();
        for(List<Double> row : listOfRows) {
            List<Double> rowCopy = new ArrayList<>(row);
            this.rows.add(rowCopy);
        }
        this.numRows = rows.size();
        this.numCols = numRows > 0 ? rows.get(0).size() : 0;
    }


    // used for internally constructing data structures
    private Matrix() {
        this.rows = new ArrayList<>();
        this.numRows = 0;
        this.numCols = 0;
    }


    // used for internally constructing data structures
    private void addRow(List<Double> row_) {
        // if this is first row being added, set numCols to be length of incoming row
        List<Double> row = new ArrayList<>(row_);
        if(this.rows.size() == 0) {
            this.numCols = row.size();
        }

        // incoming row size must agree with matrix col size
        if(row.size() != numCols) {
            String err = String.format("Attempted to add row of length %d to a matrix with %d cols.", row.size(), numCols);
            throw new RuntimeException(err);
        }

        // otherwise, all is well, add column to column list.
        this.rows.add(row);
        this.numRows++;
    }


    // getters ...
    public List<List<Double>> getBackingArray() {
        return this.rows.stream().map(ArrayList::new). collect(Collectors.toList()); // return copy of backing list.
    }


    // getters ...
    public int getNumRows() {
        return this.numRows;
    }


    // getters ...
    public int getNumColumns() {
        return this.numCols;
    }


    // getters ...
    public List<Double> getColumn(int i) {
        return this.rows.stream()
                .map( row -> row.get(i) )
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(List<Double> row: this.rows) {
            for(double d: row) {
                sb.append(d).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)                        // self check
            return true;

        if (o == null)                        // null check
            return false;


        if (o.getClass() != this.getClass())  // type check and cast
            return false;

        Matrix otherMatrix = (Matrix) o;      // compare fields ....
        // check size
        if(otherMatrix.getNumRows() != this.numRows || otherMatrix.getNumColumns() != this.numCols)
            return false;

        List<List<Double>> otherElements = otherMatrix.getBackingArray();

        for(int i = 0; i < this.numRows; i++) {
            List<Double> otherRow = otherElements.get(i);
            List<Double> myRow = rows.get(i);
            for(int j = 0; j < this.numCols; j++) {
                double myElem = myRow.get(j), otherElem = otherRow.get(j);
                double diff = Math.abs(myElem - otherElem);
                if(diff > EPSILON)
                    return false;
            }
        }
        return true;
    }


    @Override
    public int hashCode() {
        int ctr = 1;
        int sum = 0;
        for(List<Double> row: this.rows) {
            for(double d: row) {
                sum += ctr * Math.round(d);
                ctr += 2;
            }
        }
        return sum;
    }

    /* ------------------------ Operations ------------------------- */

    public Matrix mult(Matrix other) {
        Matrix newMtx = new Matrix();

        for(int i = 0; i < this.numRows; i++) {
            List<Double> newRow = new ArrayList<>();
            List<Double> curRow = this.rows.get(i);

            for(int j = 0; j < other.getNumColumns(); j++) {
                List<Double> curCol = other.getColumn(j);
                double dotProduct = (new ColumnVector(curRow)).dotProduct(new ColumnVector(curCol));
                newRow.add(dotProduct);
            }

            newMtx.addRow(newRow);
        }

        return newMtx;
    }


    public ColumnVector mult(ColumnVector v) {
        ColumnVector resultingVector = new ColumnVector(v.getElements());
        resultingVector = resultingVector.subtract(resultingVector);

        for(int i = 0; i < v.getElements().size(); i++) {
            ColumnVector mtxCol = new ColumnVector(this.getColumn(i));
            Double d = v.getElements().get(i);
            resultingVector = resultingVector.add(mtxCol.mult(d));
        }
        return resultingVector;
    }

    public Vector mult(Vector v) {
        ColumnVector cv = this.mult((ColumnVector) v);
        return new Vector(cv.getElements().get(0),
                          cv.getElements().get(1),
                          cv.getElements().get(2));
    }

    public Point mult(Point p) {
        ColumnVector cv = this.mult((ColumnVector) p);
        return new Point(cv.getElements().get(0),
                         cv.getElements().get(1),
                         cv.getElements().get(2));
    }


    public Matrix transpose() {
        Matrix mtx = new Matrix();
        for(int i = 0; i < numCols; i++) {
            List<Double> col = this.getColumn(i);
            mtx.addRow(col);
        }
        return mtx;
    }


    public double determinant() {
        if(this.numCols != this.numRows || this.numRows == 0)
            throw new RuntimeException("Cannot take determinant of non-square matrix.");

        if(this.numRows == 1)
            return this.rows.get(0).get(0); // det([[i]]) = i

        List<Double> firstRow = this.rows.get(0);
        double det = 0;
        for(int i = 0; i < firstRow.size(); i++) {
            det += (firstRow.get(i) * this.cofactor(0, i));
        }

        return det;
    }

    public Matrix subMatrix(int row, int col) {
        if(row >= this.numRows || col >= this.numCols)
            throw new RuntimeException("Submatrix indices out of bounds");

        List<List<Double>> backingArrayCopy = this.getBackingArray();
        backingArrayCopy.remove(row);
        backingArrayCopy.forEach(r -> r.remove(col));

        return new Matrix(backingArrayCopy);
    }


    public double minor(int row, int col) {
        return this.subMatrix(row, col).determinant();
    }

    public double cofactor(int row, int col) {
        return (row + col) % 2 == 0 ?
                this.minor(row, col) :
                this.minor(row, col) * -1;
    }


    public Matrix inverse() {
        if(this.numRows != this.numCols || this.numRows == 0 || this.determinant() == 0)
            throw new RuntimeException("Matrix is not invertible.");

        double det = this.determinant();
        List<List<Double>> backingArrayCopy = this.getBackingArray();

        for(int i = 0; i < this.numRows; i++) {
            for(int j = 0; j < this.numCols; j++) {
                double cofactor = this.cofactor(i, j);
                double inverseElem = cofactor / det;
                backingArrayCopy.get(j).set(i, inverseElem);
            }
        }

        return new Matrix(backingArrayCopy);
    }


    /* ------------------------ FLUENT TRANSFORMS ------------------------- */
    public Matrix translate(double x, double y, double z) {
        return Matrices.translation(x, y, z).mult(this);
    }


    public Matrix scale(double x, double y, double z) {
        return Matrices.scale(x,y,z).mult(this);
    }


    public Matrix rotateX(double rad) {
        return Matrices.rotateX(rad).mult(this);
    }


    public Matrix rotateY(double rad) {
        return Matrices.rotateY(rad).mult(this);
    }


    public Matrix rotateZ(double rad) {
        return Matrices.rotateZ(rad).mult(this);
    }


    public Matrix shear(double xy, double xz,
                        double yx, double yz,
                        double zx, double zy) {
        return Matrices.shear(xy,xz,yx,yz,zx,zy).mult(this);
    }

}
