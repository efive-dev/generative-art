package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.BROWN
import org.openrndr.extra.color.presets.ORANGE
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
            outputFile = "recordings/grids_05.mp4"
        }

        val cols = 100
        val rows = 15
        val marginX = 100.0
        val marginY = 100.0
        val cells = drawer.bounds
            .offsetEdges(-marginX, -marginY)
            .grid(cols, rows)
            .flatten()

        val colors = listOf(ColorRGBa.ORANGE, ColorRGBa.RED, ColorRGBa.BROWN)

        val cellColors = cells.map { Random.pick(colors) }

        extend {
            drawer.clear(ColorRGBa.BLACK)
            drawer.stroke = null

            cells.forEachIndexed { i, cell ->
                val amount = Random.perlin(cell.x * 0.015, cell.y * 0.015)
                val alpha = map(0.0, 1.0, 0.3, 1.0, amount)
                drawer.fill = cellColors[i].opacify(alpha)

                val rectH = map(
                    -1.0, 1.0,
                    -rows.toDouble(), rows.toDouble(),
                    sin((cell.x + cell.y) + frameCount * 0.075)
                )
                drawer.rectangle(cell.x, cell.y, 5.0, rectH)
            }
        }
    }
}
