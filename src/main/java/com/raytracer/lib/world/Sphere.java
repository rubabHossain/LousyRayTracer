package com.raytracer.lib.world;

import com.raytracer.lib.linalg.Matrices;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Point;
import lombok.RequiredArgsConstructor;
import lombok.Value;


@Value
@RequiredArgsConstructor
public class Sphere {
    Point center;
    double radius;
    Matrix transformations;

    public Sphere() {
        this.center = new Point(0,0,0);
        this.radius = 1d;
        this.transformations = Matrices.Identity(4);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public Sphere transform(Matrix transformation) {
        Matrix newTransformations = transformation.mult(this.transformations);
        return new Sphere(this.center, this.radius, newTransformations);
    }
}
