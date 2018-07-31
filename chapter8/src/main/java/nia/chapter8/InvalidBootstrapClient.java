package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 8.3 Incompatible Channel and EventLoopGroup
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//代码清单 8-3 不兼容的 Channel 和 EventLoopGroup
    //这段代码将会导致 IllegalStateExcept ion，因为它混用了不兼容的传输。
public class InvalidBootstrapClient {

    public static void main(String args[]) {
        InvalidBootstrapClient client = new InvalidBootstrapClient();
        client.bootstrap();
    }

    /**
     * Listing 8.3 Incompatible Channel and EventLoopGroup
     * */
    public void bootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        //创建一个新的 Bootstrap 类的实例，以创建新的客户端 Channel
        Bootstrap bootstrap = new Bootstrap();
        //指定一个适用于 NIO 的 EventLoopGroup 实现
        bootstrap.group(group)
                //指定一个适用于 OIO 的 Channel 实现类
                .channel(OioSocketChannel.class)
                //设置一个用于处理 Channel 的I/O事件和数据的 ChannelInboundHandler
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                @Override
                protected void channelRead0(
                    ChannelHandlerContext channelHandlerContext,
                    ByteBuf byteBuf) throws Exception {
                    System.out.println("Received data");
                }
             });
        ChannelFuture future = bootstrap.connect(
                //尝试连接到远程节点
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }
}
