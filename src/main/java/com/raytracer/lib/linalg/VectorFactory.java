package com.raytracer.lib.linalg;

public class VectorFactory {

    public static Vector makePoint(double x, double y, double z) {
        return new Vector(x, y, z, 1d);
    }

    public static Vector makeVector(double x, double y, double z) {
        return new Vector(x, y, z, 0d);
    }

}
