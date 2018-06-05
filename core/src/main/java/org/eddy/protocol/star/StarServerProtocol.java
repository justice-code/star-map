package org.eddy.protocol.star;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.eddy.protocol.Data;
import org.eddy.protocol.ServerProtocol;
import org.eddy.protocol.star.handler.ServerHandler;
import org.eddy.url.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("starServer")
public class StarServerProtocol implements ServerProtocol {

    private static final Logger logger = LoggerFactory.getLogger(StarServerProtocol.class);

    private ServerBootstrap serverBootstrap;
    private Channel serverChannel;

    @Override
    public void openServer(URL url) throws Exception{
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        EventLoopGroup workLoop = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoop, workLoop);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>(){

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))) // in 1
                        .addLast("handler", new ServerHandler()) // in 2
                        .addLast("encoder", new ObjectEncoder()); // out 3

            }
        });
        serverChannel = serverBootstrap.bind(url.getPort()).sync().channel();
        logger.info("start server at:" + url.getPort());
    }

    @Override
    public void response(Data response) {
        ServerHandler.ResponseHolder.get(response).writeAndFlush(response);
    }

    @Override
    public void close() {
        Optional.ofNullable(serverChannel).ifPresent(channel -> channel.close());
    }
}
