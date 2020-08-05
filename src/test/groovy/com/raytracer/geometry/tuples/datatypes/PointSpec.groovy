package com.raytracer.geometry.tuples.datatypes
import spock.lang.*

class PointSpec extends Specification {
    def "A Point is a 4d tuple with last coordinate as 1"() {
        when:
        def p = new Point(4.3, -4.2, 3.14)
        def w = p.getElements().get(3)
        then:
        w == 1
    }
}
