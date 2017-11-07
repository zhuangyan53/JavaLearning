package com.zy.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by ecfgikd on 2017/9/8.
 */
public class ChannelTest {

    public void TestChannel() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("src/com/zy/nio/data.txt","rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesReads = inChannel.read(buf);
        while (bytesReads!=-1){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }

            buf.clear();
            bytesReads = inChannel.read(buf);
        }
        aFile.close();

    }

    public static void main(String[] args) throws IOException {
        ChannelTest tc = new ChannelTest();
        tc.TestChannel();
    }

}
