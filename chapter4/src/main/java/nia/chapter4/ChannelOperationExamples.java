package nia.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Listing 4.5 Writing to a Channel
 *
 * Listing 4.6 Using a Channel from many threads
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//写数据并将其冲刷到远程节点
//代码清单 4-5 写出到 Channel
public class ChannelOperationExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    /**
     * Listing 4.5 Writing to a Channel
     */
    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
        //创建持有要写数据的 ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        //写数据并冲刷它
        ChannelFuture cf = channel.writeAndFlush(buf);
        //添加 ChannelFutureListener 以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    //写操作完成，并且没有错误发生
                    System.out.println("Write successful");
                } else {
                    //记录错误
                    System.err.println("Write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * Listing 4.6 Using a Channel from many threads
     */
    //代码清单 4-6 从多个线程使用同一个 Channel
    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
        //创建持有要写数据的Bytebuf
        final ByteBuf buf = Unpooled.copiedBuffer("your data",
                CharsetUtil.UTF_8);
        //创建将数据写到 Channel 的 Runnable
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.write(buf.duplicate());
            }
        };
        //获取到线程池 Executor 的引用
        Executor executor = Executors.newCachedThreadPool();

        // write in one thread   递交写任务给线程池以便在某个线程中执行
        executor.execute(writer);

        // write in another thread  递交另一个写任务以便在另一个线程中执行
        executor.execute(writer);
        //...
    }
}
