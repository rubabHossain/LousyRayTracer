package com.raytracer.lib.linalg;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

    private final List<List<Double>> rows;
    private int numRows, numCols;


    public Matrix() {
        this.rows = new ArrayList<>();
        this.numRows = 0;
        this.numCols = 0;
    }

    public Matrix(List<List<Double>> listOfRows) {
        this.rows = listOfRows;
        this.numRows = rows.size();
        this.numCols = numRows > 0 ? rows.get(0).size() : 0;
    }


    public List<List<Double>> getBackingArray() {
        return this.rows;
    }


    public Matrix addRow(List<Double> row) {
        // if this is first row being added, set numCols to be length of incoming row
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
        return this;
    }


    public Matrix mult(Matrix other) {
        return this; // TODO
    }


    public ColumnVector mult(ColumnVector v) {
        return v;   // TODO
    }


}
