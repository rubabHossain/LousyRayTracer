package com.raytracer;

import com.raytracer.lib.linalg.Vector;
import com.raytracer.lib.linalg.VectorFactory;
import lombok.RequiredArgsConstructor;
import lombok.Value;


/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Vector startingPos = VectorFactory.makePoint(0, 1, 0);
        Vector startingVel = VectorFactory.makeVector(1, 1, 0).normalize();
        Projectile p = new Projectile(startingPos, startingVel);

        Vector g = VectorFactory.makeVector(0, -0.1, 0);
        Vector w = VectorFactory.makeVector(-0.01, 0, 0);
        Environment e = new Environment(g, w);

        while(p.getPosition().getElements().get(1) > 0) {
            p = tick(p, e);
        }

        System.out.println("Final Position: " + p);
    }

    public static Projectile tick(Projectile p, Environment e) {
        Vector pos = p.getPosition(), vel = p.getVelocity();
        Vector g = e.getGravity(), w = e.getWind();

        Vector newPos = pos.add(vel);
        Vector newVel = vel.add(g).add(w);

        return new Projectile(newPos, newVel);
    }

    @Value @RequiredArgsConstructor
    public static class Environment {
        Vector gravity, wind;
    }

    @Value @RequiredArgsConstructor
    public static class Projectile {
        Vector position, velocity;
    }


}
