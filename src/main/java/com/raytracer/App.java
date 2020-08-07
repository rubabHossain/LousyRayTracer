package com.raytracer;

import com.raytracer.lib.canvas.Canvas;
import com.raytracer.lib.canvas.PpmWriter;
import com.raytracer.lib.primitives.Color;
import com.raytracer.lib.primitives.Point;
import com.raytracer.lib.primitives.Vector;
import lombok.RequiredArgsConstructor;
import lombok.Value;



public class App {
    public static void main( String[] args ) {
        int height = 550, width = 900;


        Canvas canvas = new Canvas(height, width);
        Color white = new Color(1, 1, 1);

        Point startingPos = new Point(0, 1, 0);
        Vector startingVel = new Vector(1, 1.6, 0).normalize().mult(10);
        Projectile prj = new Projectile(startingPos, startingVel);

        Vector g = new Vector(0, -0.1, 0);
        Vector w = new Vector(-0.01, 0, 0);
        Environment e = new Environment(g, w);

        while(prj.getPosition().getY() > 0) {
//            System.out.println(prj.getPosition());
            int x = (int) Math.round(prj.getPosition().getX());
            int y =  (int) Math.round(prj.getPosition().getY());
            canvas.setPixel(height - y, x, white);
            prj = tick(prj, e);
        }

        System.out.println(PpmWriter.toFmtString(canvas));

    }


    public static Projectile tick(Projectile p, Environment e) {
        Point pos = p.getPosition();
        Vector vel = p.getVelocity();
        Vector g = e.getGravity();
        Vector w = e.getWind();

        Point newPos = pos.add(vel);
        Vector newVel = vel.add(g).add(w);
        return new Projectile(newPos, newVel);
    }


    @Value @RequiredArgsConstructor
    public static class Projectile {
        Point position;
        Vector velocity;
    }


    @Value @RequiredArgsConstructor
    public static class Environment {
        Vector gravity;
        Vector wind;
    }

}
