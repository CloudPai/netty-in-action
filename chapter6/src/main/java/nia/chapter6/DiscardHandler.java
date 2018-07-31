package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Listing 6.1 Releasing message resources
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//代码清单6-1释放消息资源
//扩展了 ChannelInboundHandlerAdapter
@Sharable
public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //丢弃已接收的消息
        ReferenceCountUtil.release(msg);
    }

}

