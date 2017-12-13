package org.eddy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.eddy.protocol.Data;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Objecttest {

    @Test
    public void test() throws IOException {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(new Data());
            oos.flush();
            byte[] bytes = bos.toByteArray();
            ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        }
    }
}
