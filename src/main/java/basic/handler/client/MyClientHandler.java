package basic.handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        ctx.writeAndFlush(Unpooled.copiedBuffer("12345,", StandardCharsets.UTF_8));
        System.out.println("[CLIENT] Sent (1)");
        System.out.println("[CLIENT] Sleep for 10 seconds...Zzz");
        Thread.sleep(10000); // 10초 sleep

        ctx.writeAndFlush(Unpooled.copiedBuffer("67890,", StandardCharsets.UTF_8));
        System.out.println("[CLIENT] Sent (2)");
        System.out.println("[CLIENT] Closing connection...");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}