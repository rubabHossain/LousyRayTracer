package com.raytracer.lib.linalg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {
    private final static double EPSILON = 1E-7;

    private final List<List<Double>> rows;
    private int numRows, numCols;


    public Matrix() {
        this.rows = new ArrayList<>();
        this.numRows = 0;
        this.numCols = 0;
    }


    public Matrix(List<List<Double>> listOfRows) {
        this.rows = new ArrayList<>();
        for(List<Double> row : listOfRows) {
            List<Double> rowCopy = new ArrayList<>(row);
            this.rows.add(rowCopy);
        }
        this.numRows = rows.size();
        this.numCols = numRows > 0 ? rows.get(0).size() : 0;
    }


    public List<List<Double>> getBackingArray() {
        return this.rows;
    }


    public int getNumRows() {
        return this.numRows;
    }


    public int getNumColumns() {
        return this.numCols;
    }


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


    public List<Double> getColumn(int i) {
        return this.rows.stream()
                .map( row -> row.get(i) )
                .collect(Collectors.toList());
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

    /* -------------------- Operations -------------------- */

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

    public Matrix transpose() {
        Matrix mtx = new Matrix();
        for(int i = 0; i < numCols; i++) {
            List<Double> col = this.getColumn(i);
            mtx.addRow(col);
        }
        return mtx;
    }

}
