package basic.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class EUCKRClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 첫 번째 메시지(인코딩 정보)는 UTF-8로 전송
        ctx.writeAndFlush(Unpooled.copiedBuffer("ENCODING=EUC-KR", CharsetUtil.UTF_8));
        String message = "안녕하세요 (EUC-KR)";

        // 이후 메시지는 EUC-KR 인코딩으로 전송
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, Charset.forName("EUC-KR")));
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