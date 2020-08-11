package com.raytracer.lib.world;

import com.raytracer.lib.linalg.Matrices;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.Builder;

@Value @RequiredArgsConstructor @Builder
public class Sphere {

    Material material;
    Point center;
    Matrix transformations;
    Matrix inverseTransformations;

    // default
    public Sphere() {
        this.material = new Material();
        this.center = new Point(0,0,0);
        this.transformations = Matrices.Identity(4);
        this.inverseTransformations = Matrices.Identity(4);
    }

    // custom material
    public Sphere(Material material) {
        this(material, new Point(0, 0, 0), Matrices.Identity(4), Matrices.Identity(4));
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
        Matrix newInverse = newTransformations.inverse();
        return new Sphere(this.material, this.center, newTransformations, newInverse);
    }

    public Vector getNormalAt(Point p) {
        Point objectPoint = (this.transformations.inverse()).mult(p);
        Vector objectNormal = objectPoint.subtract(this.center).normalize();
        Vector worldNormal = (this.transformations.inverse().transpose()).mult(objectNormal).normalize();
        // worldNormal might have a polluted w component -- return a clean vector with correct xyz components instead.
        return new Vector(worldNormal.getX(), worldNormal.getY(), worldNormal.getZ());
    }

}
