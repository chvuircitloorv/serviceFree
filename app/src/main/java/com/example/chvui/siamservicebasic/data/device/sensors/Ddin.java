package com.example.chvui.siamservicebasic.data.device.sensors;

import com.example.chvui.siamservicebasic.data.device.protocol.DeviceStream;
import com.example.chvui.siamservicebasic.data.device.protocol.LiteModbus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by chvui on 14.12.2017.
 */

public final class Ddin {

    // common registers
    // name
    private static final int NAME_ADDR = 0x00000004;
    // number
    private static final int DEVICE_NUMBER = 0x0000000A;
    // uniq number
    private static final int DEVICE_ID = 0x0000000E;
    // current parametrs
    // voltage
    private static final int VOLTAGE = 0x00008400;
    // temperature
    private static final int TEMPERATURE = 0x00008402;
    // load
    private static final int LOAD = 0x00008404;
    // acceleration
    private static final int ACCELERATION = 0x00008408;
    // measurements parametrs
    // diamentr shtoka (min 120 max 400)
    private static final int ROD_DIAMETER = 0x00008000;
    // period ka4aniya (min 4000 max 180000)
    private static final int DYN_PERIOD = 0x00008002;
    // nomer ontversiya (min 1 max 5)
    private static final int APERT_NUMBER = 0x00008006;
    // dlina hoda (min 500 max 9999)
    private static final int IMTRAVEL = 0x00008008;
    // tip shgny (1, 2, 3)
    private static final int MODEL_PUMP = 0x0000800A;
    // energozavisinie parametri
    // nkp nagruzki
    private static final int NKP = 0x00008100;
    // rkp nagruzki
    private static final int RKP = 0x00008104;
    // g0
    private static final int G0 = 0x00008108;
    // g1
    private static final int G1 = 0x0000810C;
    // interval vklu4eniya min 120(2 мин), max 1209600(2 недели)(r/w)(для ДДИМ-02)
    private static final int TURN_ON_INTERVAL = 0x00008110;
    // smeshenie nylya
    private static final int ZERO_OFFSET = 0x00008114;
    // koef naklona
    private static final int INCLINE_KOEF = 0x00008118;
    // datchik uskoreniya
    private static final int G_MINUS = 0x0000811C;
    // запрет выключения по времени, 1- выкл, 0-не выкл.
    private static final int TURN_OFF_BY_TIME = 0x00008120;
    private static final int CONTROL_REG = 0x00008800;
    private static final int STATUS_REG = 0x00008802;
    // ot4et
    // maximalnya nagruzka
    private static final int REPORT_STRUCTURE = 0x80000000;

    private static final int DATA = 0x81000000;

    private static final int ERROR = 0x00008804;

    public static final short START = 1;
    public static final short INIT = 2;
    public static final short OFF = 5;

    public static final short MIN_LENGTH = 500;
    public static final short MAX_LENGTH = 9999;
    public static final int MIN_DYNPERIOD = 4000;
    public static final int MAX_DYNPERIOD = 180000;
    public static final int MIN_ROD = 120;
    public static final int MAX_ROD = 400;

    private final DeviceStream mStream;
    private final byte mDeviceAddress;

    private Report mReport;

    public Ddin(DeviceStream mStream, byte mDeviceAddress) {
        this.mStream = mStream;
        this.mDeviceAddress = mDeviceAddress;
    }

    public String getName() throws Exception {
        return LiteModbus.readText(mStream, mDeviceAddress, NAME_ADDR);
    }

    public int getDeviceNumber() throws Exception {
        return LiteModbus.readInt(mStream, mDeviceAddress, DEVICE_NUMBER);
    }

    public short getDeviceId() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, DEVICE_ID);
    }

    public short getVoltage() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, VOLTAGE);
    }

    public short getTemperature() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, TEMPERATURE);
    }

    public float getLoad() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, LOAD);
    }

    public float getAcceleration() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, ACCELERATION);
    }

    public short getRodDiameter() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, ROD_DIAMETER);
    }

    public void setRodDiameter(short value) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, ROD_DIAMETER, value);
    }

    public int getDynPeriod() throws Exception {
        return LiteModbus.readInt(mStream, mDeviceAddress, DYN_PERIOD);
    }

    public void setDynPeriod(int value) throws Exception {
        LiteModbus.writeInt(mStream, mDeviceAddress, DYN_PERIOD, value);
    }

    public short getApertNumber() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, APERT_NUMBER);
    }

    public void setApertNumber(short value) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, APERT_NUMBER, value);
    }

    public short getImtravel() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, IMTRAVEL);
    }

    public void setImtravel(short value) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, IMTRAVEL, value);
    }

    public short getModelPump() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, MODEL_PUMP);
    }

    public void setModelPump(short value) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, MODEL_PUMP, value);
    }

    //энергозависимые параметры
    public float getNKP() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, NKP);
    }

    public void setNKP(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, NKP, value);
    }

    public float getRKP() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, RKP);
    }

    public void setRKP(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, RKP, value);
    }

    public float getG0() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, G0);
    }

    public void setG0(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, G0, value);
    }

    public float getG1() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, G1);
    }

    public void setG1(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, G1, value);
    }

    public int getTurnOnInterval() throws Exception {
        return LiteModbus.readInt(mStream, mDeviceAddress, TURN_ON_INTERVAL);
    }

    public void setTurnOnInterval(int value) throws Exception {
        LiteModbus.writeInt(mStream, mDeviceAddress, TURN_ON_INTERVAL, value);
    }

    public float getZeroOffset() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, ZERO_OFFSET);
    }

    public void setZeroOffset(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, ZERO_OFFSET, value);
    }

    public float getInclineKoef() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, INCLINE_KOEF);
    }

    public void setInclineKoef(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, INCLINE_KOEF, value);
    }

    public float getMinusG() throws Exception {
        return LiteModbus.readFloat(mStream, mDeviceAddress, G_MINUS);
    }

    public void setMinusG(float value) throws Exception {
        LiteModbus.writeFloat(mStream, mDeviceAddress, G_MINUS, value);
    }

    public boolean isTurnOffByTime() throws Exception {
        short turnOn = LiteModbus.readShort(mStream, mDeviceAddress, TURN_OFF_BY_TIME);
        if (turnOn == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setTurnOffByTime(boolean value) throws Exception {
        if (value) {
            LiteModbus.writeShort(mStream, mDeviceAddress, TURN_OFF_BY_TIME, (short) 0);
        } else {
            LiteModbus.writeShort(mStream, mDeviceAddress, TURN_OFF_BY_TIME, (short) 1);
        }
    }

    public void setCommand(short command) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, CONTROL_REG, command);
    }

    public short getStatus() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, STATUS_REG);
    }

    public short[] getData() throws Exception {
        byte[] data = new byte[mReport.mPeriod * 2];
        short[] result = new short[mReport.mPeriod];
        if (mReport.mPeriod > 500) {
            byte[] part1 = new byte[1000];
            byte[] part2 = new byte[mReport.mPeriod * 2 - 1000];
            int part2Address = DATA + 1000;
            LiteModbus.read(mStream, mDeviceAddress, DATA, part1);
            LiteModbus.read(mStream, mDeviceAddress, part2Address, part2);
            data = concat(part1, part2);
        } else {
            LiteModbus.read(mStream, mDeviceAddress, DATA, data);
        }
        for (int i = 0; i < result.length; i++) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(data, i * 2, 2);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            result[i] = byteBuffer.getShort();
        }
        return result;
    }

    public byte getError() throws Exception {
        byte[] data = new byte[1];
        LiteModbus.read(mStream, mDeviceAddress, ERROR, data);
        return data[0];
    }

    public Report getReport() throws Exception {
        byte[] data = new byte[14];
        Report report = new Report();
        ByteBuffer byteBuffer;
        LiteModbus.read(mStream, mDeviceAddress, REPORT_STRUCTURE, data);
        // MaxWeight
        byteBuffer = ByteBuffer.wrap(data, 0, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mMaxWeight = byteBuffer.getShort();
        // MinWeight
        byteBuffer = ByteBuffer.wrap(data, 2, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mMinWeight = byteBuffer.getShort();
        // mTravel
        byteBuffer = ByteBuffer.wrap(data, 4, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mTravel = byteBuffer.getShort();
        // mPeriod
        byteBuffer = ByteBuffer.wrap(data, 6, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mPeriod = byteBuffer.getShort();
        // mStep
        byteBuffer = ByteBuffer.wrap(data, 8, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mStep = byteBuffer.getShort();
        // mWeightDiscr
        byteBuffer = ByteBuffer.wrap(data, 10, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mWeightDiscr = byteBuffer.getShort();
        // mTimeDiscr
        byteBuffer = ByteBuffer.wrap(data, 12, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        report.mTimeDiscr = byteBuffer.getShort();
        mReport = report;
        return report;
    }

    private byte[] concat(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c= new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public class Report {
        public short mMaxWeight;
        public short mMinWeight;
        public short mTravel;
        public short mPeriod;
        public short mStep;
        public short mWeightDiscr;
        public short mTimeDiscr;
    }
}
