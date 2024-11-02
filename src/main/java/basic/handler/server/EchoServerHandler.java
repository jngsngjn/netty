package basic.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// ChannelInboundHandlerAdapter: 네티의 기본 핸들러로서, 수신된 데이터를 처리하는 데 사용
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LogManager.getLogger(EchoServerHandler.class);

    // 클라이언트가 데이터를 서버로 전송했을 때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        // 응답을 5초 지연시킴
        System.out.println("[SERVER] 응답을 10초 지연...");
        Thread.sleep(10000);

        String receivedMessage = msg.toString().trim();
        if (receivedMessage.equalsIgnoreCase("exit")) {
            log.info("Received [EXIT] command, closing connection...");
            ctx.close();
            return;
        }
        log.info("Received: {}", receivedMessage);
        ctx.writeAndFlush("Echo: " + receivedMessage + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        log.info("Channel registered: {}", ctx.channel().remoteAddress());
        ctx.writeAndFlush("[EXIT] Command: \"EXIT or exit\"\n");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        log.info("Channel unregistered: {}", ctx.channel().remoteAddress());
    }
}