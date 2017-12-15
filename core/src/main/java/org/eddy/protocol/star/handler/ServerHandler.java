package org.eddy.protocol.star.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.eddy.constant.Constants;
import org.eddy.protocol.Data;
import org.eddy.protocol.DataContext;
import org.eddy.queue.ServerQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends SimpleChannelInboundHandler<Data> {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data request) throws Exception {
        handleRequest(request);
        Data data = new Data(request.getId(), true);
        DataContext context = new DataContext(request.getContext().getUrl(), Constants.executor, request.getContext().getCaller());
        data.setContext(context);
        ctx.channel().writeAndFlush(data);
    }

    private void handleRequest(Data request) {
        try {
            request.getContext().setSide(Constants.executor);
            ServerQueue.put(request);
        } catch (InterruptedException e) {
            logger.error("ServerQueue put error", e);
        }
    }
}
