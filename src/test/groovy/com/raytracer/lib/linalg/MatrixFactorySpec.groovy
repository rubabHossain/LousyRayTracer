package com.raytracer.lib.linalg

import spock.lang.Specification

class MatrixFactorySpec extends Specification {

    def "Mult by Identity does nothing"() {
        when:
        def mtxArray =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]
        def mtx = new Matrix(mtxArray)
        then:
        mtx.mult(MatrixFactory.Identity(4)) == mtx
    }
}
