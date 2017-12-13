package org.eddy.protocol.star.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.eddy.protocol.Data;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class ServerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readCount = in.readableBytes();

        if (readCount < 1) {
            return;
        }

        byte[] responseData = new byte[readCount];
        in.readBytes(responseData);

        Data request;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(responseData); ObjectInputStream ois = new ObjectInputStream(bis)) {
            request = (Data) ois.readObject();
        }

        //TODO 对请求数据进行接收
        ctx.channel().writeAndFlush(new Data(request.getId(),true));
    }
}
