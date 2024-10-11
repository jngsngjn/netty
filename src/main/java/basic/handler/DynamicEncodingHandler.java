package basic.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class DynamicEncodingHandler extends ChannelInboundHandlerAdapter {

    // 기본 인코딩
    private Charset charset = CharsetUtil.UTF_8;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf) msg;

            // 첫 번째 메시지로 인코딩 정보를 수신한다고 가정
            String encodingInfo = buf.toString(CharsetUtil.UTF_8).trim();
            if (encodingInfo.startsWith("ENCODING=")) {
                String encoding = encodingInfo.split("=")[1].trim();
                if (encoding.equalsIgnoreCase("EUC-KR")) {
                    charset = Charset.forName("EUC-KR");
                    System.out.println("Client encoding set to EUC-KR");
                } else {
                    charset = CharsetUtil.UTF_8;
                    System.out.println("Client encoding set to UTF-8");
                }
            } else {
                // 인코딩 정보 이외의 일반 메시지 처리
                System.out.println("Received: " + buf.toString(charset));
                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(("Echo: " + buf.toString(charset)).getBytes(charset)));
            }
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}