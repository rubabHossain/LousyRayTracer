package com.raytracer.lib.world

import com.raytracer.lib.linalg.Matrices
import com.raytracer.lib.primitives.Point
import com.raytracer.lib.primitives.Vector
import spock.lang.Specification

class RaySpec extends Specification {

    def "Ray's position method works"() {
        when:
        def r = new Ray(new Point(2,3,4), new Vector(1,0,0))

        then:
        r.position(0) == new Point(2,3,4)
        r.position(1) == new Point(3,3,4)
        r.position(-1) == new Point(1,3,4)
        r.position(2.5) == new Point(4.5,3,4)
    }

    def "Ray-Sphere Intersection works"() {
        given:
        def w = new World().addEntity(new Sphere())
        def rDiff = new Ray(new Point(0d,0d,-5d), new Vector(0d,0d,1d))
        def rSame = new Ray(new Point(0d,1d,-5d), new Vector(0d,0d,1d))
        def rNone = new Ray(new Point(0d,2d,-5d), new Vector(0d,0d,1d))
        def rNeg1 = new Ray(new Point(0d,0d,0d), new Vector(0d,0d,1d))
        def rNeg2 = new Ray(new Point(0d,0d,5d), new Vector(0d,0d,1d))
        when:
        def intersectionsDiff = rDiff.computeIntersections(w)
        def intersectionsSame = rSame.computeIntersections(w)
        def intersectionsNone = rNone.computeIntersections(w)
        def intersectionsNeg1 = rNeg1.computeIntersections(w)
        def intersectionsNeg2 = rNeg2.computeIntersections(w)

        then:
        intersectionsDiff.size() == 2
        intersectionsDiff.get(0).getIntersectionTime() == 4.0d
        intersectionsDiff.get(1).getIntersectionTime() == 6.0d

        intersectionsSame.size() == 2
        intersectionsSame.get(0).getIntersectionTime() == 5.0d
        intersectionsSame.get(1).getIntersectionTime() == 5.0d

        intersectionsNone.size() == 0

        intersectionsNeg1.size() == 2
        intersectionsNeg1.get(0).getIntersectionTime() == -1.0d
        intersectionsNeg1.get(1).getIntersectionTime() == 1.0d

        intersectionsNeg2.size() == 2
        intersectionsNeg2.get(0).getIntersectionTime() == -6.0d
        intersectionsNeg2.get(1).getIntersectionTime() == -4.0d
    }


    def "ray transforms are correct"() {
        given:
        def r = new Ray(new Point(1,2,3), new Vector(0,1,0))
        def translate = Matrices.translation(3,4,5)
        def scale = Matrices.scale(2,3,4)

        when:
        def rTranslated = r.transform(translate)
        def rScaled = r.transform(scale)

        then:
        rTranslated.getOrigin() == new Point(4,6,8)
        rTranslated.getDirection() == new Vector(0,1,0)
        rScaled.getOrigin() == new Point(2,6,12)
        rScaled.getDirection() == new Vector(0,3,0)
    }


    def "ray-sphere intersections works with sphere transforms"() {
        given:
        def r = new Ray(new Point(0,0,-5), new Vector(0,0,1))
        def s = new Sphere()

        when:
        def wScaled = new World().addEntity(s.transform(Matrices.scale(2,2,2)))
        def wTranslated = new World().addEntity(s.transform(Matrices.translation(5,0,0)))

        then:
        r.computeIntersections(wScaled).get(0).getIntersectionTime() == 3
        r.computeIntersections(wScaled).get(1).getIntersectionTime() == 7
        r.getNumIntersections() == 2

        r.computeIntersections(wTranslated).size() == 0
    }


}
