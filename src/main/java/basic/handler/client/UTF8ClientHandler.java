package basic.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UTF8ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LogManager.getLogger(UTF8ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String message = "안녕하세요. (UTF-8)";
        ctx.writeAndFlush(message);
        log.info("[Client] Send to server: {}", message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("[Client] Received from server: {}", msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}