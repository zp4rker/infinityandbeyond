package com.zp4rker.iab.utils

import net.kyori.adventure.text.minimessage.MiniMessage

val MM = MiniMessage.miniMessage()

object Lang {
    private val langFile = YamlFile("lang.yml")

    fun getMessage(yamlPath: String) = MM.deserialize(
        langFile.yaml.getString(yamlPath, "Message not set in lang.yml")!!
    )
}