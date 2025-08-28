package grids

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.noise.Random
import org.openrndr.ffmpeg.ScreenRecorder
import org.openrndr.math.Vector2

data class Cell(val pos: Vector2, val color: ColorRGBa)


fun main() = application {
    configure {
        width = 900
        height = 450
    }

    program {
        val colors = listOf(
            ColorRGBa.fromHex("#324040"), ColorRGBa.fromHex("#5A7373"),
            ColorRGBa.fromHex("#95BFBF"), ColorRGBa.fromHex("#FFFFFF"),
        )

        extend(Screenshots()) {
            key = "s"
            folder = "screenshots/"
            async = false
        }

        extend(ScreenRecorder()) {
            frameRate = 60
            maximumDuration = 5.0
            outputFile = "recordings/02.mp4"
        }

        val cols = 10
        val rows = 5
        val margin = 50.0
        val cellWidth = (width - 2 * margin) / cols
        val cellHeight = (height - 2 * margin) / rows

        val cells = List(cols) { x ->
            List(rows) { y ->
                val pos = Vector2(
                    margin + x * cellWidth,
                    margin + y * cellHeight
                )
                val color = Random.pick(colors)
                Cell(pos, color)
            }
        }.flatten()

        extend {
            drawer.clear(ColorRGBa.BLACK)

            for ((pos, color) in cells) {

                drawer.isolated {
                    drawer.fill = color
                    drawer.translate(pos.x + cellWidth * 0.45, pos.y + cellHeight * 0.45)


                    val noise = Random.simplex(pos.x * 0.01, pos.y * 0.01, seconds * 0.2) * 3.0
                    val angle = noise * 90.0

                    drawer.rotate(angle)
                    drawer.rectangle(
                        -cellWidth * 0.3375,
                        -cellHeight * 0.3375,
                        cellWidth * 0.75,
                        cellHeight * 0.75
                    )
                }
            }
        }
    }
}