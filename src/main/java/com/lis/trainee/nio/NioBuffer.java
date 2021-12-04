package com.lis.trainee.nio;

import com.sun.javafx.image.impl.ByteRgb;

import java.nio.ByteBuffer;

/**
 * ClassName NioBuffer
 *
 * @author Lis on 2021/12/4
 **/
public class NioBuffer {
    public static void main(String[] args) {
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(33);
        System.out.println("--------Test reset------------");
        // clear() ,position设置为0，limit设置为capacity的值
        buffer.clear();
        // 设置缓冲器的位置
        buffer.position(5);
        // 将此缓冲区的标记设置在其位置
        buffer.mark();
        // 设置缓冲器新的位置
        buffer.position(10);
        System.out.println("before reset:      " + buffer);
        // 将此缓冲区的位置重置为先前标记的位置
        buffer.reset();
        System.out.println("after reset:     " + buffer);

        System.out.println("----------Test rewind----------------");
        buffer.clear();
        buffer.put("abcd".getBytes());
        System.out.println("before compact:   " + buffer);
        System.out.println(new String(buffer.array()));
        ;
        // flip 翻转就是将一个处于数存数据状态的缓冲区变为一个处于准备读取数据的状态
        buffer.flip();
        System.out.println("after flip:   " + buffer);
        // get方法，相对读，从position位置读取一个byte，并将position+1，为下次读写做准备
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println("after three gets:    " + buffer);
        System.out.println("\t" + new String(buffer.array()));
        //把从position到limit中的内容移到0到limit-position的区域内，position和limit的取值也分别变成limit-position、capacity。
        // 如果先将positon设置到limit，再compact，那么相当于clear()
        buffer.compact();
        System.out.println("after compact:    " + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("-----------Test get------------------");
        buffer = ByteBuffer.allocate(32);
        buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c').put((byte) 'd').put((byte) 'e').put((byte) 'f');
        System.out.println("before flip():    " + buffer);
        // 转换为读取模式
        buffer.flip();
        System.out.println("before get():       " + buffer);
        System.out.println((char) buffer.get());
        System.out.println("after get():       " + buffer);
        // get(index)不影响position的位置
        System.out.println((char) buffer.get(2));
        System.out.println("afer get(index):     " + buffer);
        byte[] dst = new byte[10];
        buffer.get(dst, 0, 2);
        System.out.println("after get(dst, 0, 2):   " + buffer);
        System.out.println("\t dst: " + new String(dst));
        System.out.println("buffer now is:     " + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("-------------Test put-----------");
        ByteBuffer bb = ByteBuffer.allocate(32);
        System.out.println("before put(byte)" + bb);
        System.out.println("after put(byte):   " + bb.put((byte) 'z'));
        System.out.println("\t" + bb.put(2, (byte) 'z'));
        // put(2, (byte)'c'不改变position的位置
        System.out.println("after put(2, (byte) 'c'):    " + bb);
        System.out.println("\t" + new String(bb.array()));
        // 这里的buffer是 abcdef[pos=3 lim=6 cap=32]
        bb.put(buffer);
        System.out.println("after put(buffer):     " + bb);
        System.out.println("\t" + new String(bb.array()));


    }
}
