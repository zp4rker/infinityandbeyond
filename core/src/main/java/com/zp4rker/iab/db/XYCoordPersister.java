package com.zp4rker.iab.db;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import com.zp4rker.iab.api.XYCoord;

import java.sql.SQLException;

public class XYCoordPersister extends StringType {
    public XYCoordPersister() {
        super(SqlType.STRING, new Class[]{XYCoord.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        XYCoord coord = (XYCoord) javaObject;
        return coord.getX() + "," + coord.getY();
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String str = (String) sqlArg;
        if (!str.contains(",")) return null;

        String[] parts = str.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new XYCoord(x, y);
    }

    private static final XYCoordPersister singleton = new XYCoordPersister();

    public static XYCoordPersister getSingleton() {
        return singleton;
    }
}
