package com.raytracer.lib.linalg;

import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that represents an arbitrary-dimension Vector of doubles.
 * Serves as the base abstraction for pretty much everything else.
 */
@Value
public class Vector {
    
    private static final double EPSILON = 1E-7;  // 0.000_000_1 -- the threshold used to determine if 2 doubles are equal.
    ArrayList<Double> elements;    // at the lowest level, a vector is just a List of doubles.


    public Vector(Double ... elements) {
        this.elements = new ArrayList<>();
        this.elements.addAll(Arrays.asList(elements));
    }


    public Vector(List<Double> elements) {
        this.elements = new ArrayList<>();
        this.elements.addAll(elements);
    }


    public List<Double> getElements() {
        return new ArrayList<>(this.elements); // return a shallow copy of the underlying arraylist
                                               // so that clients cannot mutate underlying data structure.
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)                        // self check
            return true;

        if (o == null)                        // null check
            return false;


        if (o.getClass() != this.getClass())  // type check and cast
            return false;

        Vector otherVector = (Vector) o;      // compare fields ....
        List<Double> otherElements = otherVector.getElements();

        if(otherElements.size() != this.elements.size())
            return false;

        for(int i = 0; i < this.elements.size(); i++) {
            double diff = Math.abs(otherElements.get(i) - this.elements.get(i));
            if(diff > EPSILON)
                return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int i = 3;
        int sum = 0;
        for(double d : this.getElements()) {
            sum += (i * Math.round(d));
            i += 2;
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        this.elements.forEach(d -> sb.append(d).append(", "));
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    /* ------------------------ Vector Operations ------------------------ */

    /**
     *  Given vectors this and other, returns a third vector c where c_i = this_i OP other_i
     *   Throws runtime exception if vectors of differing length or is null.
     *  
     * @param other Other vector to apply elementwise operations with.
     * @param operation the operation to perform (eg add, subtract, dot product ...)
     * @return  a new vector
     */
    private Vector applyElementWiseOperation(@NonNull final Vector other,
                                             @NonNull final BiFunction<Double, Double, Double> operation) {
        if(other == null || this.getElements().size() != other.getElements().size())
            throw new RuntimeException("Cannot apply elementwise operation to tuples of differing lengths.");

        List<Double> combination = IntStream.range(0, this.getElements().size())
                .mapToDouble(i -> operation.apply(this.getElements().get(i), other.getElements().get(i)))
                .boxed()
                .collect(Collectors.toList());

        return new Vector(combination);
    }


    public Vector add(Vector other) {
        BiFunction<Double, Double, Double> sum = Double::sum;
        return this.applyElementWiseOperation(other, sum);
    }


    public Vector subtract(Vector other) {
        BiFunction<Double, Double, Double> diff = (x, y) -> x - y;
        return this.applyElementWiseOperation(other, diff);
    }


    public Vector mult(double scalar) {
        BiFunction<Double, Double, Double> scalarMult = (x, _y) -> scalar * x;
        return this.applyElementWiseOperation( this, scalarMult);
    }


    public Vector div(double scalar) {
        BiFunction<Double, Double, Double> scalarDiv = (x, _y) ->  x / scalar;
        return this.applyElementWiseOperation(this, scalarDiv);
    }


    public double dotProduct(Vector other) {
        BiFunction<Double, Double, Double> mult = (x, y) ->  x * y;
        return this.applyElementWiseOperation(other, mult).getElements().stream()
                .mapToDouble(i->i)
                .sum();
    }


    public Vector crossProduct(Vector other) {
        List<Double> aList = this.getElements();
        List<Double> bList = other.getElements();

        if(aList.size() != 4 || bList.size() != 4) // added 1 to account for indicator dimension
            throw new RuntimeException("Cross Product only defined for 3 dimensional vectors.");

        double ax = aList.get(0), ay = aList.get(1), az = aList.get(2);
        double bx = bList.get(0), by = bList.get(1), bz = bList.get(2);

        return new Vector(ay * bz - az * by,
                az * bx - ax * bz,
                ax * by - ay * bx,
                0d);
    }


    public Vector negate() {
        return this.mult(-1);
    }


    public double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }


    public Vector normalize() {
        return this.div(this.magnitude());
    }

}
