package pers.hugh.common.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xzding
 * @date 2018/7/17
 */
public class SelectorPractice {

    private static final int[] PORTS = new int[]{8000, 8001, 8002, 8003};

    private static final ByteBuffer BYTE_BUFFER = ByteBuffer.allocate(1024);

    //直接内存
    private static final ByteBuffer BYTE_DIRECT_BUFFER = ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws IOException {
        //==========1.创建Selector==========4.使用完后关闭Selector==========
        try (Selector selector = Selector.open()) {
            for (int port : PORTS) {
                ServerSocketChannel ssc = ServerSocketChannel.open();
                //设置为非阻塞
                ssc.configureBlocking(false);
                //监听端口
                ssc.socket().bind(new InetSocketAddress(port));
                //OP_ACCEPT是新建立连接的事件
                //==========2.向Selector注册通道==========
                SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("listen on " + port);
            }

            while (true) {
                //==========3.通过Selector选择准备就绪的通道==========
                // select() 方法将返回所发生的事件的数量, 会阻塞, 非阻塞方法selectNow。
                int num = selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();

                        sc.configureBlocking(false);
                        //由于接受这个连接的目的是为了读取来自套接字的数据，所以我们还必须将 SocketChannel 注册到 Selector上
                        sc.register(selector, SelectionKey.OP_READ);

                        System.out.println("accept on " + sc);
                        iterator.remove();
                    } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        SocketAddress localAddress = sc.getLocalAddress();
                        SocketAddress remoteAddress = sc.getRemoteAddress();

                        BYTE_BUFFER.clear();
                        sc.read(BYTE_BUFFER);

                        BYTE_BUFFER.flip();
                        sc.write(BYTE_BUFFER);
                        sc.close();

                        System.out.println("read and write on " + localAddress + " remote on " + remoteAddress + " content:\n" + new String(BYTE_BUFFER.array(), Charset.forName("UTF-8")));
                        iterator.remove();
                    }
                }
            }
        }
    }
}
