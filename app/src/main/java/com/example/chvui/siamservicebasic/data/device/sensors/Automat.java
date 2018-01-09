package com.example.chvui.siamservicebasic.data.device.sensors;

import com.example.chvui.siamservicebasic.data.device.protocol.DeviceStream;
import com.example.chvui.siamservicebasic.data.device.protocol.LiteModbus;

/**
 * Created by chvui on 20.02.2017.
 */

public final class Automat {

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

    //2
    // +0x08 - Расшифровка битов REVBIT. D5 = 0 - переход в КВД из КВУ, КПУ при уровне меньше 50м
    // и количестве отражений больше 8 разрешен. D2 = 0 – длительность срабатывания автоматического
    // клапана - 0.2 секунд, D2 = 1 - 0.05 секунд. D1 = 1 – разрешено срабатывания автоматического
    // клапана. D15 = 1 – датчик устанавливается в сервисный режим. Неиспользуемые биты должны быть
    // равны нулю.
    private static final int REVBIT = 0x00008000;

    // – вид исследования от 1 до 5.
    private static final int VISSL = 0x0000800A;

    // Char kust[5];	// +0x0B – номер куста.
    private static final int KUST = 0x0000800B;

    //Char skv[6];	// +0x10 – номер скважины.
    private static final int SKV = 0x00008010;

    //U16 field;		// +0x16 – код месторождения.
    private static final int FIELD = 0x00008016;

    //U16 shop;		// +0x18 – номер цеха.
    private static final int SHOP = 0x00008018;

    // U16 operator;	// +0x1A – номер оператора.
    private static final int OPERATOR = 0x0000801A;

    //U16 vzvuk;	// +0x1C – скорость звука 0.1м/сек.
    private static final int VZVUK = 0x0000801C;

    //U16 Ntpop;	// +0x1E – номер таблицы поправок.
    private static final int NTPOP = 0x0000801E;

    //U8 perp;		// +0x20 – период в КВД задается от 0 до 19, результат берется из таблицы.
    // __flash const int tabper[]={1,2,3,4,5,7,10,15,20,30,40,60,90,120,180,
    //240,300,420,600,720};
    private static final int PERP = 0x00008020;

    //U8 kolp;		// +0x21 – количество измерений в КВД задается от 0 до 24, результат берется из таблицы.
    //__flash const int tbkol[]={0,1,2,3,4,5,7,10,15,20,30,40,50,70,100,150,200,300,400,
    // 500,600,700,800,900,0xffff};
    private static final int KOLP = 0x00008021;

    //U8 peru[5];	// +0x22 – период для пяти интервалов в КВУ, КПУ берется из таблицы int tabper[].
    private static final int PERU = 0x00008022;

    //U8 kolur[5];	// +0x27 – количество измерений для пяти интервалов в КВУ, КПУ задается от 0 до
    //// 24, результат берется из таблицы int tbkol[], исключая период 1 минута.
    private static final int KOLUR = 0x00008027;

    //U16 Rterm[3];	// +0x2C – термокоэффициенты, сопротивление тензомоста (0,..,1024) 512 -
    // среднее значение. Номер элемента массива [0] – соответствует – 40 градусов, [1] –
    // соответствует +20 градусов, [2] – соответствует +60 градусов.
    private static final int RTERM = 0x0000802C;

    //U16 Pterm[3];	// +0x32 – термокоэффициенты, давление, 0.1 атм + 200. 0 атм.
    // соответствует 200. 10 атм. соответствует 300.
    private static final int PTERM = 0x00008032;

    private final DeviceStream mStream;
    private final byte mDeviceAddress;

    public Automat(DeviceStream mStream, byte mDeviceAddress) {
        this.mStream = mStream;
        this.mDeviceAddress = mDeviceAddress;
    }

    public String getName() throws Exception {
        return LiteModbus.readText(mStream, mDeviceAddress, DEVICE_NAME);
    }

}
