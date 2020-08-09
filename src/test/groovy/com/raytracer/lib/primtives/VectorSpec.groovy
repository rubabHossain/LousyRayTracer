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
}
