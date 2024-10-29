package com.zp4rker.iab.db

import com.j256.ormlite.field.FieldType
import com.j256.ormlite.field.SqlType
import com.j256.ormlite.field.types.StringType
import com.zp4rker.iab.api.Coord

class CoordPersister : StringType(SqlType.STRING, arrayOf(Coord::class.java)) {
    override fun javaToSqlArg(fieldType: FieldType?, javaObject: Any?): Any {
        val coord = javaObject as Coord?
        return coord?.let { "${it.x},${it.y},${it.z}" } ?: ""
    }

    override fun sqlArgToJava(fieldType: FieldType?, sqlArg: Any?, columnPos: Int): Any? {
        val str = sqlArg as String
        if (!str.contains(",")) return null

        val parts = str.split(",")
        val x = parts[0].toInt()
        val y = parts[1].toInt()
        val z = parts[2].toInt()
        return Coord(x, y, z)
    }

    companion object {
        private val singleton = CoordPersister()

        @JvmStatic
        fun getSingleton(): CoordPersister = singleton
    }
}