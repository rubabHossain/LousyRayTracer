package com.raytracer.lib.primtives

import spock.lang.Specification
import com.raytracer.lib.primitives.Vector

class VectorSpec extends Specification {

    def "Vector x Vector CROSS PRODUCT"() {
        when:
        def v1 = new Vector(1,2,3)
        def v2 = new Vector(2,3,4)
        then:
        v1.crossProduct(v2) == new Vector(-1,2,-1)
        v2.crossProduct(v1) == new Vector(1,-2,1)
    }

    def "Vector reflection"() {
        when:
        def v1 = new Vector(0,-1,0)
        def normal1 = new Vector(Math.sqrt(2)/2, Math.sqrt(2)/2, 0)

        def v2 = new Vector(1,-1,0)
        def normal2 = new Vector(0,1,0)

        then:
        v1.reflect(normal1) == new Vector(1,0,0)
        v2.reflect(normal2) == new Vector(1,1,0)
    }


}
