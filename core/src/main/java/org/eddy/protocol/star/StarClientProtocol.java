package org.eddy.protocol.star;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.star.handler.ClientReadHandler;
import org.eddy.protocol.star.handler.DataDecoder;
import org.eddy.protocol.star.handler.DataEncoder;
import org.eddy.url.URL;
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
        bootstrap.option(ChannelOption.SO_TIMEOUT, 3 * 1000);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3 * 1000);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
//                        .addLast("logging",new LoggingHandler(LogLevel.INFO))
                        .addLast("decoder", new DataDecoder())
                        .addLast("encoder", new DataEncoder())
                        .addLast("handler", new ClientReadHandler());
            }
        });
    }

    @Override
    public void connect(URL url) throws Exception {
        ChannelFuture future = bootstrap.connect(url.getHost(), url.getPort()).sync();
        channelMap.put(url.getAddress(), future.channel());
    }

    @Override
    public void close(URL url) {
        Channel channel = channelMap.get(url.getAddress());
        if (null != channel) {
            channel.close();
        }
    }

    @Override
    public void send(URL url, Data data) throws Exception{
        if (channelMap.containsKey(url.getAddress())) {
            connect(url);
        }

        Channel channel = channelMap.get(url.getAddress());
        if (! channel.isActive()) {
            connect(url);
            channel = channelMap.get(url.getAddress());
        }

        channel.writeAndFlush(data);
    }
}
