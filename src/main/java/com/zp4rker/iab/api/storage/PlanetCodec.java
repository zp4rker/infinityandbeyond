package com.zp4rker.iab.api.storage;

import com.zp4rker.iab.api.Planet;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class PlanetCodec implements Codec<Planet> {
    @Override
    public Planet decode(BsonReader reader, DecoderContext decoderContext) {
        ObjectId id = reader.readObjectId();
        return null;
    }

    @Override
    public void encode(BsonWriter writer, Planet value, EncoderContext encoderContext) {
        writer.writeObjectId(value.getId());
    }

    @Override
    public Class<Planet> getEncoderClass() {
        return Planet.class;
    }
}
