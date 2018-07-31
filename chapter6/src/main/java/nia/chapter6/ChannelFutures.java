package nia.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by kerr.
 *
 * Listing 6.13 Adding a ChannelFutureListener to a ChannelFuture
 */
//代码清单 6-13 添加 ChannelFutureListener 到 ChannelFuture
public class ChannelFutures {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ByteBuf SOME_MSG_FROM_SOMEWHERE = Unpooled.buffer(1024);

    /**
     * Listing 6.13 Adding a ChannelFutureListener to a ChannelFuture
     * */
    public static void addingChannelFutureListener(){
        Channel channel = CHANNEL_FROM_SOMEWHERE; // get reference to pipeline;
        ByteBuf someMessage = SOME_MSG_FROM_SOMEWHERE; // get reference to pipeline;
        //...
        //调用出站操作（如 write（）方法）所返回的 ChannelFuture上的 addListener（）方法
        io.netty.channel.ChannelFuture future = channel.write(someMessage);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(io.netty.channel.ChannelFuture f) {
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                    f.channel().close();
                }
            }
        });
    }
}
