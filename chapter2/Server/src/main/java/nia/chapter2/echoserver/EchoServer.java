package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Listing 2.2 EchoServer class
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
                " <port>"
            );
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        //1.创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2.创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //3.指定所使用的 NIO 传输 Channel
                .channel(NioServerSocketChannel.class)
                    //4.使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                    //5.添加一个 EchoServerHandler 到子Channel 的 ChannelPipeline
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //①.EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                        ch.pipeline().addLast(serverHandler);
                    }
                });

            //6.异步地绑定服务器；调用 sync（)方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //7.获取 Channel 的 CloseFuture，并且阻塞当前线程直到它完成
            System.out.println(EchoServer.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } finally {
            //8.关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
