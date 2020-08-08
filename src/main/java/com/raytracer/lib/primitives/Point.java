package com.raytracer.lib.primitives;

import com.raytracer.lib.linalg.ColumnVector;
import lombok.NonNull;


public class Point extends ColumnVector{

    public Point(double x, double y, double z) {
        super(x, y, z, 1d);
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

    public Point verify() {
        if(this.getElements().get(3) != 1)
            throw new RuntimeException("Integrity of point datatype violated.");
        return this;
    }

    /* -------------------- Operations -----------------------*/

    public Point add(@NonNull final Vector other) {
        super.add(other);
        return this;
    }


    public Point subtract(@NonNull final Vector other) {
        super.subtract(other);
        return this;
    }

}