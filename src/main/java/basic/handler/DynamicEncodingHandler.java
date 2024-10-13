package basic.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class DynamicEncodingHandler extends ChannelInboundHandlerAdapter {

    private Charset charset = CharsetUtil.UTF_8;  // 기본 인코딩은 UTF-8
    private boolean encodingDetected = false;     // 인코딩이 감지되었는지 여부

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;

        try {
            if (!encodingDetected) {
                // 첫 번째 메시지는 항상 UTF-8로 해석하여 인코딩 정보를 받는다.
                String encodingInfo = buf.toString(CharsetUtil.UTF_8).trim();
                if (encodingInfo.startsWith("ENCODING=")) {
                    String encoding = encodingInfo.split("=")[1].trim();
                    System.out.println("encoding = " + encoding);

                    if (encoding.equalsIgnoreCase("EUC-KR")) {
                        charset = Charset.forName("EUC-KR");  // 인코딩 설정
                        System.out.println("Client encoding set to EUC-KR");
                    } else {
                        charset = CharsetUtil.UTF_8;  // 기본 UTF-8 설정
                        System.out.println("Client encoding set to UTF-8");
                    }

                    encodingDetected = true;  // 인코딩 정보 감지 완료
                }
            } else {
                // 이후 메시지는 감지된 인코딩 방식으로 처리
                String received = buf.toString(charset);
                System.out.println("Received message: " + received);

                // 클라이언트에 응답 전송
                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(("Echo: " + received).getBytes(charset)));
            }
        } finally {
            buf.release();  // ByteBuf 해제
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}