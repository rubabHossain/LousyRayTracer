package com.raytracer;

import com.raytracer.lib.canvas.Canvas;
import com.raytracer.lib.canvas.PpmWriter;
import com.raytracer.lib.linalg.Matrices;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Color;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import com.raytracer.lib.world.LightSource;
import com.raytracer.lib.world.Material;
import com.raytracer.lib.world.Ray;
import com.raytracer.lib.world.Sphere;
import com.raytracer.lib.world.Intersection;
import java.util.Optional;


public class App {
    static int SCALE = 1;
    static int height = 100 * SCALE, width = 100 * SCALE;
    static Canvas canvas = new Canvas(height, width);
    static Point lightPoint = new Point(10 * SCALE,150 * SCALE,2 * SCALE);
    static LightSource lightSource = new LightSource(lightPoint, new Color(1,1,1));
    static Color purple = new Color(1, 0.2, 1);
    static Material material = new Material(purple, 0.1, 0.9, 0.9, 200);
    static Point center = new Point(0,0,0);
    static Matrix identity = Matrices.Identity(4);


    public static void main( String[] args ) {

        Sphere s = new Sphere(material, center, identity, identity);
        s = s.transform(Matrices.scale(30 * SCALE,15  * SCALE,20  * SCALE))
             .transform(Matrices.translation(50  * SCALE, 50 * SCALE, 40 * SCALE));

        Point rayOrigin = new Point(50 * SCALE,50 * SCALE,150 * SCALE);

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Vector dir = rayOrigin.subtract(new Point(j, i, 0)).negate().normalize();
                Ray r = new Ray(rayOrigin, dir);
                Optional<Intersection> hit = r.getIntersections(s).getHit();
                if(hit.isPresent()) {
//                    Color c = new Color(1,1,1);
//                    canvas.setPixel(j, i, c);
                    Point hitPoint = r.position(hit.get().getIntersectionTime());
                    Vector normal = s.getNormalAt(hitPoint);
                    Color shadedC = getColorAfterLighting(s.getMaterial(), lightSource, hitPoint, dir.negate(), normal);
                    canvas.setPixel(j, i, shadedC);
                }
            }
        }

        System.out.println(PpmWriter.toFmtString(canvas));
    }

    public static Color getColorAfterLighting(Material material, LightSource lightSource, Point point, Vector eye, Vector normal) {
        // combine the surface color with the light's color/intensity
        Color effectiveColor = material.getColor().mult(lightSource.getColor());

        // find the direction to the light source
        Vector lightVector = (lightSource.getPosition().subtract(point)).normalize();

        // compute ambient, diffuse, and specular contributions
        Color ambient = computeAmbientColorContribution(effectiveColor, material);
        Color diffuse = computeDiffuseColorContribution(lightVector, normal, material, effectiveColor);
        Color specular = computeSpecularColorContribution(lightVector, normal, material, effectiveColor, eye, lightSource);

        return (ambient).add(diffuse).add(specular);
    }


    private static Color computeAmbientColorContribution(Color effectiveColor, Material material) {
        return effectiveColor.mult(material.getAmbient());
    }


    private static Color computeDiffuseColorContribution(Vector lightVector, Vector normal, Material material, Color effectiveColor) {
        double lightDotNormal = lightVector.dotProduct(normal);
        return  (lightDotNormal < 0) ?
                new Color(0,0,0) :
                effectiveColor.mult(material.getDiffuse()).mult(lightDotNormal);
    }


    private static Color computeSpecularColorContribution(Vector lightVector, Vector normal, Material material, Color effectiveColor, Vector eye, LightSource lightSource) {
        double lightDotNormal = lightVector.dotProduct(normal);
        if(lightDotNormal < 0)
            return new Color(0,0,0);

        Vector reflectionVector = (lightVector.negate()).reflect(normal);
        double reflectionDotEye = reflectionVector.dotProduct(eye);
        if(reflectionDotEye <= 0)
            return new Color(0,0,0);

        double factor = Math.pow(reflectionDotEye, material.getShininess());
        return lightSource.getColor().mult(material.getSpecular()).mult(factor);

    }


}
