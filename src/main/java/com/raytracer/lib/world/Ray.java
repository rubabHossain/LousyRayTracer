package com.raytracer.lib.world;

import com.raytracer.lib.linalg.ColumnVector;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import lombok.RequiredArgsConstructor;
import lombok.Value;



@Value @RequiredArgsConstructor
public class Ray {
    Point origin;
    Vector direction;


    public Point position(double t) {
        return origin.add( direction.mult(t) ); // origin + direction * t
    }


    public IntersectionSet getIntersections(Sphere s) {
        // apply inverse sphere transforms to ray
        Ray transformedRay = this.transform(s.getTransformations().inverse());

        Vector sphereToRay = (transformedRay.getOrigin()).subtract(s.getCenter());

        double a = (transformedRay.getDirection()).dotProduct(transformedRay.getDirection());
        double b = 2 * (transformedRay.getDirection()).dotProduct(sphereToRay);
        double c = (sphereToRay).dotProduct(sphereToRay) - 1;

        double discriminant = (b*b) - (4*a*c);  // b^2 - 4ac

        if(discriminant < 0) {
            return new IntersectionSet();
        }

        double intPoint1 = ( (-1*b) - Math.sqrt(discriminant)) / (2*a);
        double intPoint2 = ( (-1*b) + Math.sqrt(discriminant)) / (2*a);
        return new IntersectionSet(new Intersection(intPoint1, s),
                                   new Intersection(intPoint2, s));
    }


    public Ray transform(Matrix transformation) {
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
