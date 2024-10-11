package basic.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String sendMessage = "Hello, server!";

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(sendMessage.getBytes());

        StringBuilder sb = new StringBuilder();
        sb.append("전송할 문자열 [");
        sb.append(sendMessage);
        sb.append("]");

        System.out.println(sb);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());

        StringBuilder sb = new StringBuilder();
        sb.append("수신한 문자열 [");
        sb.append(readMessage);
        sb.append("]");

        System.out.println(sb);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}