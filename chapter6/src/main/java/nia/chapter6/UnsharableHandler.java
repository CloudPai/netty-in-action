package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Listing 6.11 Invalid usage of @Sharable
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//代码清单 6-11 @Sharable 的错误用法
    //使用注解 @Sharable 标注
@Sharable
public class UnsharableHandler extends ChannelInboundHandlerAdapter {
    private int count;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //将 count 字段的值加 1
        count++;
        //记录方法调用，并转发给下一个 ChannelHandler
        System.out.println("inboundBufferUpdated(...) called the "
                + count + " time");
        ctx.fireChannelRead(msg);
    }
}

