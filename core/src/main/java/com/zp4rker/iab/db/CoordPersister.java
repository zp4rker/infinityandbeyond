package com.zp4rker.iab.db;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import com.zp4rker.iab.api.Coord;

public class CoordPersister extends StringType {
    public CoordPersister() {
        super(SqlType.STRING, new Class[]{Coord.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        Coord coord = (Coord) javaObject;
        return coord.getX() + "," + coord.getY() + "," + coord.getZ();
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String str = (String) sqlArg;
        if (!str.contains(",")) return null;

        String[] parts = str.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        return new Coord(x, y, z);
    }

    private static final CoordPersister singleton = new CoordPersister();

    public static CoordPersister getSingleton() {
        return singleton;
    }
}
