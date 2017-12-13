package org.eddy.protocol.star;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.eddy.protocol.Data;
import org.eddy.protocol.ServerProtocol;
import org.eddy.protocol.star.handler.DataDecoder;
import org.eddy.protocol.star.handler.DataEncoder;
import org.eddy.protocol.star.handler.ServerDecoder;
import org.eddy.protocol.star.handler.ServerHandler;
import org.eddy.url.URL;
import org.springframework.stereotype.Component;

@Component("starServer")
public class StarServerProtocol implements ServerProtocol {

    private ServerBootstrap serverBootstrap;

    @Override
    public void openServer(URL url) throws Exception{
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        EventLoopGroup workLoop = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoop, workLoop);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childOption(ChannelOption.SO_TIMEOUT, 3 * 1000);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>(){

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("decoder", new ServerDecoder())
                        .addLast("encoder", new DataEncoder());
//                        .addLast("handler", new ServerHandler());
            }
        });
    }
}
