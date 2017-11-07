package com.zy.timeZone;

//import org.joda.time.DateTime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
/**
 * Created by ecfgikd on 2017/9/14.
 */
public class timeZoneTest
{

    public String getServerTimeInfo() throws Exception {
        Instant now = Instant.now();
        String offset = ZonedDateTime.ofInstant(now, ZoneId.systemDefault()).getOffset().toString();
        String serverTime = "{\"serverTimeZone\": \"" + offset + "\", \"utcOffset\": " + Integer.parseInt(offset.split(":")[0]) + " , \"timestamp\": " + now.toEpochMilli() + " }";
        return  serverTime;
    }

    public static void main(String[] args)  throws Exception{
        String s = new timeZoneTest().getServerTimeInfo();
        System.out.println(s);
    }
}




class ServerTime {
    private String serverTime;
    private String serverTimeZone;
    private long serverTimeZoneOffset;


    /**
     * @return the server time
     */
    public String getServerTime() {
        return serverTime;
    }

    /**
     * @param serverTime
     *            the server time to set
     */
    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }


    /**
     * @return the server time zone
     */
    public String getServerTimeZone() {
        return serverTimeZone;
    }


    /**
     * @param serverTimeZone
     *            the server time zone to set
     */
    public void setServerTimeZone(String serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }

    /**
     * @return the server time zone offset
     */
    public long getServerTimeZoneOffset() {
        return serverTimeZoneOffset;
    }


    /**
     * @param serverTimeZoneOffset
     *            the server time zone offset to set
     */
    public void setServerTimeZoneOffset(long serverTimeZoneOffset) {
        this.serverTimeZoneOffset = serverTimeZoneOffset;
    }
}

