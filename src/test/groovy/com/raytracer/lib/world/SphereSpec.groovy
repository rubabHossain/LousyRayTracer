package com.raytracer.lib.world

import com.raytracer.lib.linalg.Matrices
import com.raytracer.lib.primitives.Point
import com.raytracer.lib.primitives.Vector
import spock.lang.Specification

class SphereSpec extends Specification{

    def "Untransformed sphere has correct Normals" () {
        when:
        def s = new Sphere()
        def sq3Over3 = Math.sqrt(3)/3
        then:
        s.getNormalAt(new Point(0,0,1)) == new Vector(0,0,1)
        s.getNormalAt(new Point(0,1,0)) == new Vector(0,1,0)
        s.getNormalAt(new Point(1,0,0)) == new Vector(1,0,0)
        s.getNormalAt(new Point(sq3Over3,sq3Over3,sq3Over3)) == new Vector(sq3Over3,sq3Over3,sq3Over3)
        s.getNormalAt(new Point(2,0,0)).normalize() == new Vector(1,0,0)
    }

    def "Transformed sphere has correct normals"() {
        given:
        def s = new Sphere()
        def translate = Matrices.translation(0,1,0)
        def rotateThenScale = Matrices.rotateZ(Math.PI/5).scale(1,0.5,1)
        when:
        def translatedSphere = s.transform(translate)
        def scaledAndRotatedSphere = s.transform(rotateThenScale)
        then:
        translatedSphere.getNormalAt(new Point(0, 1.70711, -0.70711)) == new Vector(0, 0.70711, -0.70711)
        scaledAndRotatedSphere.getNormalAt(new Point(0, Math.sqrt(2)/2, -1*Math.sqrt(2)/2)) == new Vector(0, 0.97014, -0.24254)

    }
}
