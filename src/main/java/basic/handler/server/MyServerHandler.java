package basic.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.StandardCharsets;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private final StringBuilder messageBuilder = new StringBuilder();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("[SERVER] channelRead() is called");

        String partMessage = ((ByteBuf) msg).toString(StandardCharsets.UTF_8);
        messageBuilder.append(partMessage);
        System.out.println("[SERVER] Received part: " + partMessage);

        // 10바이트를 모두 수신했을 때 전체 메시지를 출력
        if (messageBuilder.length() >= 10) {
            System.out.println("[SERVER] Full message received: " + messageBuilder);
            messageBuilder.setLength(0);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            if (event.state() == IdleStateEvent.READER_IDLE_STATE_EVENT.state()) {
                System.out.println("[SERVER] Read timeout - closing connection..");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}