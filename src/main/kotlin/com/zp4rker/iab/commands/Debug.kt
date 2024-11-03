package com.zp4rker.iab.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import com.zp4rker.bukkot.extensions.runTaskTimer
import com.zp4rker.bukkot.extensions.toTicks
import com.zp4rker.bukkot.listener.expect
import com.zp4rker.iab.IAB
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import java.util.concurrent.TimeUnit

@CommandAlias("debug")
object Debug : BaseCommand() {
    @Subcommand("drawborder")
    fun drawBorder(player: Player) {
        fun drawLine(start: Location, end: Location, vec: Vector) {
            for (i in 0..(start.distance(end).toInt() * 5)) {
                val loc = start.clone().add(vec.clone().multiply(i))
                Particle.DUST.builder().apply {
                    color(Color.RED)
                    location(loc)
                    receivers(10)
                }.spawn()
            }
        }

        fun drawSquare(location: Location) {
            location.clone().apply {
                drawLine(this, clone().add(0.0, 0.0, 1.0), Vector(0.0, 0.0, 0.2))
                drawLine(this, clone().add(1.0, 0.0, 0.0), Vector(0.2, 0.0, 0.0))
                drawLine(add(1.0, 0.0, 0.0), clone().add(0.0, 0.0, 1.0), Vector(0.0, 0.0, 0.2))
                drawLine(add(-1.0, 0.0, 1.0), clone().add(1.0, 0.0, 0.0), Vector(0.2, 0.0, 0.0))
            }
        }

        val loc = player.location.toBlockLocation().add(2.0, 0.0, 0.0)
        val task = IAB.runTaskTimer(period = TimeUnit.MILLISECONDS.toTicks(125)) {
            drawSquare(loc)
            for (x in 0..1) {
                for (z in 0..1) {
                    val start = loc.clone().add(x.toDouble(), 0.0, z.toDouble())
                    val end = start.clone().add(0.0, 5.0, 0.0)
                    val vec = Vector(0.0, 0.2, 0.0)
                    drawLine(start, end, vec)
                }
            }
            drawSquare(loc.clone().add(0.0, 5.0, 0.0))
        }

        player.expect<PlayerMoveEvent>(IAB, predicate = { it.to.distance(loc) <= 1.0 }) {
            task.cancel()
        }
    }
}