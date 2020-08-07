package com.raytracer.lib.linalg;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Class that represents an arbitrary-dimension Vector of doubles.
 * Serves as the base abstraction for pretty much everything else.
 * Mostly a wrapper around List<Double>, and also provides utility functions.
 */
public class ColumnVector {
    
    private static final double EPSILON = 1E-7;  // 0.000_000_1 -- the threshold used to determine if 2 doubles are equal.
    private final ArrayList<Double> elements;    // at the lowest level, a vector is just a List of doubles.


    public ColumnVector(@NonNull final Double ... elements) {
        this.elements = new ArrayList<>();
        this.elements.addAll(Arrays.asList(elements));
    }


    public ColumnVector(@NonNull final List<Double> elements) {
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

        ColumnVector otherVector = (ColumnVector) o;      // compare fields ....
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
        sb.append("]");
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
    private ColumnVector applyElementWiseOperation(@NonNull final ColumnVector other,
                                                   @NonNull final BiFunction<Double, Double, Double> operation) {

        if(this.elements.size() != other.getElements().size())
            throw new RuntimeException("Cannot apply elementwise operation to vectors of differing lengths.");

        for(int i = 0; i < this.elements.size(); i++) {
            double currElem = this.elements.get(i);
            double incomingElem = other.getElements().get(i);

            double newElement = operation.apply(currElem, incomingElem);
            this.elements.set(i, newElement);
        }

        return this;
    }


    public ColumnVector add(@NonNull final ColumnVector other) {
        BiFunction<Double, Double, Double> sum = Double::sum;
        return this.applyElementWiseOperation(other, sum);
    }


    public ColumnVector subtract(@NonNull final ColumnVector other) {
        BiFunction<Double, Double, Double> diff = (x, y) -> x - y;
        return this.applyElementWiseOperation(other, diff);
    }


    public ColumnVector mult(final double scalar) {
        BiFunction<Double, Double, Double> scalarMult = (x, _y) -> scalar * x;
        return this.applyElementWiseOperation( this, scalarMult);
    }


    public ColumnVector div(final double scalar) {
        BiFunction<Double, Double, Double> scalarDiv = (x, _y) ->  x / scalar;
        return this.applyElementWiseOperation(this, scalarDiv);
    }


    public double dotProduct(@NonNull final ColumnVector other) {
        BiFunction<Double, Double, Double> mult = (x, y) ->  x * y;
        return this.applyElementWiseOperation(other, mult).getElements().stream()
                .mapToDouble(i->i)
                .sum();
    }


    public ColumnVector negate() {
        return this.mult(-1);
    }


    public double magnitude() {
        return Math.sqrt(this.dotProduct(this));
    }


    public ColumnVector normalize() {
        return this.div(this.magnitude());
    }

}
