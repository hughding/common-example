package pers.hugh.common.practice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author dingxiuzheng
 */
public class NettyServer {
    public static void main(String[] args) {
        // 第1步:
        // 首先我们创建了两个EventLoopGroup实例：parentGroup和childGroup，
        // 目前可以将parentGroup和childGroup理解为两个线程池:
        // 其中parentGroup用于接受客户端连接，parentGroup在接受到客户端连接之后，将连接交给childGroup来进行处理
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        // 第2步:
        // 接着，我们创建一个ServerBootstrap实例，从名字上就可以看出来这是一个服务端启动类，
        // 我们需要给它设置一些参数，包括第1步创建的parentGroup和childGroup。
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentGroup, childGroup)

                // 第3步:
                // 我们通过channel方法指定了NioServerSocketChannel，这是netty中表示服务端的类，
                // 用于接受客户端连接，对应于java.nio包中的ServerSocketChannel。
                .channel(NioServerSocketChannel.class)

                // 第4步:
                // 我们通过childHandler方法，设置了一个匿名内部类ChannelInitializer实例，
                // 用于初始化客户端连接SocketChannel实例。
                // 在第3步中，我们提到NioServerSocketChannel是用于接受客户端连接，
                // 在接收到客户端连接之后，netty会回调ChannelInitializer的initChannel方法需要对接受到的这个客户端连接进行一些初始化工作，
                // 主要是告诉netty之后如何处理和响应这个客户端的请求。
                // 在这里，主要是添加了3个ChannelHandler实例：LineBasedFrameDecoder、StringDecoder、TimeServerHandler。
                // 其中LineBasedFrameDecoder、StringDecoder是netty本身提供的，用于解决TCP粘包、解包的工具类。

                //LineBasedFrameDecoder在解析客户端请求时，遇到字符”\n”或”\r\n”时则认为是一个完整的请求报文，
                // 然后将这个请求报文的二进制字节流交给StringDecoder处理。

                //StringDecoder将二进制字节流转换成一个字符串，交给TimeServerHandler来进行处理。

                //TimeServerHandler是我们自己要编写的类，在这个类中，我们要根据用户请求返回当前时间。
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });

        try {
            // 第5步:
            // 在所有的配置都设置好之后，我们调用了ServerBootstrap的bind(port)方法，
            // bind是异步操作，sync方法是等待异步操作执行完毕。
            // 开启真正的监听在8080端口，接受客户端请求。
            ChannelFuture cf = serverBootstrap.bind(8080).sync();
            System.out.println("NettyServer Started on 8080...");
            // 等待服务端监听端口关闭，closeFuture是异步操作,
            // 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }

    static class ServerHandler extends ChannelInboundHandlerAdapter {

        //当客户端连接服务器完成就会触发该方法
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println("客户端连接通道建立完成");
        }

        //读取客户端发送的数据
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            //Channel channel = ctx.channel();
            //ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站
            //将 msg 转成一个 ByteBuf，类似NIO 的 ByteBuffer
            ByteBuf buf = (ByteBuf) msg;
            System.out.println("收到客户端的消息:" + buf.toString(CharsetUtil.UTF_8));
        }

        // 数据读取完毕处理方法
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ByteBuf buf = Unpooled.copiedBuffer("HelloClient".getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(buf);
        }

        //处理异常, 一般是需要关闭通道
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            ctx.close();
        }
    }
}
