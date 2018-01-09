package com.example.chvui.siamservicebasic.data.device.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chvui on 23.10.2017.
 */

public final class DeviceStream {

    private static final int TIME_OUT = 5000;

    private final InputStream mInputStream;
    private final OutputStream mOutputStream;

    public DeviceStream(InputStream inputStream, OutputStream outputStream) {
        mInputStream = inputStream;
        mOutputStream = outputStream;
    }

    public void init() throws IOException {
        int av = mInputStream.available();
        if(av > 0 ) {
            byte[] buffer = new byte[av];
            mInputStream.read(buffer);
        }
    }

    public void read(byte[] data, int offset, int length) throws IOException {
        long startTime = System.currentTimeMillis();
        long elapsed;
        while(length > 0) {
            if (mInputStream.available() > 0) {
                int result = mInputStream.read(data, offset, length);
                offset += result;
                length -= result;
            }
            elapsed = System.currentTimeMillis() - startTime;
            if (elapsed > TIME_OUT)
                throw new RuntimeException("timeout");
        }
    }

    public void read(byte[] data) throws IOException {
        read(data, 0, data.length);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        mOutputStream.write(data, offset, length);
    }

    public void write(byte[] data)  throws IOException{
        write(data, 0, data.length);
    }
}
