package com.zp4rker.iab.api.storage;

import com.zp4rker.iab.api.PlanetaryLocation;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class PlaneteryLocationCodec implements Codec<PlanetaryLocation> {
    @Override
    public PlanetaryLocation decode(BsonReader reader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter writer, PlanetaryLocation value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeName("planet");
        writer.writeObjectId(value.getPlanet().getId());
        writer.writeName("position");
        writer.writeStartDocument();
        writer.writeName("x");
        writer.writeInt32(value.getX());
        writer.writeName("y");
        writer.writeInt32(value.getY());
        writer.writeName("z");
        writer.writeInt32(value.getZ());
        writer.writeEndDocument();
        writer.writeEndDocument();
    }

    @Override
    public Class<PlanetaryLocation> getEncoderClass() {
        return PlanetaryLocation.class;
    }
}
