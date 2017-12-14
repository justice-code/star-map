package org.eddy.protocol.star.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.eddy.future.FutureHolder;
import org.eddy.protocol.Data;

public class ClientReadHandler extends SimpleChannelInboundHandler<Data> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data msg) throws Exception {
        FutureHolder.receive(msg);
    }
}
