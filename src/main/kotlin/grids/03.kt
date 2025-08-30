package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.shape.LineSegment

fun main() = application {
    configure {
        width = 900
        height = 450
    }

    program {
        extend(Screenshots()) {
            key = "s"
            folder = "screenshots/"
            async = false
        }

        val lineSegments = mutableListOf<LineSegment>()
        val target = Vector2(0.0, 0.0)
        val spacing = 100.0

        for (x in 0..6) {
            for (y in 0..3) {
                val pos = Vector2(
                    map(0.0, 6.0, 100.0, width.toDouble(), x.toDouble()),
                    map(0.0, 3.0, 100.0, height.toDouble(), y.toDouble())
                )
                val dir = (target - pos).normalized
                val end = target - dir * spacing
                lineSegments.add(LineSegment(pos, end))
            }
        }

        extend {
            drawer.clear(ColorRGBa.BLACK)
            drawer.stroke = ColorRGBa.WHITE
            drawer.strokeWeight = 1.0

            drawer.lineSegments(lineSegments)
        }
    }
}
