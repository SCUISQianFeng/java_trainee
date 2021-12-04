package com.lis.trainee.nio;

import com.sun.javafx.image.impl.ByteRgb;
import org.apache.catalina.Server;
import org.apache.logging.log4j.util.StringBuilders;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName WebServer
 *
 * @author Lis on 2021/12/4
 **/
public class WebServer {

    public static void main(String[] args) throws IOException {
//        try {
//            // 1. 通过ServerSocketChannel 的open()方法创建一个ServerSocketChannel对象，open方法用于打开套接字通道
//            ServerSocketChannel ssc = ServerSocketChannel.open();
//            // 通过ServerSocketChannel绑定ip地址和port
//            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 3333));
//            // 通过ServerSocketChannelImpl的accept()方法创建一个SockerChannel对象用户从客户端读写数据
//            SocketChannel socketChannel = ssc.accept();
//            // 3.创建写数据的缓冲区对象
//            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
//            writeBuffer.put("Hello WebClient this is from WebServer".getBytes());
//            writeBuffer.flip();
//            socketChannel.write(writeBuffer);
//            // 创建读数据的缓冲区
//            ByteBuffer readBuffer = ByteBuffer.allocate(128);
//            // 读取缓冲区的数据
//            socketChannel.read(readBuffer);
//            StringBuilder stringBuilder = new StringBuilder();
//            // 4.将Buffer从写模式变成可读对象
//            readBuffer.flip();
//            while (readBuffer.hasRemaining()) {
//                stringBuilder.append((char) readBuffer.get());
//            }
//            System.out.println("从客户端收到的数据： " + stringBuilder);
//            socketChannel.close();
//            ssc.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8000));
            ssc.configureBlocking(false);

            Selector selector = Selector.open();
            // 注册channel， 并且指定感兴趣的事件是 Accept
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            ByteBuffer writeBuffer = ByteBuffer.allocate(128);

            writeBuffer.put("received".getBytes());
            writeBuffer.flip();

            while (true) {
                int nReady = selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        // 创建新的连接，并且注册到selector上，而且声明这个channel只对读操作感兴趣
                        SocketChannel socketChannel = ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuffer.clear();
                        socketChannel.read(readBuffer);
                        readBuffer.flip();
                        System.out.println("Received: " + new String(readBuffer.array()));
                        key.interestOps(SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        writeBuffer.rewind();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(writeBuffer);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
