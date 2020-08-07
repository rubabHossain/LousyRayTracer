package com.raytracer.lib.linalg

import spock.lang.Specification

class MatrixSpec extends Specification{

    def "Constructing matrix from list of ROWS"() {
        given:
        def row0 = [0.0, 0.1, 0.2, 0.3]
        def row1 = [1.0, 1.1, 1.2, 1.3]
        def row2 = [2.0, 2.1, 2.2, 2.3]
        def row3 = [3.0, 3.1, 3.2, 3.3]
        def listOfRows = [row0, row1, row2, row3]

        when:
        def mtx = new Matrix(listOfRows)
        def underlyingArray = mtx.getBackingArray()

        then:
        underlyingArray.get(0).get(0) == 0.0
        underlyingArray.get(0).get(1) == 0.1
        underlyingArray.get(0).get(2) == 0.2
        underlyingArray.get(2).get(3) == 2.3
        underlyingArray.get(2).get(0) == 2.0
        underlyingArray.get(3).get(1) == 3.1
        underlyingArray.get(3).get(3) == 3.3
    }

    def "Matrix x Matrix MULTIPLICATION"() {
        given:
        def row0 = [0.0, 0.1, 0.2, 0.3]
        def row1 = [1.0, 1.1, 1.2, 1.3]
        def row2 = [2.0, 2.1, 2.2, 2.3]
        def row3 = [3.0, 3.1, 3.2, 3.3]
        def listOfRows = [row0, row1, row2, row3]

        when:
        def mtx1 = new Matrix(listOfRows)
        def mtx2 = new Matrix(listOfRows)
        def underlyingArray = mtx2.getBackingArray()

        then:
        true
    }

    def "Matrix x Vector MULTIPLICATION"() {

    }

    def "Identity MULTIPLICATION" () {

    }
}
