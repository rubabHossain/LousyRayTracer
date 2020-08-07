package com.raytracer.lib.linalg

import spock.lang.Specification

class ColumnVectorSpec extends Specification{

    def "Col Vector EQUALS"() {

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
        // TODO
    }

    def "Col Vector SCALAR Mult-Div" () {
        // TODO
    }

    def "Col Vector MAGNITUDE"() {
        // TODO
    }

    def "Col Vector NORMALIZE"() {
        // TODO
    }

    def "Col Vector DOT PRODUCT"() {
        // TODO
    }


}
