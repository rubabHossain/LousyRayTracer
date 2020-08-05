package com.raytracer.geometry.tuples.datatypes;

import java.util.ArrayList;
import java.util.List;


class Tuple{
    
    private static final double EPSILON = 1E-5; // 0.000_000_1

    private final double[] elements;

    public Tuple(double ... tupleElements) {
        this.elements = new double[tupleElements.length];
        System.arraycopy(tupleElements, 0, this.elements, 0, elements.length);
    }

    
    public List<Double> getElements() {
        List<Double> elementsCopy = new ArrayList<>(elements.length);
        for(double d : this.elements) {
            elementsCopy.add(d);
        }
        return elementsCopy;
    };



    @Override
    public boolean equals(Object o) {
        if (this == o)                  // self check
            return true;

        if (o == null)                  // null check
            return false;

        if (getClass() != o.getClass()) // type check and cast
            return false;

        Tuple otherTuple = (Tuple) o;   // compare fields ....
        List<Double> otherElements = otherTuple.getElements();
        
        if(otherElements.size() != this.elements.length)
            return false;
        
        for(int i = 0; i < elements.length; i++) {
            double diff = otherElements.get(i) - this.elements[i];
            diff = diff < 0 ? diff * -1 : diff;
            if(diff > EPSILON)
                return false;
        }

        return true;
    }

}
