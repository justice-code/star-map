package org.eddy.protocol.star.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.eddy.protocol.Data;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class DataEncoder extends MessageToByteEncoder<Data> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Data msg, ByteBuf out) throws Exception {
        byte[] bytes;
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(msg);
            oos.flush();
            bytes = bos.toByteArray();
        }

        out.writeBytes(bytes);
        ctx.flush();
    }
}
