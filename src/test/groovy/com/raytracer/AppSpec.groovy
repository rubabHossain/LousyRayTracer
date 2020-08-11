package com.raytracer

import com.raytracer.lib.primitives.Color
import com.raytracer.lib.primitives.Point
import com.raytracer.lib.world.LightSource
import com.raytracer.lib.world.Material
import com.raytracer.lib.primitives.Vector
import spock.lang.*

class AppSpec extends Specification {
    def "This fkn lighting function"() {
        given:
        def m = new Material()
        def position = new Point(0, 0, 0)

        when:
        def eyev = new Vector(0, 0, -1)
        def normalv = new Vector(0, 0, -1)
        def light = new LightSource(new Point(0, 0, -10), new Color(1, 1, 1))

        then:
        App.getColorAfterLighting(m, light, position, eyev, normalv) == new Color(1.9, 1.9, 1.9)

    }

    def "This fkn lighting function1"() {
        given:
        def m = new Material()
        def position = new Point(0, 0, 0)

        when:
        def eyev = new Vector(0, Math.sqrt(2)/2, Math.sqrt(2)/2)
        def normalv = new Vector(0, 0, -1)
        def light = new LightSource(new Point(0, 0, -10), new Color(1, 1, 1))

        then:
        App.getColorAfterLighting(m, light, position, eyev, normalv) == new Color(1, 1, 1)
    }

    def "This fkn lighting function2"() {
        given:
        def m = new Material()
        def position = new Point(0, 0, 0)

        when:
        def eyev = new Vector(0, 0, -1)
        def normalv = new Vector(0, 0, -1)
        def light = new LightSource(new Point(0, 10, -10), new Color(1, 1, 1))

        then:
        App.getColorAfterLighting(m, light, position, eyev, normalv) == new Color(0.7364, 0.7364, 0.7364)
    }


    def "This fkn lighting function3"() {
        given:
        def m = new Material()
        def position = new Point(0, 0, 0)

        when:
        def eyev = new Vector(0, -1 * Math.sqrt(2)/2, -1 * Math.sqrt(2)/2)
        def normalv = new Vector(0, 0, -1)
        def light = new LightSource(new Point(0, 10, -10), new Color(1, 1, 1))

        then:
        App.getColorAfterLighting(m, light, position, eyev, normalv) == new Color(1.6364, 1.6364, 1.6364)
    }

    def "This fkn lighting function4"() {
        given:
        def m = new Material()
        def position = new Point(0, 0, 0)

        when:
        def eyev = new Vector(0, 0, -1)
        def normalv = new Vector(0, 0, -1)
        def light = new LightSource(new Point(0, 0, 10), new Color(1, 1, 1))

        then:
        App.getColorAfterLighting(m, light, position, eyev, normalv) == new Color(0.1, 0.1, 0.1)
    }

}
