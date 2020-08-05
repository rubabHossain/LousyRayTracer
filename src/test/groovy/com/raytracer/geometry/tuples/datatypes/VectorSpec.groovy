package com.raytracer.geometry.tuples.datatypes
import spock.lang.*


class VectorSpec extends Specification {
     def "A Vector is a 4d tuple with last coordinate as 0"() {
         when:
         def v = new Vector(4.3, -4.2, 3.14);

         then:
         v.getElements().get(3) == 0;
     }
}
