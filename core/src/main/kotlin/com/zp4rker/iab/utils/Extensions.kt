package com.zp4rker.iab.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

fun Component.raw() = JSONComponentSerializer.json().serialize(this)
fun Component.legacy() = LegacyComponentSerializer.legacy('§').serialize(this)