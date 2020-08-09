package com.raytracer;

import com.raytracer.lib.canvas.Canvas;
import com.raytracer.lib.canvas.PpmWriter;
import com.raytracer.lib.linalg.Matrices;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Color;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import com.raytracer.lib.world.Ray;
import com.raytracer.lib.world.Sphere;


public class App {
    static int height = 1000, width = 1000;
    static Canvas canvas = new Canvas(height, width);
    static Color WHITE = new Color(1,1,1);

    public static void main( String[] args ) {

        Sphere s = (new Sphere()).transform(Matrices.scale(400,100,100))
                                 .transform(Matrices.translation(500, 500, 0));

        Point rayOrigin = Matrices.translation(500,500,0).mult(new Point(0,0,200));

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Vector dir = rayOrigin.subtract(new Point(j, i, 0)).negate();
                Ray r = new Ray(rayOrigin, dir);
                if(r.getIntersections(s).getHit().isPresent()) {
                    canvas.setPixel(j, i, WHITE);
                }
            }
        }

        System.out.println(PpmWriter.toFmtString(canvas));
    }

}
