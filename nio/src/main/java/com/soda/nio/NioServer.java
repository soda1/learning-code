package com.soda.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author eric
 * @date 1/18/2023
 */
public class NioServer {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final static int LISTEN_PORT = 8002;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
    public void init() throws IOException {
        //创建fd
        serverSocketChannel = ServerSocketChannel.open();
        //设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        //绑定端口
        socket.bind(new InetSocketAddress(LISTEN_PORT));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() throws IOException {
        while (true) {
            int select = selector.selectNow();
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        }
    }

    private void write(SelectionKey key) throws IOException {
        //System.out.println(key + "is write");
        //SocketChannel channel = (SocketChannel)key.channel();
        //serverSocketChannel.configureBlocking(false);
        //channel.write(this.byteBuffer);
        //int interestOps = SelectionKey.OP_READ;
        //channel.register(this.selector, interestOps);
    }

    private void read(SelectionKey key) throws IOException {
        System.out.println(key + " is readable");
        SocketChannel channel = (SocketChannel)key.channel();
        this.byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        System.out.println("have read " + read + "bytes");
        this.byteBuffer.flip();
        channel.write(this.byteBuffer);
        //channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    private void accept(SelectionKey key) throws IOException {
        System.out.println(key + " is acceptable");
        ServerSocketChannel clientChanel =(ServerSocketChannel) key.channel();
        SocketChannel channel = clientChanel.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
    }


    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.init();
        nioServer.run();
    }


}
