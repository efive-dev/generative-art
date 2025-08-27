package firstSketches

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.WHEAT
import org.openrndr.extra.color.presets.WHITE_SMOKE
import org.openrndr.extra.noise.simplex
import org.openrndr.math.Vector2
import kotlin.math.PI
import kotlin.math.sin

fun main() = application {
    configure {
        width = 1920
        height = 1080
    }

    program {
        val numLines = 10
        val colors = listOf(
            ColorRGBa.WHITE,
            ColorRGBa.WHITE_SMOKE,
            ColorRGBa.WHEAT,
        )

        val horizontalLines = mutableListOf<Pair<List<Vector2>, ColorRGBa>>()
        val diagonalLines = mutableListOf<List<Vector2>>()

        for (i in 0 until numLines) {
            val yBase = height * (i + 1) / (numLines + 1.0)
            val points = mutableListOf<Vector2>()

            val numPoints = 150
            val noiseScale = 0.01
            val amplitude = 60.0
            val offsetSeed = i * 100

            for (j in 0 until numPoints) {
                val x = width * j / (numPoints - 1.0)
                val normalizedX = j / (numPoints - 1.0)

                val noise1 = simplex(offsetSeed, normalizedX * noiseScale * 100) * amplitude
                val noise2 = simplex(offsetSeed + 1000, normalizedX * noiseScale * 200) * amplitude * 0.5
                val noise3 = simplex(offsetSeed + 2000, normalizedX * noiseScale * 400) * amplitude * 0.25

                val y = yBase + noise1 + noise2 + noise3
                points.add(Vector2(x, y))
            }

            horizontalLines.add(Pair(points, colors[i % colors.size].opacify(0.8)))
        }

        for (i in 0 until 6) {
            val points = mutableListOf<Vector2>()
            val numPoints = 100
            val noiseScale = 0.008
            val amplitude = 40.0
            val offsetSeed = i * 200 + 5000

            for (j in 0 until numPoints) {
                val progress = j / (numPoints - 1.0)
                val x = width * progress
                val yBase = height * 0.2 + (height * 0.6) * progress + sin(progress * PI * 2) * 30

                val noise = simplex(offsetSeed, x * noiseScale) * amplitude
                val y = yBase + noise
                points.add(Vector2(x, y))
            }

            diagonalLines.add(points)
        }

        extend {
            drawer.clear(ColorRGBa.BLACK)
            drawer.fill = null

            drawer.strokeWeight = 2.0
            for ((points, color) in horizontalLines) {
                drawer.stroke = color
                drawer.lineStrip(points)
            }

            drawer.strokeWeight = 1.0
            drawer.stroke = ColorRGBa.WHITE.opacify(0.3)
            for (points in diagonalLines) {
                drawer.lineStrip(points)
            }
        }
    }
}