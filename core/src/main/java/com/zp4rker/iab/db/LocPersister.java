package com.zp4rker.iab.db;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;
import com.zp4rker.iab.api.Loc;

public class LocPersister extends StringType {
    public LocPersister() {
        super(SqlType.STRING, new Class[]{Loc.class});
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        Loc loc = (Loc) javaObject;
        return loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        String str = (String) sqlArg;
        if (!str.contains(",")) return null;

        String[] parts = str.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        return new Loc(x, y, z);
    }

    private static final LocPersister singleton = new LocPersister();

    public static LocPersister getSingleton() {
        return singleton;
    }
}
