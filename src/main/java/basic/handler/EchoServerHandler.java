package basic.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// ChannelInboundHandlerAdapter: 네티의 기본 핸들러로서, 수신된 데이터를 처리하는 데 사용
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /*
    클라이언트가 데이터를 서버로 전송했을 때 호출
    msg: 클라이언트로부터 받은 메시지
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Received: " + msg);

        // 클라이언트에게 받은 메시지를 "Echo: "와 함께 다시 보냄
        ctx.writeAndFlush("Echo: " + msg);
    }

    /*
    통신 중에 예외가 발생했을 때 호출
    예외 상황을 로그로 출력하고, 클라이언트와의 연결을 닫음
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}