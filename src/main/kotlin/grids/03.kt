package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.extra.shapes.grid

fun main() = application {
    configure {
        width = 900
        height = 450
    }
    program {
        val colors = listOf(ColorRGBa.PINK, ColorRGBa.WHITE,
            ColorRGBa.WHITE, ColorRGBa.PINK.shade(0.5))
        val cells = drawer.bounds.grid(16, 6, 100.0, 100.0).flatten()
        extend {
            Random.resetState()
            drawer.clear(ColorRGBa.WHITE)
            cells.forEach { cell ->
                val brightness = Random.simplex(cell.center * 0.0035) + 0.5
                drawer.stroke = ColorRGBa.PINK.opacify(0.4)
                drawer.fill = ColorRGBa.PINK.shade(brightness)
                drawer.circle(cell.center, cell.width * 0.4)
                if (Random.bool()) {
                    drawer.fill = Random.pick(colors)
                    drawer.circle(cell.center, cell.width * 0.2)
                }
            }
        }
    }
}
