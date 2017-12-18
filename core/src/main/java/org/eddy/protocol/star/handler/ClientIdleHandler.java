package org.eddy.protocol.star.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientIdleHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state() == IdleState.WRITER_IDLE) {
            ctx.writeAndFlush("heartbeat").addListener((ChannelFutureListener) future -> {
                if (! future.isSuccess()) {
                    future.channel().close();
                }
            });
        }
    }
}
