package basic.server.tcp;

import basic.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class EchoServer {

    // 클라이언트의 요청을 받을 포트
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        // 클라이언트의 연결 요청 처리
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

        // 데이터 입출력 처리
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 서버를 설정하고 시작하기 위한 클래스
            ServerBootstrap sbs = new ServerBootstrap();

            // parent group, child group
            sbs.group(bossGroup, workerGroup)

                    // 서버가 사용할 채널 유형 설정 (Non-blocking IO)
                    .channel(NioServerSocketChannel.class)

                    // 새로운 클라이언트가 연결될 때마다 각 클라이언트 소켓에 대해 처리할 핸들러 설정
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 클라이언트가 연결될 때 호출되며, 각 클라이언트 연결마다 SocketChannel이 생성
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            // 바이트 데이터를 문자열로 변환하는 디코더. 클라이언트로부터 받은 데이터를 문자열로 변환
                            pipeline.addLast(new StringDecoder());

                            // 서버에서 클라이언트로 보낼 데이터를 문자열에서 바이트로 변환하는 인코더. 네트워크에서 전송 가능한 형식으로 데이터를 변환
                            pipeline.addLast(new StringEncoder());

                            // 직접 정의한 핸들러 등록 (데이터를 처리하고 응답을 보내는 역할)
                            pipeline.addLast(new EchoServerHandler());
//                            pipeline.addLast(new BufferTranslator());
                        }
                    });

            // 클라이언트 연결 요청 대기
            ChannelFuture cf = sbs.bind(port).sync();

            // 서버가 종료될 때까지 대기
            cf.channel().closeFuture().sync();
        } finally {
            // 서버가 종료될 때 사용 중인 스레드를 안전하게 종료하고 자원을 정리
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}