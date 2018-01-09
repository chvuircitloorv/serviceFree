package com.example.chvui.siamservicebasic.data.device.sensors;

import com.example.chvui.siamservicebasic.data.device.protocol.DeviceStream;
import com.example.chvui.siamservicebasic.data.device.protocol.LiteModbus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by chvui on 23.10.2017.
 */

public final class Du {

    //1.	С адреса 0x00000000 – общие регистры
    //U16 Device Type;	// +0x00 – тип прибора (только чтение), 0x1101
    private static final int TYPE = 0x00000000;
    //U16 MemModel;		// +0x02 – версия модели данных (только чтение), 0x0001
    private static final int DATA_MODEL = 0x00000002;
    //U32pDeviceName;	// +0x04 – адрес названия прибора (только чтение)
    private static final int DEVICE_NAME = 0x00000004;
    //U16DeviceNameSize;	// +0x08 – размер названия прибора (только чтение)
    private static final int DEVICE_NAME_SIZE = 0x00000008;
    //U32 DeviceNum;		// +0x0A – заводской номер
    private static final int DEVICE_NUM = 0x0000000A;

    //2.	С адреса 0x00001000 – справочные (информационные данные)0
    //U32 pVer;		// +0x00 – адрес строки с версией программы (только чтение)
    private static final int PROGRAM_VERSION = 0x00001000;
    //U16 VerSize;	// +0x04 – размер строки с версией (только чтение)
    private static final int PROGRAM_VERSION_SIZE = 0x00001004;

    //3.	С адреса 0x00008000 – параметры прибора и исследования
    //U16 Chdav;	// +0x00 – чувствительность датчика давления, 0.1мВ/100атм
    private static final int SENSOR_SENSITIVITY = 0x00008000;
    //U16 Chpiezo;	// +0x02 – чувствительность пьезодатчика в условных единицах от 0 до 255.
    private static final int PIEZOSENSOR_SENSITIVITY = 0x00008002;
    //U16 Noldav;	// +0x04 – смещение нуля датчика давления , 0.1атм (только чтение).
    private static final int ZERO_OFFSET_OF_THE_PRESSURE_SENSOR = 0x00008004;
    //U16 Nolexo;	// +0x06 – смещение нуля канала усиления сигнала с пьезодатчика
    //(только чтение). В абсолютных единицах. При выводе на индикатор в уровнемере СУДОС-мастер извлекается корень квадратный.
    private static final int ZERO_OFFSET_OF_THE_AMPLIFIER_CHANNEL = 0x00008006;
    //U16 Revbit;	// +0x08 - Расшифровка битов REVBIT.
    // D6 - максимальный уровень при скорости звука 341м/с 0 – 3000м, 1 – 6000м.
    // D0=0 – выпуск газа из скважины. D0=1 – впуск газа в скважину (ГАИ-1, баллон с газом).
    // D9=0 – дополнительное усиление выключено. D9=1 – дополнительное усиление включено.
    // Неиспользуемые биты должны быть равны нулю.
    private static final int REVBIT = 0x00008008;

    //4.	С адреса 0x00008400 – текущие данные
    //U16 Acc;		// +0x00 – напряжение аккумулятора, 0.1В (только чтение)
    private static final int BATTERY_VOLTAGE = 0x00008400;
    //U16 Dav;		// +0x04 – давление, 0.1атм (только чтение)
    private static final int PRESSURE = 0x00008404;

    //5.	С адреса 0x00008800 – операционные регистры
    //U8 Op;		// +0x00 – запуск процесса (только запись):
    // 1 – запуск измерения уровня,
    // 2 – запуск обнуления датчика давления,
    // 3 – запуск инициализации (для проведения инициализации послать команду дважды),
    // 4 – обнуление состояния датчика SOSTDAT,
    // 5 – выключить датчик.
    private static final int OP = 0x00008800;
    //U16 Sostdat	// +0x02 – состояние датчика SOSTDAT (только чтение):
    // 0 – датчик ничего не делает, 1 – датчик измеряет шумы в скважине в течение
    // 1 секунды после запуска измерения уровня,
    // 2 – датчик ожидает нажатия на ручной клапан,
    // 3 – производится запись эхограммы 18 секунд для 3000метров и 36 секунд для 6000 метров,
    // 4 – измерение уровня закончено, но не считано (SOSTDAT=4 сохраняется во фрам и не стирается при выключении питания).
    private static final int SOSTDAT = 0x00008802;

    //6.	  адреса 0x80000000 – заголовок исследования
/*    ECHO_HEAD Head;
    // структура ECHO_HEAD
    Typedef struct
    {
        U16 Urov;		// уровень в метрах при скорости звука 341,333м/сек ,
        U16 otr;		// количество отражений. Максимальное количество 99.
    }  ECHO_HEAD;  // вся структура, только чтение целиком, по частям нельзя*/
    private static final int RESEARCH_HEADER = 0x80000000;

    //7.	С адреса 0x81000000 – данные исследования
    //U8 Data[3000]; - эхограмма. Эхограмму можно считывать частями. Каждый байт эхограммы соответствует одному дискрету амплитуды.
    // Отрицательная амплитуда кодируется “1” в старшем бите. Разрешение равно одному метру. Передается 3000 байт, которые соответствуют 3000 метрам.
    // Диапазон значений амплитуд от 0 до 126 единиц в упакованном формате. При упаковке взята степень 0.35.
    // Для получения реальной амплитуды нужно амплитуду возвести в степень 1/0.35.
    // Для удобства визуализации обычно в базе данных показывается корень квадратный от реальной амплитуды.
    // При этом видно больше отражений сигнала и не требуется большое разрешение дисплея по вертикали.
    // В уровнемере СУДОС-мастер при визуализации амплитуда не возводится в степень и показываются условные единицы амплитуды  в степени 0.5/0.35.
    // Корень квадратный от реальной амплитуды сигнала принят для проверки по техническим условиям,
    // указана зона допустимых значений чувствительности пьезодатчика. Дискретность эхограммы рассчитывается из фиксированной скорости звука 341,333 м в сек.
    // Дискретность для 1 метра = 2/341,33333=5.85938 мс.
    private static final int RESEARCH = 0x81000000 ;

    public static final short START_MEASUREMENT = 1;
    public static final short RESET_PRESSURE = 2;
    public static final short INITIALISATION = 3;
    public static final short RESET_SOSTDATA = 4;
    public static final short TURN_OFF = 5;

    private static final int MIN_LEVEL = 3000;
    private static final int MAX_LEVEL = 6000;
    private static final int DOTS_COUNT = 3000;

    private final DeviceStream mStream;
    private final byte mDeviceAddress;

    public Du(DeviceStream mStream, byte mDeviceAddress) {
        this.mStream = mStream;
        this.mDeviceAddress = mDeviceAddress;
    }

    public short getDeviceType() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, TYPE);
    }

    public short getDataModel() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, DATA_MODEL);
    }

    public short getNameLegth() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, DEVICE_NAME_SIZE);
    }

    public String getName() throws Exception {
        return LiteModbus.readText(mStream, mDeviceAddress, DEVICE_NAME);
    }

    public int getFactoryNumber() throws Exception {
        return LiteModbus.readInt(mStream, mDeviceAddress, DEVICE_NUM);
    }

    public void setFactoryNumber(int number) throws Exception {
        LiteModbus.writeInt(mStream, mDeviceAddress, DEVICE_NUM, number);
    }

    public String getProgramVersion() throws Exception {
        return LiteModbus.readText(mStream, mDeviceAddress, PROGRAM_VERSION);
    }

    public short getProgramVersionSize() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, PROGRAM_VERSION_SIZE);
    }

    public short getSensorSensitivity() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, SENSOR_SENSITIVITY);
    }

    public void setSensorSensitivity(short sensitivity) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, SENSOR_SENSITIVITY, sensitivity);
    }

    public short getPiezoSensorSensitivity() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, PIEZOSENSOR_SENSITIVITY);
    }

    public void setPiezoSensorSensitivity(short number) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, PIEZOSENSOR_SENSITIVITY, number);
    }

    public short getPressureOffset() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, ZERO_OFFSET_OF_THE_PRESSURE_SENSOR);
    }

    public double getAmplifierChannelOffset() throws Exception {
        short offset = LiteModbus.readShort(mStream, mDeviceAddress, ZERO_OFFSET_OF_THE_AMPLIFIER_CHANNEL);
        return Math.sqrt(Math.abs(offset));
    }

    public short getMaxLevel() throws Exception {
        return (short) (getBit(getRevbit(), 6) ? MAX_LEVEL : MIN_LEVEL);
    }

    public void setMaxLevel(boolean is6000) throws Exception {
        if (is6000) {
            setRevbit((short) turnOnBit(getRevbit(), 6));
        } else {
            setRevbit((short) turnOffBit(getRevbit(), 6));
        }
    }

    public boolean isGasInWell() throws Exception {
        return getBit(getRevbit(), 0);
    }

    public void setGasInWell(boolean inWell) throws Exception {
        if (inWell) {
            setRevbit((short) turnOnBit(getRevbit(), 0));
        } else {
            setRevbit((short) turnOffBit(getRevbit(), 0));
        }
    }

    public boolean isAdditionalGain() throws Exception {
        return getBit(getRevbit(), 9);
    }

    public void setAdditionalGain(boolean isAdditionalGain) throws Exception {
        if (isAdditionalGain) {
            setRevbit((short) turnOnBit(getRevbit(), 9));
        } else {
            setRevbit((short) turnOffBit(getRevbit(), 9));
        }
    }

    public short getVoltage() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, BATTERY_VOLTAGE);
    }

    public short getPressure() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, PRESSURE);
    }

    public void setCommand(short command) throws Exception {
        if (command == Du.INITIALISATION) {
            LiteModbus.writeShort(mStream, mDeviceAddress, OP, command);
        }
        LiteModbus.writeShort(mStream, mDeviceAddress, OP, command);
    }

    public short getState() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, SOSTDAT);
    }

    public short getLevel() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.wrap(getResearchHeader(), 0, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort();
    }

    public short getEcho() throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.wrap(getResearchHeader(), 2, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort();
    }

    public byte[] getEchogram() throws Exception {
        byte[] data = new byte[DOTS_COUNT];
        LiteModbus.read(mStream, mDeviceAddress, RESEARCH, data);
        return data;
    }

    private byte[] getResearchHeader() throws Exception {
        byte[] data = new byte[4];
        LiteModbus.read(mStream, mDeviceAddress, RESEARCH_HEADER, data);
        return data;
    }

    private short getRevbit() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, REVBIT);
    }

    private void setRevbit(short number) throws Exception {
        LiteModbus.writeShort(mStream, mDeviceAddress, REVBIT, number);
    }

    private boolean getBit(int number, int position)
    {
        return ((number >> position) & 1) == 1;
    }

    private int turnOnBit(int number, int position) {
        return number | (int) Math.pow(2, position);
    }

    private int turnOffBit(int number, int position) {
        return number & ~(int) Math.pow(2, position);
    }
}
