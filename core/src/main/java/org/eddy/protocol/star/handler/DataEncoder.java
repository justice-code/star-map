package org.eddy.protocol.star.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import org.eddy.protocol.Data;

public class DataEncoder extends MessageToByteEncoder<Data> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Data msg, ByteBuf out) throws Exception {

    }
}
