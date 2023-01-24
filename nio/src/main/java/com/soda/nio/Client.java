package com.soda.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @date 1/23/2023
 */
public class Client {
    SocketChannel socketChannel;

    public void init() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8002));
        //socketChannel.configureBlocking(false);
    }

    public int write(ByteBuffer byteBuffer) throws IOException {
        return socketChannel.write(byteBuffer);

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.init();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            byte[] bytes = scanner.nextLine().getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            if (client.socketChannel.finishConnect()) {
                //while (true) {
                System.out.println(byteBuffer.position());
                int write = client.socketChannel.write(byteBuffer);
                System.out.println("have write：" + write);
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                System.out.println("read start");
                int read = client.socketChannel.read(readBuffer);

                System.out.println("read end：" + read);
                if (read > 0) {
                    byte[] array = readBuffer.array();
                    System.out.println(new String(array));
                }
            }
        }
        //TimeUnit.SECONDS.sleep(2);
        //}
    }
}
