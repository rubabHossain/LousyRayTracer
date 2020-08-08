package com.raytracer.lib.linalg;

import java.util.ArrayList;
import java.util.List;

public class MatrixFactory {

    public static Matrix Identity(int n) {
        List<List<Double>> listOfRows = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                row.add(0d);
            }
            row.set(i, 1d);
            listOfRows.add(row);
        }
        return new Matrix(listOfRows);
    }
}
