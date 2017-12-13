package org.eddy.protocol.star.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class DataDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readCount = in.readableBytes();

        if (readCount < 1) {
            return;
        }

        byte[] responseData = new byte[readCount];
        in.readBytes(responseData);

        try(ByteArrayInputStream bis = new ByteArrayInputStream(responseData); ObjectInputStream ois = new ObjectInputStream(bis)) {
            out.add(ois.readObject());
        }
    }
}
