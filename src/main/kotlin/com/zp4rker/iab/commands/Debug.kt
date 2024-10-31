package com.zp4rker.iab.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player

@CommandAlias("debug")
class Debug : BaseCommand() {
    @Subcommand("drawborder")
    fun drawBorder(player: Player) {
        val start = player.eyeLocation.toBlockLocation()
        val end = player.location.toBlockLocation()
        val vec = end.clone().subtract(start).toVector()

        for (i in 0..vec.length().toInt()) {
            val loc = start.clone().add(vec.clone().multiply(i / vec.length()))
            Particle.DUST.builder().apply {
                location(loc)
                color(Color.RED)
                receivers(10)
            }.spawn()
        }
    }
}