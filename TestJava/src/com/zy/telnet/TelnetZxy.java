package com.zy.telnet;



import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * Created by ecfgikd on 2017/11/3.
 */
public class TelnetZxy {


    static String host = "10.144.26.25";
    static int port = 10023;
    static String userName = "admin";
    static String password = "1234";
    static String loginPrompt = "name:";
    static String passwordPrompt = "Password:";
    static int connectionTimeout = 10000;
    static String crlf = "\r";
    static String command = "help";

    public static void main(String[] args) throws IOException {

        TelnetClient tc = new TelnetClient(host,port,crlf);
        tc.connect(connectionTimeout,connectionTimeout);
        tc.login(userName,password,loginPrompt,passwordPrompt);
        tc.readUntil(">");
        tc.write_command(command);
        try {
            tc.readUntil(">");
        }catch(Exception e){
            System.out.println("timeout!!!!!!!!!!!!!!! no prompt found.");
        }
        tc.write_command(command);
        tc.readUntil(">");
        tc.write_command(command);
        tc.readUntil(">");
        tc.close();

    }
}

class TelnetInputStream extends FilterInputStream{
    private OutputStream outputStream;
    private final byte[] delimiter;
    private final byte[] delimiterWindow;
    private static final int IAC = 255;
    private static final int DO = 253;
    private static final int DONT = 254;
    private static final int WILL = 251;
    private static final int WONT = 252;
    private static final int SUBNEGOTIATION = 250;
    private static final int NOOPERATION = 0;
    private static final int ENDOFSUBNEGOTIATION = 240;


    public TelnetInputStream(String delimiter, InputStream in, OutputStream out) {
        super(in);

        this.outputStream = out;
        this.delimiter = delimiter.getBytes();
        this.delimiterWindow = new byte[this.delimiter.length];
        for (int i = 0; i < delimiterWindow.length; i++) {
            delimiterWindow[i] = -1;
        }
    }

    public int read(byte[] byteBuffer) throws IOException {
        return this.read(byteBuffer, 0, byteBuffer.length);
    }

    public int read(byte[] byteBuffer, int off, int len) throws IOException {
        if (len > byteBuffer.length - off) {
            throw new IllegalArgumentException("The sum of length [" + len + "] and offset [" + off + "] cannot exceed the length [" + byteBuffer.length
                    + "] of the array");
        }

        if (this.delimiter.length > len) {
            throw new IllegalArgumentException("The amount of data to read [" + len + "] must be equal or greater than the length [" + this.delimiter.length
                    + "] of the delimiter");
        }

        /*
         * if we've already found the delimiter return -1, i.e. no data read
         */
        if (Arrays.equals(delimiterWindow, delimiter)) {
            return -1;
        }

        int read = 0;

        byte currentByte = 0;
        byte shiftedByte = -1;
        while (!Arrays.equals(delimiterWindow, delimiter)) {
            currentByte = (byte) read();

            // shift all data in the window one step to the left
            shiftedByte = this.shiftToLeft(delimiterWindow, currentByte);

            // copy the first (valid) byte in the delimiter window into the response buffer
            if (shiftedByte >= 0) {
                byteBuffer[read + off] = shiftedByte;
                read++;
            }



//            System.out.println("delimiter window [" + new String(delimiterWindow) + "]");

            // check that we haven't exceeded the amount of data to read
            if (read >= len) {
                break;
            }

        }
        return read;
    }

    public int read() throws IOException {
        int b = super.read();

        /*
         * if we detect end-of-stream (-1) it is considered an error as there is no more data to fetch
         */
        if (b <= -1) {
            throw new SocketException("End of stream detected on the connection");
        }

        while (b == IAC) {
            handleIAC();
            b = super.read();
        }

        return b;
    }

    private void handleIAC() throws IOException {
        final int command = super.read();
        System.out.println("recived Negotiation message!");
        // Option Negotiation
        if ((command == WILL) || (command == WONT) || (command == DO) || (command == DONT) || command == SUBNEGOTIATION) {
            final int option = super.read();
            if (option == 24) {
                if (command == DO) {
                    System.out.println("Command: DO, option:" + option);
                    outputStream.write(IAC);
                    outputStream.write(WILL);
                    outputStream.write(option);
                } else {
                    if (command == SUBNEGOTIATION) {
                        while (super.read() != ENDOFSUBNEGOTIATION) {
                        }

                        System.out.println("Command: 250, option:" + option);
                        outputStream.write(IAC);
                        outputStream.write(SUBNEGOTIATION); // Subnegotiation
                        outputStream.write(option); // Should be 24
                        outputStream.write(NOOPERATION); // No operation
                        outputStream.write(85); // U
                        outputStream.write(78); // N
                        outputStream.write(75); // K
                        outputStream.write(78); // N
                        outputStream.write(79); // O
                        outputStream.write(87); // W
                        outputStream.write(78); // N
                        outputStream.write(IAC); // Interpret as command
                        outputStream.write(ENDOFSUBNEGOTIATION); // End of subnegotiation
                        // parameters
                    }
                }
            } else {
                if (command == DO) {
                    System.out.println("Command: DO, option:" + option);
                    outputStream.write(IAC);
                    outputStream.write(WONT);
                    outputStream.write(option);
                } else {
                    if (command == WILL) {
                        System.out.println("Command: DO, option:" + option);
                        outputStream.write(IAC);
                        outputStream.write(DONT);
                        outputStream.write(option);
                    }
                }
            }
            outputStream.flush();
        }
    }

    private byte shiftToLeft(byte[] buf, byte shiftInByte) {
        byte shiftOutByte = buf[0];

        // shift all data in the window one step to the left
        for (int i = 0; i < buf.length - 1; i++) { // NOSONAR 2013-02-18 epkmkas, element shifting
            buf[i] = buf[i + 1];
        }

        // shift in new byte from the right
        buf[buf.length - 1] = shiftInByte;

        return shiftOutByte;
    }
}

class TelnetClient implements Closeable{
    private final String crlf;

    /** The buffer used to read data from the {@link #inputStream}. */
    private final byte[] byteBuffer = new byte[1024];

    /** The host we're connected to. */
    private final String host;

    /** The port we're connected to. */
    private final int port;

    /** The actual connection object. */
    private Socket socket;

    /** The output stream for the connection. */
    private OutputStream outputStream;

    /** The input stream for the connection. */
    private InputStream inputStream;

    private Writer writer;

    /** States if this client is connected to the server. */
    private boolean connected = false;

    public TelnetClient(String host, int port, String crlf) {
        this.host = host;
        this.port = port;
        this.crlf = crlf;
        connected = false;
    }

    public void connect(int connectionTimeout, int responseTimeout) throws IOException {
        if (connected) {
            return;
        }


        System.out.println("Attempting to connect to [" + host + "]:[" + port + "] with a timeout of " + connectionTimeout + "ms");

        this.socket = new Socket();

        try {
            this.socket.connect(new InetSocketAddress(host, port), connectionTimeout);
            this.socket.setTcpNoDelay(true);
            this.socket.setSoTimeout(responseTimeout);
        } catch (SocketTimeoutException e) {

            throw new IOException(e.getMessage(), e);
        }

        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
        this.writer = new OutputStreamWriter(this.outputStream);
        this.connected = true;


        System.out.println("Connected to [" + this.host + "]:[" + this.port + "]");
    }

    public void login(String userName, String password, String loginPrompt, String passwordPrompt) throws IOException {

        readUntil(loginPrompt);
        write_login(userName);
        readUntil(passwordPrompt);
        write_login(password);
    }

    public String readUntil(String delimiter) throws IOException {

        System.out.println("waiting prompt: [" + delimiter + "]");

        TelnetInputStream istream = new TelnetInputStream(delimiter, this.inputStream, this.outputStream);

        StringBuilder sb = new StringBuilder();
        int read = -1;
        String response = null;
        boolean moredata = true;

        while (moredata) {
            read = istream.read(byteBuffer);
            if (read > 0) {
                sb.append(new String(byteBuffer, 0, read));
            } else {
                moredata = false;
                response = sb.toString();
            }
        }
        System.out.println("Response:"+response);
        return response;
    }

    public void close() {
        if (!connected) {
            return;
        }

        System.out.println("Disconnecting from [" + this.host + "]:[" + this.port + "]");

        connected = false;

        try {
            if (!socket.isInputShutdown()) {
                inputStream.close();
            }
        } catch (IOException ex) { // NOSONAR 4 jan 2012 Peter Nerg (epknerg) - just ignored
        }

        try {
            if (!socket.isOutputShutdown()) {
                outputStream.close();
            }
        } catch (IOException ex) { // NOSONAR 4 jan 2012 Peter Nerg (epknerg) - just ignored
        }

        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) { // NOSONAR 4 jan 2012 Peter Nerg (epknerg) - just ignored
        }
    }

    private void write_login(String data) throws IOException {
        writer.write(data);
        writer.write(crlf);
        writer.flush();
    }

    public void write_command(String command) throws IOException {
        System.out.println("Sending Command:"+command);
        writer.write(command);
        writer.write(crlf);
        writer.flush();

        System.out.println("Command send out:"+command);
    }

    public void write_crlf() throws IOException {
        System.out.println("Sending crlf..");
        writer.write(crlf);
        writer.flush();

        System.out.println("crlf Command send out");
    }


    OutputStream getOutputStream() {
        return outputStream;
    }

    InputStream getInputStream() {
        return inputStream;
    }

}
