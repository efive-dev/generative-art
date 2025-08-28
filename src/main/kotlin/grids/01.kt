package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.noise.Random
import org.openrndr.math.Vector2
import org.openrndr.math.map

fun main() = application {
    configure {
        width = 900
        height = 450
    }
    program {
        val colors = listOf(ColorRGBa.fromHex("FFC15E"), ColorRGBa.fromHex("F7B05B"),
            ColorRGBa.fromHex("F7934C"))
        extend(Screenshots()) {
            key="s"
            folder="screenshots/"
            async=false
        }
        extend {
            Random.resetState()
            drawer.clear(ColorRGBa.BLACK)
            for (x in 0..15) {
                for (y in 0..5) {
                    val pos = Vector2(
                        map(0.0, 15.0, 100.0, width - 100.0, x * 1.0),
                        map(0.0, 5.0, 100.0, height - 100.0, y * 1.0)
                    )
                    drawer.stroke = ColorRGBa.fromHex("B8B08D")
                    drawer.strokeWeight = 3.0
                    val amount = Random.simplex(pos.x * 0.004, pos.y * 0.003) + 0.5
                    drawer.fill = ColorRGBa.fromHex("CC5803").shade(amount)
                    drawer.circle(pos, 20.0)
                    if (Random.bool()) {
                        drawer.fill = Random.pick(colors)
                        drawer.circle(pos, 10.0)
                    }
                }
            }
        }
    }
}