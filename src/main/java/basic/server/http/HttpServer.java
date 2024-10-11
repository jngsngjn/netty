package basic.server.http;

import basic.handler.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServer {

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1); // 클라이언트 연결 처리
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); // 데이터 처리

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            ChannelPipeline pipeline = sc.pipeline();

                            // HTTP 디코더 및 인코더
                            pipeline.addLast(new HttpServerCodec());

                            // HTTP 객체를 읽을 수 있는 핸들러 (HTTP 메시지 조각들을 하나로 모음) - 64KB까지의 HTTP 요청/응답을 하나의 객체로 집계
                            pipeline.addLast(new HttpObjectAggregator(65536));

                            // HTTP 요청을 처리할 핸들러
                            pipeline.addLast(new HttpServerHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("HTTP Server started on port: " + port);
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}