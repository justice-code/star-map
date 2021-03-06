package org.eddy.protocol.star;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.eddy.constant.Constants;
import org.eddy.future.FutureHolder;
import org.eddy.future.StarFuture;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.DataContext;
import org.eddy.protocol.star.handler.ClientIdleHandler;
import org.eddy.protocol.star.handler.ClientReadHandler;
import org.eddy.url.URL;
import org.eddy.util.NetUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("starClient")
public class StarClientProtocol implements ClientProtocol{

    private Bootstrap bootstrap;
    private Map<String, Channel> channelMap = new HashMap<>();

    @Override
    public void open() {
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoop);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3 * 1000);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
//                        .addLast("logging",new LoggingHandler(LogLevel.INFO))
                        .addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader()))) // in 1
                        .addLast("handler", new ClientReadHandler()) // in 2
                        .addLast("encoder", new ObjectEncoder())// out 3
                        .addLast("idleStateHandler", new IdleStateHandler(0, 1, 0))
                        .addLast(new ClientIdleHandler());

            }
        });
    }

    @Override
    public void connect(URL url) throws Exception {
        ChannelFuture future = bootstrap.connect(url.getHost(), url.getPort()).sync();
        channelMap.put(url.getAddress(), future.channel());
    }

    @Override
    public void close() {
        channelMap.values().stream().filter(channel -> null != channel && channel.isActive()).forEach(channel -> channel.close());
    }

    @Override
    public StarFuture send(URL url, Data data) throws Exception{
        if (! channelMap.containsKey(url.getAddress())) {
            connect(url);
        }

        Channel channel = channelMap.get(url.getAddress());
        if (! channel.isActive()) {
            connect(url);
            channel = channelMap.get(url.getAddress());
        }
        channel.writeAndFlush(data);
        return FutureHolder.createFuture(data);
    }
}
