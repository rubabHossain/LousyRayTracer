package com.raytracer.lib.world;

import com.raytracer.lib.linalg.ColumnVector;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class Ray {
    @Getter private final Point origin;
    @Getter private final Vector direction;
    @Getter private final List<Intersection> intersections;

    private boolean isIntersected;


    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
        this.intersections = new ArrayList<>();
        this.isIntersected = false;
    }


    public Point position(double t) {
        return origin.add( direction.mult(t) ); // origin + direction * t
    }


    public List<Intersection> computeIntersections(World w) {
        this.isIntersected = true;

        this.intersections.clear();

        for(Sphere s: w.getEntities()) {
            List<Intersection> newIntersections = this.getIntersections(s);
            this.intersections.addAll(newIntersections);
        }

        this.intersections.sort( (i1, i2) -> (int) Math.round(i1.getIntersectionTime() - i2.getIntersectionTime()));
        return this.intersections;
    }


    public int getNumIntersections() {
        return this.intersections.size();
    }


    public Optional<Intersection> getHit() {
        if(!this.isIntersected)
            throw new RuntimeException("Cannot call ray.getHit() before calling ray.computeIntersections().");

        return this.intersections.stream()
                .filter( i -> i.getIntersectionTime() > 0)
                .min( (i1, i2) -> (int) Math.round(i1.getIntersectionTime() - i2.getIntersectionTime()) );
    }


    @Value
    public static class Intersection {
        double intersectionTime;
        Sphere sphere;

        Point hitPoint;
        Vector eyeVector;
        Vector normalAtHitPoint;

        public Intersection(Ray ray, double intersectionTime, Sphere sphere) {
            this.intersectionTime = intersectionTime;
            this.sphere = sphere;

            this.hitPoint = ray.position(intersectionTime);
            this.eyeVector = ray.getDirection().negate();
            this.normalAtHitPoint = sphere.getNormalAt(hitPoint);
        }
    }


    /* -------------------------------------------------------------------------------------------------------------- */
    private List<Intersection> getIntersections(Sphere s) {
        // apply inverse sphere transforms to ray
        Ray transformedRay = this.transform(s.getInverseTransformations());

        Vector sphereToRay = (transformedRay.getOrigin()).subtract(s.getCenter());

        double a = (transformedRay.getDirection()).dotProduct(transformedRay.getDirection());
        double b = 2 * (transformedRay.getDirection()).dotProduct(sphereToRay);
        double c = (sphereToRay).dotProduct(sphereToRay) - 1;

        double discriminant = (b*b) - (4*a*c);  // b^2 - 4ac

        if(discriminant < 0) {
            return new ArrayList<>();
        }

        double intPoint1 = ( (-1*b) - Math.sqrt(discriminant)) / (2*a);
        double intPoint2 = ( (-1*b) + Math.sqrt(discriminant)) / (2*a);
        return new ArrayList<>(Arrays.asList(new Intersection(this, intPoint1, s),
                                             new Intersection(this, intPoint2, s) ));
    }


    private Ray transform(Matrix transformation) {
        ColumnVector newOrigin = transformation.mult(this.origin);
        ColumnVector newDirection = transformation.mult(this.direction);

        Point newOriginAsPoint = new Point(newOrigin.getElements().get(0),
                                           newOrigin.getElements().get(1),
                                           newOrigin.getElements().get(2));
        Vector newDirectionAsVector = new Vector(newDirection.getElements().get(0),
                                                 newDirection.getElements().get(1),
                                                 newDirection.getElements().get(2));

        return new Ray(newOriginAsPoint, newDirectionAsVector);
    }

}
