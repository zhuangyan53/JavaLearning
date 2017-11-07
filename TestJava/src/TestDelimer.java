import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ecfgikd on 2017/9/7.
 */
public class TestDelimer {

    List<byte[]> delimiterByteList = new ArrayList();
    List<byte[]> delimiterByteWindowList = new ArrayList();


    InputStream in;


    public static void main(String [] args) throws Exception{
        String response = null;
        byte[] byteBuffer = new byte[1024];
        int off = 0;
        int len = byteBuffer.length;
        StringBuilder sb = new StringBuilder();
        int read;
        boolean endOfStream = false;

        List <String> d = new ArrayList();
        d.add(":");
        d.add(">");
        TestDelimer td = new TestDelimer(d.iterator());

        try {
            while (!endOfStream) {
                read = td.read(byteBuffer, off, len);
                if (read > 0) {
                    sb.append(new String(byteBuffer, 0, read, Charset.forName("UTF-8")));
                } else {
                    endOfStream = true;
                    response = sb.toString();
                }
            }
        } catch (IOException e) {
            throw new IOException("IOException while reading", e);
        } finally {
        }
        System.out.println(response);
    }
    public TestDelimer(Iterator<String> delimiters) {

        while (delimiters.hasNext()) {
            String currentDelimiter = delimiters.next();

            byte[] delimiterByteArray = currentDelimiter.getBytes();
            delimiterByteList.add(currentDelimiter.getBytes());

            this.delimiterByteWindowList.add(new byte[delimiterByteArray.length]);
//            byte[] currentByteArray = delimiterByteWindowList.get(delimiterByteWindowList.size() - 1);
//            for (int i = 0; i < currentByteArray.length; i++) {
//                currentByteArray[i] = -1;
//            }
        }
    }

    private int read1() throws Exception{
        return this.getInputStream().read();
    }

    private InputStream getInputStream() throws Exception{
        if(in==null){
            File f = new File("src/resp.txt");
            in = new FileInputStream(f);
        }

        return in;
    }



    public int read(byte[] byteBuffer, int off, int len) throws Exception{

        StringBuilder stringBuilder = new StringBuilder();


        if(delimiterFound()) {
            return -1;
        }

        int read = 0;

        byte currentByte;
        while (!delimiterFound()) {
            currentByte = (byte) read1();

            // shift all data in the window one step to the left. first delimiter.
            this.shiftToLeft(delimiterByteWindowList.get(0), currentByte);

            // shift all data in the window one step to the left of the remaining delimiters
            for (int index = 1; index < delimiterByteWindowList.size(); index++) {
                this.shiftToLeft(delimiterByteWindowList.get(index), currentByte);
            }
            // copy the first (valid) byte in the delimiter window into the response buffer
            if (currentByte >= 0) {
                byteBuffer[read + off] = currentByte;
                read++;
            }



            // check that we haven't exceeded the amount of data to read
            if (read >= len) {
                break;
            }

        }
        return read;

    }

    private boolean delimiterFound() {
        for (int index = 0; index < delimiterByteList.size(); index++) {
            if (Arrays.equals(delimiterByteWindowList.get(index), delimiterByteList.get(index))) {
                return true;
            }
        }
        return false;
    }

    private byte shiftToLeft(byte[] buf, byte shiftInByte) {
        byte shiftOutByte = buf[0];

        // shift all data in the window one step to the left
        System.arraycopy(buf, 1, buf, 0, buf.length - 1);

        // shift in new byte from the right
        buf[buf.length - 1] = shiftInByte;

        return shiftOutByte;
    }
}
