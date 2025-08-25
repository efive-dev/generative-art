package firstSketches

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random
import org.openrndr.math.Vector2
import org.openrndr.shape.Segment

data class Circle(var radius: Double, var x: Double, var y: Double)

fun generateRandomCircle(width: Int, height: Int): Circle {
    val radius = random(5.0, 50.0)
    val x = random(radius, width - radius)
    val y = random(radius, height - radius)
    return Circle(radius, x, y)
}

fun generateRandomLine(width: Int, height: Int): Segment {
    val start = Vector2(random(0.0, width.toDouble()), random(0.0, height.toDouble()))
    val end = Vector2(random(0.0, width.toDouble()), random(0.0, height.toDouble()))
    return Segment(start, end)
}

fun main() = application {
    configure {
        width = 1920
        height = 1080
        title = "Random Circles + Lines"
    }
    program {
        val circles = List(20) { generateRandomCircle(width, height) }
        val segments = List(10) { generateRandomLine(width, height) }

        extend {
            drawer.clear(ColorRGBa.BLACK)

            drawer.fill = ColorRGBa.WHITE
            drawer.stroke = null
            circles.forEach {
                drawer.circle(it.x, it.y, it.radius)
            }

            drawer.stroke = ColorRGBa.WHITE
            drawer.strokeWeight = 2.0
            drawer.segments(segments)
        }
    }
}
