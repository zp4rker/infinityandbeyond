package com.zp4rker.iab.api.storage;

import com.zp4rker.iab.api.CelestialLocation;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class CelestialLocationCodec implements Codec<CelestialLocation> {
    @Override
    public CelestialLocation decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readName("x");
        int x = reader.readInt32();
        reader.readName("y");
        int y = reader.readInt32();
        reader.readEndDocument();
        return new CelestialLocation(x, y);
    }

    @Override
    public void encode(BsonWriter writer, CelestialLocation value, EncoderContext encoderContext) {
        if (value != null) {
            writer.writeStartDocument();
            writer.writeName("x");
            writer.writeInt32(value.getX());
            writer.writeName("y");
            writer.writeInt32(value.getY());
            writer.writeEndDocument();
        }
    }

    @Override
    public Class<CelestialLocation> getEncoderClass() {
        return CelestialLocation.class;
    }
}
