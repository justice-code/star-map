package org.eddy.protocol.star.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.eddy.protocol.Data;

public class ServerHandler extends SimpleChannelInboundHandler<Data> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data request) throws Exception {
        System.out.println(request);
        handleRequest(request);
        ctx.channel().writeAndFlush(new Data(request.getId(), true));
    }

    private void handleRequest(Data request) {
        //TODO handle message
    }
}
