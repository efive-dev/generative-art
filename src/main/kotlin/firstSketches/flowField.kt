package firstSketches

import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.GHOST_WHITE
import org.openrndr.extra.color.presets.NAVAJO_WHITE
import org.openrndr.extra.color.presets.WHITE_SMOKE
import org.openrndr.extra.noise.simplex
import org.openrndr.math.Vector2
import kotlin.random.Random

enum class colors (color : ColorRGBa) {
    WHITE(ColorRGBa.WHITE),
    WHITESMOKE(ColorRGBa.WHITE_SMOKE),
    WHITEGHOST(ColorRGBa.GHOST_WHITE),
    NAVAJOWHITE(ColorRGBa.NAVAJO_WHITE)
}

fun randomColor() : colors {
    val randomNumber = Random.nextInt(1, 4)
    return when (randomNumber) {
        1 -> colors.WHITE
        2 -> colors.WHITESMOKE
        3 -> colors.NAVAJOWHITE
        4 -> colors.WHITEGHOST
        else -> colors.WHITE
    }
}

fun getColor(color: colors): ColorRGBa {
    return when(color) {
        colors.WHITE -> ColorRGBa.WHITE
        colors.WHITESMOKE -> ColorRGBa.WHITE_SMOKE
        colors.WHITEGHOST -> ColorRGBa.GHOST_WHITE
        colors.NAVAJOWHITE -> ColorRGBa.NAVAJO_WHITE
    }
}

fun main() = application {
    configure {
        width = 1920
        height = 1080
    }
    program {
        val particles = MutableList(500) {
            Vector2(Math.random() * width, Math.random() * height)
        }

        extend {
            drawer.clear(ColorRGBa.BLACK)

            val newParticles = particles.map { pos ->
                val angle = simplex(seconds.toInt(), pos.x * 0.01, pos.y * 0.01) * Math.PI * 2
                val velocity = Vector2(Math.cos(angle), Math.sin(angle)) * 2.0
                val newPos = pos + velocity

                val color = randomColor()
                drawer.fill = getColor(color)
                drawer.circle(newPos, 10.0)

                Vector2((newPos.x + width) % width, (newPos.y + height) % height)
            }

            for (i in particles.indices) {
                particles[i] = newParticles[i]
            }
        }
    }
}
