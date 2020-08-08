package com.raytracer.lib.linalg;

import java.util.ArrayList;
import java.util.List;

public class MatrixFactory {

    public static Matrix Zero(int n) {
        List<List<Double>> listOfRows = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for(int j = 0; j < n; j++) {
                row.add(0d);
            }
            listOfRows.add(row);
        }
        return new Matrix(listOfRows);
    }


    public static Matrix Identity(int n) {
        List<List<Double>> temp = MatrixFactory.Zero(n).getBackingArray();
        for(int i = 0; i < n; i++)
            temp.get(i).set(i, 1d);
        return new Matrix(temp);
    }
}
