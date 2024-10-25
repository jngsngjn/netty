package basic.client;

import basic.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChannelOptionTestClient {

    private final int port;

    public ChannelOptionTestClient(int port) {
        this.port = port;
    }

    public void connect() throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                    .option(ChannelOption.SO_SNDBUF, 1048576)
//                    .option(ChannelOption.SO_RCVBUF, 1048576)
//                    .option(ChannelOption.SO_LINGER, 10)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast(new ClientHandler());
                        }
                    });

            ChannelFuture cf = bootstrap.connect("localhost", port).sync();
            boolean isConnected = false;
            isConnected = true;
            while (isConnected) {
                Thread.sleep(1000);
            }
            cf.channel().closeFuture().sync();
            System.out.println("closed connection from server");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}