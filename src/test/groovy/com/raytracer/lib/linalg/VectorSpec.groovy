package com.raytracer.lib.linalg

import spock.lang.Specification

class VectorSpec extends Specification{

    def "Adding two Vectors should return the correct Vector"() {
        given:
        def v1 = VectorFactory.makeVector(3, 4, 5)
        def v2 = VectorFactory.makeVector(-3, 4, 5000000000.234224524521)
        def vAns = VectorFactory.makeVector(0, 8, 5000000005.234224524521)

        when:
        def vSum = v1.add(v2)

        then:
        vSum.getElements().get(0) == 0
        vSum.getElements().get(1) == 8
        vSum.getElements().get(2) == 5000000005.234224524521
        vSum.getElements().get(3) == 0
        vSum == vAns
    }

    def "Adding a Vector and a Point should return the correct Point"() {
        given:
        def v1 = VectorFactory.makeVector(3, 4, 5)
        def p1 = VectorFactory.makePoint(-3, 4, 5000000000.234224524521)
        def pAns = VectorFactory.makePoint(0, 8, 5000000005.234224524521)

        when:
        def pSum = v1.add(p1)

        then:
        pSum.getElements().get(0) == 0
        pSum.getElements().get(1) == 8
        pSum.getElements().get(2) == 5000000005.234224524521
        pSum.getElements().get(3) == 1
        pSum == pAns
    }

    def "Subtracting two Vectors should return the correct Vector"() {
        given:
        def v1 = VectorFactory.makeVector(3, 4, 5)
        def v2 = VectorFactory.makeVector(-3, 4, 5000000000.234224524521)
        def vAns = VectorFactory.makeVector(6, 0, 5 - 5000000000.234224524521)

        when:
        def vSum = v1.subtract(v2)

        then:
        vSum.getElements().get(0) == 6
        vSum.getElements().get(1) == 0
        vSum.getElements().get(2) == 5 - 5000000000.234224524521
        vSum.getElements().get(3) == 0
        vSum == vAns
    }

    def "Subtracting a Vector and a Point should return the correct Point"() {
        given:
        def v1 = VectorFactory.makeVector(3, 4, 5)
        def p1 = VectorFactory.makePoint(-3, 4, 5000000000.234224524521)
        def pAns = VectorFactory.makePoint(-6, 0, 5000000000.234224524521 - 5)

        when:
        def pSum = p1.subtract(v1)

        then:
        pSum.getElements().get(0) == -6
        pSum.getElements().get(1) == 0
        pSum.getElements().get(2) == 5000000000.234224524521 - 5
        pSum.getElements().get(3) == 1
        pSum == pAns
    }

    def "Negating Tuples should be simple" () {
        // TODO
    }

    def "Multiplying and Dividing Vectors with Scalars should be simple" () {
        // TODO
    }

    def "Magnitude of a vector should be simple"() {
        // TODO
    }

    def "Normalizing of a vector should be simple"() {
        // TODO
    }

    def "Dot Product of vectors should be simple"() {
        // TODO
    }

    def "Cross Product of vectors should be simple"() {
        // TODO
    }

}
