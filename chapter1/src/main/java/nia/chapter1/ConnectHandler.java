package nia.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kerr.
 *
 * Listing 1.2 ChannelHandler triggered by a callback
 */
//代码清单 1-2：被回调触发的ChannelHandler
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    //当一个新的连接已经被建立时，channelActive ((hannelHandler Context）将会被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println(
                "Client " + ctx.channel().remoteAddress() + " connected");
    }
}