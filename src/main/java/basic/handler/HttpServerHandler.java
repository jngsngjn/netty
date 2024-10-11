package basic.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    // 클라이언트로부터 HTTP 요청이 수신되었을 때 호출
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 요청 확인
        HttpMethod method = request.method();
        String uri = request.uri();

        // 응답 생성 및 설정
        FullHttpResponse response;

        if (HttpMethod.GET.equals(method) && "/hello".equals(uri)) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.content().writeBytes("Hello, this is Netty HTTP Server!".getBytes());
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
            response.content().writeBytes("404 Not Found".getBytes());
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        }

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        // 응답 전송 후 해당 클라이언트와의 연결을 닫음
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}