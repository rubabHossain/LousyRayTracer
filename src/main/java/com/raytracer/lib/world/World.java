package com.raytracer.lib.world;

import lombok.Value;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Value
public class World {
    List<Sphere> entities;
    List<LightSource> lightSources;

    public World() {
        this.entities = new ArrayList<>();
        this.lightSources = new ArrayList<>();
    }

    public World addEntity(Sphere s) {
        this.entities.add(s);
        return this;
    }

    public World addLightSource(LightSource l) {
        this.lightSources.add(l);
        return this;
    }

    public World addAllEntities(Collection<Sphere> spheres) {
        this.entities.addAll(spheres);
        return this;
    }

    public World addAllLightSources(Collection<LightSource> lightSources) {
        this.lightSources.addAll(lightSources);
        return this;
    }




}
