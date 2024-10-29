package com.zp4rker.iab.utils

import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.bukkot.yaml.YamlFile
import com.zp4rker.iab.IAB
import net.kyori.adventure.text.Component

object Lang {
    private val langFile = YamlFile(IAB, "lang.yml")

    fun getMessage(yamlPath: String, replacements: Map<String, String> = emptyMap()): Component {
        return langFile.getString(yamlPath, "Message not set in lang.yml")!!.replaceText(replacements).minimessage()
    }

    fun getMessages(yamlPath: String, replacements: Map<String, String> = emptyMap()): List<Component> {
        return langFile.getStringList(yamlPath).map { it.replaceText(replacements).minimessage() }
    }

    private fun String.replaceText(map: Map<String, String>): String {
        var text = this
        map.forEach { (key, value) -> text = text.replace(key, value) }
        return text
    }
}