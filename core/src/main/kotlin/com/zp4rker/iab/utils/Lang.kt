package com.zp4rker.iab.utils

import com.zp4rker.bukkot.extensions.minimessage
import com.zp4rker.bukkot.yaml.YamlFile
import com.zp4rker.iab.PLUGIN

object Lang {
    private val langFile = YamlFile(PLUGIN, "lang.yml")

    fun getMessage(yamlPath: String) = langFile.getString(yamlPath, "Message not set in lang.yml")!!.minimessage()

    fun getMessages(yamlPath: String) = langFile.getStringList(yamlPath).map { it.minimessage() }
}