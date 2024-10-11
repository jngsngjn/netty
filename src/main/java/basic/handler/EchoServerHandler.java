package basic.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// ChannelInboundHandlerAdapter: 네티의 기본 핸들러로서, 수신된 데이터를 처리하는 데 사용
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // 클라이언트가 데이터를 서버로 전송했을 때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String receivedMessage = msg.toString().trim();
        if (receivedMessage.equalsIgnoreCase("exit")) {
            System.out.println("Received [EXIT] command, closing connection...");
            ctx.close();
            return;
        }

        System.out.println("Received: " + receivedMessage);
        ctx.writeAndFlush("Echo: " + receivedMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("Channel registered: " + ctx.channel().remoteAddress());
        ctx.writeAndFlush("[EXIT] Command: \"EXIT or exit\"");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        System.out.println("Channel unregistered: " + ctx.channel().remoteAddress());
    }
}