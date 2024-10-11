package basic.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    // 클라이언트가 서버에 연결되었을 때 호출
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String sendMessage = "Hello, server!";

        // ByteBuf 객체 생성
        ByteBuf buffer = Unpooled.buffer();

        // 메시지를 바이트 배열로 변환 후 버퍼에 씀
        buffer.writeBytes(sendMessage.getBytes());

        System.out.println("전송할 문자열 [" + sendMessage + "]");

        // 데이터를 전송하고 버퍼를 비움
        ctx.writeAndFlush(buffer);
    }

    // 서버로부터 데이터를 수신했을 때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Charset.defaultCharset(): 시스템 기본 문자셋
        String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
        System.out.println("수신한 문자열 [" + readMessage + "]");
    }

    // 서버로부터의 데이터 수신이 완료되면 호출
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