package com.zp4rker.iab.api.db;

import com.zp4rker.iab.IABCore;

public class Saveable {
    public void save() {
        IABCore.DATABASE.save(this);
    }

    public void delete() {
        IABCore.DATABASE.delete(this);
    }
}
