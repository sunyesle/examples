package com.sunyesle.spring_boot_bulk_insert.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtil {

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
