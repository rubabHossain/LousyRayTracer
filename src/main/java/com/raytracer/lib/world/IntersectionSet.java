package com.raytracer.lib.world;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Value @RequiredArgsConstructor
public class IntersectionSet {

    List<Intersection> intersectionsList;

    public IntersectionSet() {
        this.intersectionsList = new ArrayList<>();
    }

    public IntersectionSet(Intersection ... intersections) {
        this.intersectionsList = new ArrayList<>();
        this.intersectionsList.addAll(Arrays.asList(intersections));
    }


    public int getNumIntersections() {
        return this.intersectionsList.size();
    }


    public Optional<Intersection> getHit() {
        return this.intersectionsList.stream()
                .filter( i -> i.getIntersectionTime() > 0)
                .min( (i1, i2) -> (int) Math.round(i1.getIntersectionTime() - i2.getIntersectionTime()) );
    }


}
