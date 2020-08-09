package com.raytracer.lib.linalg

import spock.lang.Specification
import com.raytracer.lib.primitives.Vector
import com.raytracer.lib.primitives.Point

class MatricesSpec extends Specification {

    def "Mult by Identity does nothing"() {
        when:
        def mtxArray =
                [[ 1d, 2d, 3d, 4d ],
                 [ 5d, 6d, 7d, 8d ],
                 [ 9d, 8d, 7d, 6d ],
                 [ 5d, 4d, 3d, 2d ]]
        def mtx = new Matrix(mtxArray)
        then:
        mtx.mult(Matrices.Identity(4)) == mtx
    }

    def "TRANSLATION Matrix Test"() {

        when:
        def origin = new Point(0,0,0)
        def p1 = new Point(3, 4, 5)
        def v1 = new Vector(3, 4, 5)
        Matrix translation = Matrices.translation(1, 2, 3)

        then:
        translation.mult(origin) == new Point(1, 2, 3)
        translation.mult(p1) == new Point(4, 6, 8)
        translation.mult(v1) == v1

        translation.inverse().mult(translation).mult(p1) == p1
        translation.inverse().mult(translation).mult(origin) == origin
    }

    def "SCALE Matrix Test"() {

        when:
        def origin = new Point(0,0,0)
        def p1 = new Point(3, 4, 5)
        def v1 = new Vector(3, 4, 5)
        Matrix transformation = Matrices.scale(10, 1, 1)

        then:
        transformation.mult(origin) == new Point(0, 0, 0)
        transformation.mult(p1) == new Point(30, 4, 5)
        transformation.mult(v1) == new Vector(30, 4, 5)

        transformation.inverse().mult(transformation).mult(p1) == p1
        transformation.inverse().mult(transformation).mult(origin) == origin
        transformation.inverse().mult(transformation).mult(v1) == v1
    }


    def "ROTATION X Matrix Test"() {
        when:
        def p = new Point(0, 1, 0)
        def v = new Vector(0, 1, 0)
        def quarterCWRotationX = Matrices.rotateX(Math.PI / 2)
        def halfQuarterCWRotationX = Matrices.rotateX(Math.PI / 4)
        then:
        // X rotations on points
        halfQuarterCWRotationX.mult(p) == new Point(0, Math.sqrt(2)/2, Math.sqrt(2)/2)
        halfQuarterCWRotationX.mult(halfQuarterCWRotationX).mult(p) == quarterCWRotationX.mult(p)
        quarterCWRotationX.mult(p) == new Point(0, 0, 1)

        // X rotations on vectors
        halfQuarterCWRotationX.mult(v) == new Vector(0, Math.sqrt(2)/2, Math.sqrt(2)/2)
        quarterCWRotationX.mult(v) == new Vector(0, 0, 1)

        // X rotation inverse
        halfQuarterCWRotationX.mult(halfQuarterCWRotationX.inverse()).mult(p) == p
    }


    def "ROTATION Y Matrix Test"() {
        when:
        def p = new Point(0, 0, 1)
        def v = new Vector(0, 0, 1)
        def quarterCWRotationY = Matrices.rotateY(Math.PI / 2)
        def halfQuarterCWRotationY = Matrices.rotateY(Math.PI / 4)

        then:
        // Y rotations on points
        halfQuarterCWRotationY.mult(p) == new Point(Math.sqrt(2)/2, 0, Math.sqrt(2)/2)
        halfQuarterCWRotationY.mult(halfQuarterCWRotationY).mult(p) == quarterCWRotationY.mult(p)
        quarterCWRotationY.mult(p) == new Point(1, 0, 0)
        // Y rotations on vectors
        halfQuarterCWRotationY.mult(v) == new Vector(Math.sqrt(2)/2, 0, Math.sqrt(2)/2)
        quarterCWRotationY.mult(v) == new Vector(1, 0, 0)
        // Y rotation inverse
        halfQuarterCWRotationY.mult(halfQuarterCWRotationY.inverse()).mult(p) == p

    }


    def "ROTATION Z Matrix Test"() {
        when:
        def p = new Point(0, 1, 0)
        def v = new Vector(0, 1, 0)

        def quarterCWRotationZ = Matrices.rotateZ(Math.PI / 2)
        def halfQuarterCWRotationZ = Matrices.rotateZ(Math.PI / 4)

        then:
        // Z rotations on points
        halfQuarterCWRotationZ.mult(p) == new Point(-1 * Math.sqrt(2)/2, Math.sqrt(2)/2, 0)
        halfQuarterCWRotationZ.mult(halfQuarterCWRotationZ).mult(p) == quarterCWRotationZ.mult(p)
        quarterCWRotationZ.mult(p) == new Point(-1, 0, 0)
        // Z rotations on vectors
        halfQuarterCWRotationZ.mult(v) == new Vector(-1 * Math.sqrt(2)/2, Math.sqrt(2)/2, 0)
        quarterCWRotationZ.mult(v) == new Vector(-1, 0, 0)
        // Z rotation inverse
        halfQuarterCWRotationZ.mult(halfQuarterCWRotationZ.inverse()).mult(p) == p
    }

    def "SHEAR Matrix Test"() {
        when:
        def shearxy = Matrices.shear(1,0,0,0,0,0)
        def shearxz = Matrices.shear(0,1,0,0,0,0)
        def shearyx = Matrices.shear(0,0,1,0,0,0)
        def shearyz = Matrices.shear(0,0,0,1,0,0)
        def shearzx = Matrices.shear(0,0,0,0,1,0)
        def shearzy = Matrices.shear(0,0,0,0,0,1)
        def p = new Point(2,3,4)

        then:
        shearxy.mult(p) == new Point(5, 3, 4)
        shearxz.mult(p) == new Point(6,3,4)

        shearyx.mult(p) == new Point(2, 5, 4)
        shearyz.mult(p) == new Point(2, 7, 4)

        shearzx.mult(p) == new Point(2, 3, 6)
        shearzy.mult(p) == new Point(2, 3, 7)
    }

}
