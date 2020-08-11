package com.raytracer.lib.primitives;

import com.raytracer.lib.linalg.ColumnVector;
import lombok.NonNull;
import java.util.List;


public class Vector extends ColumnVector{

    public Vector(double x, double y, double z) {
        super(x, y, z , 0d);
    }

    public double getX() {
        return this.getElements().get(0);
    }

    public double getY() {
        return this.getElements().get(1);
    }

    public double getZ() {
        return this.getElements().get(2);
    }

    public Vector verify() {
        if(this.getElements().get(3) != 0)
            throw new RuntimeException("Integrity of vector datatype violated.");
        return this;
    }
    /* -------------------- Operations -----------------------*/

    public Vector add(@NonNull final Vector other) {
        ColumnVector cv = super.add(other);
        return new Vector(cv.getElements().get(0), cv.getElements().get(1), cv.getElements().get(2));
    }


    public Vector subtract(@NonNull final Vector other) {
        ColumnVector cv = super.subtract(other);
        return new Vector(cv.getElements().get(0), cv.getElements().get(1), cv.getElements().get(2));
    }


    public Vector mult(final double scalar) {
        ColumnVector cv = super.mult(scalar);
        return new Vector(cv.getElements().get(0), cv.getElements().get(1), cv.getElements().get(2));
    }


    public Vector div(final double scalar) {
        ColumnVector cv = super.div(scalar);
        return new Vector(cv.getElements().get(0), cv.getElements().get(1), cv.getElements().get(2));
    }


    public Vector negate() {
        return this.mult(-1);
    }


    public double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }


    public Vector normalize() {
        return this.div(this.magnitude());
    }


    public double dotProduct(@NonNull final Vector other) {
        return super.dotProduct(other);
    }


    public Vector crossProduct(@NonNull final Vector other) {
        List<Double> aList = this.getElements();
        List<Double> bList = other.getElements();

        if(aList.size() != 4 || bList.size() != 4) // added 1 to account for indicator dimension
            throw new RuntimeException("Cross Product only defined for 3 dimensional vectors.");

        double ax = aList.get(0), ay = aList.get(1), az = aList.get(2);
        double bx = bList.get(0), by = bList.get(1), bz = bList.get(2);

        double newX = (ay * bz) - (az * by);
        double newY = (az * bx) - (ax * bz);
        double newZ = (ax * by - ay * bx);

        return new Vector(newX, newY, newZ);
    }

    public Vector reflect(Vector normal) {
        double dotProduct = this.dotProduct(normal);
        return this.subtract(normal.mult(2 * dotProduct));
    }

}
