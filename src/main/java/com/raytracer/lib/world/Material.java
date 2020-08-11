package com.raytracer.lib.world;

import com.raytracer.lib.primitives.Color;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value @Builder @RequiredArgsConstructor
public class Material {
    Color color;
    double ambient;     // 0 - 1
    double diffuse;     // 0 - 1
    double specular;    // 0 - 1
    double shininess;   // 10 - 200

    public Material() {
        // default material
        this.color = new Color(1,1,1);
        this.ambient = 0.1;
        this.diffuse = 0.9;
        this.specular = 0.9;
        this.shininess = 200;
    }

    public Material(Color c) {
        // default material
        this.color = c;
        this.ambient = 0.1;
        this.diffuse = 0.9;
        this.specular = 0.9;
        this.shininess = 200;
    }

}
