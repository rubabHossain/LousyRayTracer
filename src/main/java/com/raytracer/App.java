package com.raytracer;

import com.raytracer.lib.canvas.Canvas;
import com.raytracer.lib.canvas.PpmWriter;
import com.raytracer.lib.linalg.Matrices;
import com.raytracer.lib.linalg.Matrix;
import com.raytracer.lib.primitives.Color;
import com.raytracer.lib.primitives.Point;


public class App {
    static int height = 100, width = 100;
    static Canvas canvas = new Canvas(height, width);
    static Color WHITE = new Color(1,1,1);

    public static void main( String[] args ) {

        Matrix rotationMtx = Matrices.Identity(4).rotateZ(Math.PI / 6);
        Matrix translationMtx = Matrices.Identity(4).translate(50, 50, 0);

        Point p = new Point(0, 40, 0);

        for(int i = 0; i < 12; i++) {
            Point drawP = translationMtx.mult(p);
            canvas.setPixel(height - (int) Math.round(drawP.getY()),
                            (int) Math.round(drawP.getX()),
                            WHITE);
            p = rotationMtx.mult(p);
        }

        System.out.println(PpmWriter.toFmtString(canvas));
    }

}
