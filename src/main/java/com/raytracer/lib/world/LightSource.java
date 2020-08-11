package com.raytracer.lib.world;

import com.raytracer.lib.primitives.Color;
import com.raytracer.lib.primitives.Point;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value @RequiredArgsConstructor
public class LightSource {
    Point position;
    Color color;
}
