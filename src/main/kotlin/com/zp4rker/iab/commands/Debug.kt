package com.zp4rker.iab.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Default
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
    @Subcommand("draw cube")
    fun drawCube(player: Player, @Default("5") size: Int) {
        val start = player.location.toBlockLocation().add(2.0, 0.0, 0.0)
        val end = start.clone().add(size.toDouble(), size.toDouble(), size.toDouble())
        val task = IAB.runTaskTimer(period = TimeUnit.MILLISECONDS.toTicks(125)) {
            drawBox(start, end)
        }

        player.expect<PlayerMoveEvent>(IAB, predicate = { e ->
            e.to.x.let { it >= start.x && it <= end.x } &&
                    e.to.y.let { it >= start.y && it <= end.y } &&
                    e.to.z.let { it >= start.z && it <= end.z }
        }) {
            task.cancel()
        }
    }

    @Subcommand("draw prism")
    fun drawPrism(player: Player, @Default("5") width: Int, @Default("3") height: Int) {
        val start = player.location.toBlockLocation().add(2.0, 0.0, 0.0)
        val end = start.clone().add(width.toDouble(), height.toDouble(), width.toDouble())
        val task = IAB.runTaskTimer(period = TimeUnit.MILLISECONDS.toTicks(125)) {
            drawBox(start, end)
        }

        player.expect<PlayerMoveEvent>(IAB, predicate = { e ->
            e.to.x.let { it >= start.x && it <= end.x } &&
                    e.to.y.let { it >= start.y && it <= end.y } &&
                    e.to.z.let { it >= start.z && it <= end.z }
        }) {
            task.cancel()
        }
    }

    private fun drawBox(start: Location, end: Location) {
        drawLine(start, end.clone().set(start.x, start.y, end.z), Vector(0.0, 0.0, 0.1))
        drawLine(start, end.clone().set(end.x, start.y, start.z), Vector(0.1, 0.0, 0.0))
        drawLine(start, end.clone().set(start.x, end.y, start.z), Vector(0.0, 0.1, 0.0))

        drawLine(end, start.clone().set(end.x, end.y, start.z), Vector(0.0, 0.0, -0.1))
        drawLine(end, start.clone().set(start.x, end.y, end.z), Vector(-0.1, 0.0, 0.0))
        drawLine(end, start.clone().set(end.x, start.y, end.z), Vector(0.0, -0.1, 0.0))

        drawLine(start.clone().apply { x = end.x }, end.clone().set(end.x, end.y, start.z), Vector(0.0, 0.1, 0.0))
        drawLine(start.clone().apply { x = end.x }, end.clone().set(end.x, start.y, end.z), Vector(0.0, 0.0, 0.1))

        drawLine(start.clone().apply { z = end.z }, end.clone().set(start.x, end.y, end.z), Vector(0.0, 0.1, 0.0))
        drawLine(start.clone().apply { z = end.z }, end.clone().set(end.x, start.y, end.z), Vector(0.1, 0.0, 0.0))

        drawLine(start.clone().apply { y = end.y }, end.clone().set(end.x, end.y, start.z), Vector(0.1, 0.0, 0.0))
        drawLine(start.clone().apply { y = end.y }, end.clone().set(start.x, end.y, end.z), Vector(0.0, 0.0, 0.1))
    }

    private fun drawLine(start: Location, end: Location, vec: Vector = Vector(0.0, 0.1, 0.0)) {
        for (i in 0..(start.distance(end).toInt() * 10)) {
            val loc = start.clone().add(vec.clone().multiply(i))
            Particle.DUST.builder().apply {
                color(Color.RED)
                location(loc)
                receivers(10 + start.distance(end).toInt())
            }.spawn()
        }
    }

    private fun drawSquare(location: Location) {
        location.clone().apply {
            drawLine(this, clone().add(0.0, 0.0, 1.0), Vector(0.0, 0.0, 0.2))
            drawLine(this, clone().add(1.0, 0.0, 0.0), Vector(0.2, 0.0, 0.0))
            drawLine(add(1.0, 0.0, 0.0), clone().add(0.0, 0.0, 1.0), Vector(0.0, 0.0, 0.2))
            drawLine(add(-1.0, 0.0, 1.0), clone().add(1.0, 0.0, 0.0), Vector(0.2, 0.0, 0.0))
        }
    }
}