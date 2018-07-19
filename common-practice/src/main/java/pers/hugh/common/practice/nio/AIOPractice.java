package pers.hugh.common.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author xzding
 * @date 2018/7/19
 */
public class AIOPractice {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                result.read(byteBuffer);

                System.out.println(new String(byteBuffer.array(), Charset.forName("UTF-8")));
                try {
                    result.close();
                } catch (IOException e) {
                    System.out.println("close exception " + e);
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("accept fail " + exc);
            }
        });

        TimeUnit.SECONDS.sleep(600);
    }
}
