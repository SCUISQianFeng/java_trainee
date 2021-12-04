package com.lis.trainee.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName NioChannel
 *
 * @author Lis on 2021/12/4
 **/
public class NioChannel {

    public static void main(String[] args) throws IOException {
        // 1.创建一个RandomAccessFile对象
        RandomAccessFile raf = new RandomAccessFile("E:\\Project\\java_trainee\\src\\main\\resources\\data\\niodata.txt", "rw");
        // 通过RandomAccessFile对象的getChannel()方法
        FileChannel inChannel = raf.getChannel();
        // 2.创建一个读数据的缓冲区对象
        ByteBuffer buf = ByteBuffer.allocate(48);
        // 3. 从通道中读取数据
        int bytesRead = inChannel.read(buf);
        // 创建一个写数据缓冲区对象
        ByteBuffer buf2 = ByteBuffer.allocate(48);
        // 写入数据
        buf2.put("filechannel test".getBytes());
        // 翻转，写模式转变为读模式
        buf2.flip();
        // 将写缓冲区中的数据读入到inChannel中
        inChannel.write(buf);
        while (bytesRead != -1) {
            System.out.println("Read" + bytesRead);
            buf.flip();
            // 如果还有未读内容
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            // 清空缓存区
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        // 关闭RandomAccessFile对象
        raf.close();
    }
}
