package com.raytracer.lib.world;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value @RequiredArgsConstructor
public class Intersection {
    double intersectionTime;
    Sphere sphere;
}
