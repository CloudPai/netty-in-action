package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * Listing 8.8 Using Bootstrap with DatagramChannel
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 * @author <a href="mailto:mawolfthal@gmail.com">Marvin Wolfthal</a>
 */
//代码清单 8-8 使用 Bootstrap 和 DatagramChannel
//唯-区别就是，不再调用 connect（）方法，而是只调用 bind（）方法，如代码清单 8-8 所示。
public class BootstrapDatagramChannel {

    /**
     * Listing 8.8 Using Bootstrap with DatagramChannel
     */
    public void bootstrap() {
        //创建一个 Bootstrap 的实例以创建和绑定新的数据报 Channel
        Bootstrap bootstrap = new Bootstrap();
        //设置 EventLoopGroup，其提供了用以处理 Channel 事件的 EventLoop
        bootstrap.group(new OioEventLoopGroup()).channel(
                //指定Channel的实现
            OioDatagramChannel.class).handler(
                    //设置用以处理 Channel 的 I/O以及数据的ChannelInboundHandler
            new SimpleChannelInboundHandler<DatagramPacket>() {
                @Override
                public void channelRead0(ChannelHandlerContext ctx,
                    DatagramPacket msg) throws Exception {
                    // Do something with the packet
                }
            }
        );
        //调用 bind（方法，因为该协议是无连接的
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture)
               throws Exception {
               if (channelFuture.isSuccess()) {
                   System.out.println("Channel bound");
               } else {
                   System.err.println("Bind attempt failed");
                   channelFuture.cause().printStackTrace();
               }
            }
        });
    }
}
