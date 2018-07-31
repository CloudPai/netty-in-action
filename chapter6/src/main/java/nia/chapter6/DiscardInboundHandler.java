package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Listing 6.3 Consuming and releasing an inbound message
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//代码清单 6-3 消费并释放入站消息
    //扩展了 ChannelInboundandlerAdapter
@Sharable
public class DiscardInboundHandler extends ChannelInboundHandlerAdapter {
    //通过调用 ReferenceCountUtil. Release ()方法释放资源
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ReferenceCountUtil.release(msg);
    }
}
