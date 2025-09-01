package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.extra.shapes.grid
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.math.map
import kotlin.math.sin

fun main() = application {
    configure {
        width = 900
        height = 450
    }

    program {

        extend(ScreenRecorder()) {
            frameRate = 60
            maximumDuration = 5.0
            outputFile = "recordings/grids_04.mp4"
        }

        val cols = 100
        val rows = 15
        val cells = drawer.bounds.grid(cols, rows, 0.0, 0.0).flatten()

        extend {
            drawer.clear(ColorRGBa.WHITE)
            drawer.stroke = null
            cells.forEach { cell ->
                val amount = Random.perlin(cell.x * 0.015, cell.y * 0.015)
                drawer.fill = ColorRGBa.BLUE.opacify(amount)
                val rectH = map(-1.0, 1.0,
                    (-rows).toDouble(), rows.toDouble(),
                    sin((cell.y + cell.x) + frameCount * 0.0575)
                )
                drawer.rectangle(cell.x, cell.y, rectH)
            }
        }
    }
}