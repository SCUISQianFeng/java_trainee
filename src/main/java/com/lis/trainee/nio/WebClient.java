package com.lis.trainee.nio;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ClassName WebClient
 *
 * @author Lis on 2021/12/4
 **/
public class WebClient {
    public static void main(String[] args) throws IOException {
//        // 1. 通过SocketChannel的open()方法创建一个SocketChannel对象
//        SocketChannel socketChannel = SocketChannel.open();
//        // 2. 连接到远程服务器
//        socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));
//        // 3.创建写缓冲区
//        ByteBuffer writeBuffer = ByteBuffer.allocate(128);
//        writeBuffer.put("Hello WebServer this is from WebClient".getBytes());
//        writeBuffer.flip();
//        // 把写缓冲区的数据写入通道中
//        socketChannel.write(writeBuffer);
//        // 创建读缓冲区
//        ByteBuffer readBuffer = ByteBuffer.allocate(128);
//        socketChannel.read(readBuffer);
//        // 将读缓冲区的数据转变成String StringBuilder 线程不安全
//        StringBuilder stringBuilder = new StringBuilder();
//        // 4.将buffer从写模式转变成读模式
//        readBuffer.flip();
//        while (readBuffer.hasRemaining()) {
//            stringBuilder.append((char) readBuffer.get());
//        }
//        System.out.println("从服务端接受的数据：" + stringBuilder);
//        socketChannel.close();
        try {

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            ByteBuffer readBuffer = ByteBuffer.allocate(128);

            writeBuffer.put("hello".getBytes());
            writeBuffer.flip();

            while(true) {
                writeBuffer.rewind();
                socketChannel.write(writeBuffer);
                readBuffer.clear();
                socketChannel.read(readBuffer);

            }
        } catch (IOException e) {

        }
    }
}
