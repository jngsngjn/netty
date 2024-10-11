package basic.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UTF8ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 첫 번째 메시지로 인코딩 정보를 보냄
        ctx.writeAndFlush("ENCODING=UTF-8");
        String message = "안녕하세요 (UTF-8)";
        ctx.writeAndFlush(message);
        System.out.println("Sent: " + message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Received from server: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}