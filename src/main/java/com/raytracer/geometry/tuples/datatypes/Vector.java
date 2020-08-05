package com.raytracer.geometry.tuples.datatypes;

import lombok.Value;

@Value
public class Vector extends Tuple{
    
    public Vector(double x, double y, double z) {
        super(x, y, z, 0.0d);
    }

}
