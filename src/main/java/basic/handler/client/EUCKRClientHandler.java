package basic.handler.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.Charset;

public class EUCKRClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LogManager.getLogger(EUCKRClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String message = "안녕하세요. (EUC-KR)";
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, Charset.forName("EUC-KR")));
        log.info("[Client] Send to server: {}", message);
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