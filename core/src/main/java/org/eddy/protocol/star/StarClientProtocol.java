package org.eddy.protocol.star;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.star.handler.ClientReadHandler;
import org.eddy.protocol.star.handler.DataDecoder;
import org.eddy.protocol.star.handler.DataEncoder;
import org.springframework.stereotype.Component;

@Component
public class StarClientProtocol implements ClientProtocol{

    @Override
    public void open() {
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
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
    public void connect() {

    }

    @Override
    public void close() {

    }

    @Override
    public void send(Data data) {

    }
}
