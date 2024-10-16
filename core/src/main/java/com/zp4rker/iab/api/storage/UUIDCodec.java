package com.zp4rker.iab.api.storage;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class UUIDCodec implements Codec<UUID> {
    @Override
    public UUID decode(BsonReader reader, DecoderContext decoderContext) {
        return UUID.fromString(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, UUID value, EncoderContext encoderContext) {
        writer.writeString(value.toString());
    }

    @Override
    public Class<UUID> getEncoderClass() {
        return UUID.class;
    }
}
