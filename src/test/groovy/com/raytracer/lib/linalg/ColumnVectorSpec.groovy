package com.raytracer.lib.linalg

import spock.lang.Specification

class ColumnVectorSpec extends Specification{
    def "Col Vector EQUALS"() {
        when:
        def v1 = new ColumnVector(0, 0, 0)
        def v2 = new ColumnVector(0, 0, 0)
        v2.subtract(v1)
        def v4 = new ColumnVector(5, 3, 2)
        def v5 = new ColumnVector(5, 3, 2 + 1E-8)
        def v6 = new ColumnVector(5, 3, 2 + 1E-6)
        then:
        v2 == v1
        v4 == v5
        v4 != v6
    }


    def "Col Vector ADDITION"() {
        given:
        def v1 = new ColumnVector(3.0, 4.0, 5.0)
        def v2 = new ColumnVector(-3, 4, 5000000000.234224524521)
        def vAns = new ColumnVector(0, 8, 5000000005.234224524521)

        when:
        def vSum = v1.add(v2)

        then:
        vSum.getElements().get(0) == 0
        vSum.getElements().get(1) == 8
        vSum.getElements().get(2) == 5000000005.234224524521
        vSum == vAns
    }

    def "Col Vector SUBTRACTION"() {
        given:
        def v1 = new ColumnVector(3, 4, 5)
        def v2 = new ColumnVector(-3, 4, 5000000000.234224524521)
        def vAns = new ColumnVector(6, 0, 5 - 5000000000.234224524521)

        when:
        def vSum = v1.subtract(v2)

        then:
        vSum.getElements().get(0) == 6
        vSum.getElements().get(1) == 0
        vSum.getElements().get(2) == 5 - 5000000000.234224524521
        vSum == vAns
    }


    def "Col Vector NEGATION" () {
        when:
        def v1 = new ColumnVector(3, 4, 5)
        def v2 = (new ColumnVector(-3, -4, -1*(5+1E-8))).negate()
        then:
        v1==v2
    }

    def "Col Vector SCALAR Mult-Div" () {
        when:
        def zero = ColumnVector.make(0,0,0)
        def zeroTimes4 = ColumnVector.make(0,0,0).mult(4)

        def v1 = ColumnVector.make(1, 1, 1)
        def v4 = ColumnVector.make(4, 4, 4)
        def v1Times4 = ColumnVector.make(1,1,1).mult(4)
        def v1Times4Div4 = ColumnVector.make(1,1,1).mult(4).div(4)
        then:
        zero == zeroTimes4
        v1Times4 == v4
        v1 == v1Times4Div4
    }

    def "Col Vector MAGNITUDE"() {
        when:
        def unit = ColumnVector.make(-1,0,0)
        def ez = ColumnVector.make(3,-4)
        def exact = ColumnVector.make(5, 10, 453.34)

        then:
        unit.magnitude() == 1
        ez.magnitude() == 5
        Math.abs( exact.magnitude() - Math.sqrt( (5*5) + (10*10) + (453.34*453.34))) < 1E-7
    }

    def "Col Vector NORMALIZE"() {
        when:
        def unit = ColumnVector.make(1, 0, 0)
        def unitNormalized = ColumnVector.make(1, 0, 0).normalize()

        def v1 = ColumnVector.make(3, -4)
        def v1Normalized = ColumnVector.make(3/5, -4/5)

        def v1Dim = ColumnVector.make(51543632.15415415)
        def v1DimNormalized = ColumnVector.make(1)

        def x = 451541.1451
        def y = 1075001965.4135
        def z = -1241.122455
        def mag = Math.sqrt((x*x) + (y*y) + (z*z))
        def xNorm = x / mag
        def yNorm = y / mag
        def zNorm = z / mag
        def vHard = ColumnVector.make(x, y, z)
        def vHardNormalized = ColumnVector.make(xNorm, yNorm, zNorm)

        then:
        unit.normalize() == unitNormalized
        v1.normalize() == v1Normalized
        v1Dim.normalize() == v1DimNormalized
        vHard.normalize() == vHardNormalized

    }

    def "Col Vector DOT PRODUCT"() {
        when:
        def v0 = ColumnVector.make(1, 0, -1)
        def v1 = ColumnVector.make(3, -4, 5)
        def v2 = ColumnVector.make(51543632.15415415,
                                   51543632.15415415,
                                   51543632.15415415)

        then:
        v0.dotProduct(v0) == 2
        v0.dotProduct(v1) == -2
        v0.dotProduct(v2) == 0

    }


}
