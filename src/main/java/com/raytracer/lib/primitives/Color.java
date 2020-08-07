package com.raytracer.lib.primitives;

import com.raytracer.lib.linalg.ColumnVector;



public class Color extends ColumnVector{

    public Color(double r, double g, double b) {
        super(r, g, b);
    }


    public double getRed() {
        return this.getElements().get(0);
    }


    public double getGreen() {
        return this.getElements().get(1);
    }


    public double getBlue() {
        return this.getElements().get(2);
    }


    public Color add(Color other) {
        super.add(other);
        return this;
    }

    public Color mult(Color other) {
        super.dotProduct(other);
        return this;
    }
}
