package com.zp4rker.iab.api.storage;

import com.zp4rker.iab.InfinityAndBeyond;

public class Saveable {
    public void save() {
        InfinityAndBeyond.DATABASE.save(this);
    }

    public void delete() {
        InfinityAndBeyond.DATABASE.delete(this);
    }
}
