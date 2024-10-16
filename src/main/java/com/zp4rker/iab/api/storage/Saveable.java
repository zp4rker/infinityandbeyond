package com.zp4rker.iab.api.storage;

import com.zp4rker.iab.InfinityAndBeyond;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

public class Saveable {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void save() {
        InfinityAndBeyond.DATABASE.save(this);
    }

    public void delete() {
        InfinityAndBeyond.DATABASE.delete(this);
    }
}
