package basic.server.tcp;

import java.nio.charset.StandardCharsets;

import basic.handler.server.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class MyServer {
    private final int port;

    public MyServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            ChannelPipeline pipeline = sc.pipeline();
                            sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(",", StandardCharsets.UTF_8)));
                            pipeline.addLast(new IdleStateHandler(5, 0, 0));
                            // pipeline.addLast(new IdleStateHandler(15, 0, 0));
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.bind(port).sync();
            System.out.println("Server started on port " + port);
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}