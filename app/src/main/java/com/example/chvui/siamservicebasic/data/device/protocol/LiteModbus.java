package com.example.chvui.siamservicebasic.data.device.protocol;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by chvui on 23.10.2017.
 */

public final class LiteModbus {

    public static void write(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data) throws Exception {
        write(stream, deviceAddress, dataAddress, data, 0, (short) data.length);
    }

    public static void write(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data, int offset, short length) throws Exception {
        sendData(stream, deviceAddress, dataAddress, data, offset, length);
        receiveHeader(stream, deviceAddress, (byte) 2, dataAddress, length);
    }

    public static void read(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data) throws Exception {
        read(stream, deviceAddress, dataAddress, data, 0, (short) data.length);
    }

    public static void read(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data, int offset, short length) throws Exception {
        sendHeader(stream, deviceAddress, (byte) 1, dataAddress, length);
        receiveData(stream, deviceAddress, dataAddress, data, offset, length);
    }

    public static String readText(DeviceStream stream, byte deviceAddress, int textAddress, short length) throws Exception {
        byte[] data = new byte[length];
        read(stream, deviceAddress, textAddress, data);
        return new String(data, "Cp1251");
    }

    public static String readText(DeviceStream stream, byte deviceAddress, int textInfoAddress) throws Exception {
        byte[] buffer = new byte[6];
        read(stream, deviceAddress, textInfoAddress, buffer);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int textAddress = byteBuffer.getInt();
        short length = byteBuffer.getShort();
        return readText(stream, deviceAddress, textAddress, length);
    }

    public static ByteBuffer bufferRead(DeviceStream stream, byte deviceAddress, int dataAddress, int dataSize) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(dataSize);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        read(stream, deviceAddress, dataAddress, byteBuffer.array());
        return byteBuffer;
    }

    public static byte readByte(DeviceStream stream, byte deviceAddress, int dataAddress) throws Exception {
        return bufferRead(stream, deviceAddress, dataAddress, 1).get();
    }

    public static short readShort(DeviceStream stream, byte deviceAddress, int dataAddress) throws Exception {
        return bufferRead(stream, deviceAddress, dataAddress, 2).getShort();
    }

    public static int readInt(DeviceStream stream, byte deviceAddress, int dataAddress) throws Exception {
        return bufferRead(stream, deviceAddress, dataAddress, 4).getInt();
    }

    public static float readFloat(DeviceStream stream, byte deviceAddress, int dataAddress) throws Exception {
        return bufferRead(stream, deviceAddress, dataAddress, 4).getFloat();
    }

    public static void writeByte(DeviceStream stream, byte deviceAddress, int dataAddress, byte value) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(value);
        write(stream, deviceAddress, dataAddress, byteBuffer.array());
    }

    public static void writeShort(DeviceStream stream, byte deviceAddress, int dataAddress, short value) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort(value);
        write(stream, deviceAddress, dataAddress, byteBuffer.array());
    }

    public static void writeInt(DeviceStream stream, byte deviceAddress, int dataAddress, int value) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(value);
        write(stream, deviceAddress, dataAddress, byteBuffer.array());
    }

    public static void writeFloat(DeviceStream stream, byte deviceAddress, int dataAddress, float value) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putFloat(value);
        write(stream, deviceAddress, dataAddress, byteBuffer.array());
    }

    private static void sendData(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data, int offset, short length) throws IOException {
        sendHeader(stream, deviceAddress, (byte) 2, dataAddress, length);
        stream.write(data, offset, length);
        stream.write(Crc16.crc16(data, offset, length));
    }

    private static void sendHeader(DeviceStream stream, byte deviceAddress, byte commandId, int dataAddress, short dataSize) throws IOException {
        stream.init();
        stream.write(makeHeader(deviceAddress, commandId, dataAddress, dataSize));
    }

    private static byte[] makeHeader(byte deviceAddress, byte commandId, int dataAddress, short dataSize) {
        ByteBuffer buffer =  ByteBuffer.allocate(12);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) 0xd);
        buffer.put((byte) 0xa);
        buffer.put(deviceAddress);
        buffer.put(commandId);
        buffer.putInt(dataAddress);
        buffer.putShort(dataSize);
        buffer.put(Crc16.crc16(buffer.array(), 2, 8));
        return buffer.array();
    }

    private static void receiveHeader(DeviceStream stream, byte deviceAddress, byte commandId, int dataAddress, short length) throws Exception {
        byte[] buffer = new byte[14];
        stream.read(buffer, 0, 12);
        ByteBuffer header = ByteBuffer.wrap(buffer, 2, 10);
        header.order(ByteOrder.LITTLE_ENDIAN);
        byte aDeviceAddress = header.get();
        byte aCommandId = header.get();
        int aDataAddress = header.getInt();
        short aLength = header.getShort();

        if (aCommandId == commandId) {
            if (dataAddress != aDataAddress || length != aLength) {
                throw new Exception("Нарушение протокола");
            }
        } else if (aCommandId == (commandId | 0x80)) {
            stream.read(buffer, 12, 2);
            if (!Crc16.check(buffer, 2, 12)) {
                throw new Exception("Ошибка контрольной суммы");
            }            //ok для чтения заголовка ответа об ошибке
            short errorId = header.getShort();
            throw new Exception(String.format("Ошибка %d", errorId));
        } else {
            throw new Exception("Нарушение протокола");
        }
    }

    private static void receiveData(DeviceStream stream, byte deviceAddress, int dataAddress, byte[] data, int offset, short length) throws Exception {
        receiveHeader(stream, deviceAddress, (byte) 1, dataAddress, length);
        stream.read(data, offset, length);
        byte[] csBuffer = new byte[2];
        stream.read(csBuffer);
        byte[] cs = Crc16.crc16(data, offset, length);
        if (csBuffer[0] != cs[0] || csBuffer[1] != cs[1]) {
            throw new Exception("Ошибка контрольной суммы");
        }
    }
}
