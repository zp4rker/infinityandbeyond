package com.zp4rker.iab.db

import com.j256.ormlite.field.FieldType
import com.j256.ormlite.field.SqlType
import com.j256.ormlite.field.types.StringType
import com.zp4rker.iab.api.XYCoord

class XYCoordPersister : StringType(SqlType.STRING, arrayOf(XYCoord::class.java)) {
    override fun javaToSqlArg(fieldType: FieldType?, javaObject: Any?): Any {
        val xycoord = javaObject as XYCoord?
        return xycoord?.let { "${it.x},${it.y}" } ?: ""
    }

    override fun sqlArgToJava(fieldType: FieldType?, sqlArg: Any?, columnPos: Int): Any? {
        val str = sqlArg as String
        if (!str.contains(",")) return null

        val parts = str.split(",")
        val x = parts[0].toInt()
        val y = parts[1].toInt()
        return XYCoord(x, y)
    }

    companion object {
        private val singleton = XYCoordPersister()

        @JvmStatic
        fun getSingleton(): XYCoordPersister = singleton
    }
}