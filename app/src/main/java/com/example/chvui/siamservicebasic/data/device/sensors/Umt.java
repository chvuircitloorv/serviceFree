package com.example.chvui.siamservicebasic.data.device.sensors;

import com.example.chvui.siamservicebasic.data.device.protocol.DeviceStream;
import com.example.chvui.siamservicebasic.data.device.protocol.LiteModbus;

/**
 * Created by chvui on 14.11.2017.
 */

public final class Umt {

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
    //U16 Adrtek		// +0x0E – адрес последней (текущей) записи в cтранице (только чтение)
    private static final int ADR_TEK = 0x0000000E;
    //U16 ukstr			// +0x10 - адрес последней (текущей) страницы в флэш памяти (только чтение)
    private static final int UK_STR = 0x00000010;
    //U16 ukbl			// +0x12 – адрес последнего (текущего) блока в флэш памяти (только чтение)
    private static final int UK_UB = 0x00000012;
    //U16 page			// +0x14 – размер страницы, например 528 или 2112 (только чтение)
    private static final int PAGE = 0x00000014;
    //U16 kolstr			// +0x16 – количество страниц в блоке, например 8192 или 64 (только чтение)
    private static final int KOL_STR = 0x00000016;
    //U16 kolbl			// +0x18 – количество блоков в флэш памяти, например 1 или 8192 (только чтение)
    private static final int KOL_BL = 0x00000018;
    //U8 BaudRate		// +0x1A – скорость обмена по таблице: 0 – 9600, 1 – 19200, 2 – 38400,
    // 3 – 57600,    4 – 115200 бод, 5 – 230400 бод, 6 – 460800 бод, 7 - 921600 бод.
    // Начальная скорость обмена 9600 бод. (только запись)
    private static final int BOUND_RATE = 0x0000001A;
    //2.	С адреса 0x00001000 – справочные (информационные данные)0
    //U32 pVer;		// +0x00 – адрес строки с версией программы (только чтение)
    private static final int PROGRAM_VERSION = 0x00001000;
    //U16 VerSize;	// +0x04 – размер строки с версией (только чтение)
    private static final int PROGRAM_VERSION_SIZE = 0x00001004;
    //3.	С адреса 0x00002000 – таблица инвалидных блоков
    //Tabinv[kolbl/8] 	// +0x00 – номер инвалидного блока представлен побитно, например,
    // если Tabinv[0] младший бит = «1», то по  нулевому адресу блок – инвалидный, обходится.
    private static final int TAB_INV = 0x00002000;
    //4.	С адреса 0x00008000 – параметры прибора и исследования
    //U8 Vissl;		// +0x00 – вид исследования от 1 до 4.
    private static final int V_ISSL = 0x00008000;
    //Char kust[5];	// +0x01 – номер куста.
    private static final int KUST = 0x00008001;
    //Char skv[6];	// +0x06 – номер скважины.
    private static final int SKV = 0x00008006;
    //U16 field;		// +0x0C – код месторождения.
    private static final int FIELD = 0x0000800C;
    //U16 shop;		// +0x0E – номер цеха.
    private static final int SHOP = 0x0000800E;
    //U16 operator;	// +0x10 – номер оператора.
    private static final int OPERATOR = 0x00008010;
    //Float Noldav;	// +0x12 – смещение нуля датчика давления, (только чтение) , разрешение 0.001 атм.
    private static final int NOLDAV = 0x00008012;
    //U32 Interval;	// +0x16 – интервал замера. Дискрет - 100мкс.
    private static final int INTERVAL = 0x00008016;
    //U16 Revbit;	// +0x1A - Расшифровка битов REVBIT. D1 = 0 – записывается в память давление и
    // температура, D1 = 1 записывается только давление. D0=0 индикация в мегапаскалях,
    // D0=1 индикация в атмосферах. D3, D2	 - верхний предел датчика давления
    // 0    0  – 250 атм, 0    1  – 400 атм, 1    0  – 600 атм, 1    1  – 100 атм.
    // D14 = 0 пометка данных для последующей передачи через GSM модем включена.
    // D14 = 1 пометка данных для последующей передачи через GSM модем выключена.
    private static final int REV_BIT = 0x0000801A;
    //	// +0x1C – задается количество точек для видов исследования «3», «4», через которое
    // автоматически включится отправка через GSM-модем. Возможные значения от 1 до 999 точек.
    // Если старший бит D15==1, то автоматическая отправка данных через GSM модем отключена.
    private static final int CHER = 0x0000801C;
    //5.	С адреса 0x00008400 – текущие данные
    //Float Dav;		// +0x00 – давление (только чтение), разрешение 0.001 атм
    private static final int DAV = 0x00008400;
    //Float Temp 	// +0x04 - температура внутренняя (только чтение), разрешение 0.001 градусов
    // Цельсия, от -40 до +50 градусов Цельсия
    private static final int TEMP = 0x00008404;
    //Float ExTemp	// +0x08 - температура внешняя (только чтение), разрешение 0.001 градусов
    // Цельсия, от -55 до +125 градусов Цельсия, если значение -300, то внешний термозонд не подключен
    private static final int EX_TEMP = 0x00008408;
    //Float Acc;		// +0x0C – напряжение аккумулятора (только чтение), разрешение 0.01В
    private static final int ACC = 0x0000840C;
    //Float RaznR	// +0x10 - разность сопротивлений моста, Ом (только чтение)
    private static final int RAZN_R = 0x00008410;
    //Float ObshR	// +0x14 - общее сопротивление моста, Ом (только чтение)
    private static final int OBSH_R = 0x00008414;
    //U16 Emak;	// +0x18 – емкость аккумулятора, дискретность 1%  (только чтение)
    private static final int EM_AK = 0x00008418;
    //U16 Emem;	// +0x1A – количество свободной памяти, дискретность 0.1% (только чтение)
    private static final int E_MEM = 0x0000841A;
    //U16 Kolisl 	// +0x1C – количество исследований в памяти (только чтение), которое было до
    // циклического заполнения памяти
    private static final int KOL_ISL = 0x0000841C;
    //U16 schstr		// +0x1E – количество свободных страниц в памяти (только чтение)
    private static final int SCH_STR = 0x0000841E;
    //U16 schbl		// +0x20 – количество свободных блоков в памяти (только чтение)
    private static final int SCH_BL = 0x00008420;
    //U32 koliz		// +0x22 – количество свободных измерений в памяти (только чтение)
    private static final int KOL_IZ = 0x00008422;
    //TIME Time	// +0x26 – время и дата
    //Typedef struct {	BYTE	hour;	// час BYTE	min;	// минута BYTE	sec;	// секунда
    // BYTE date;	// число BYTE month; // месяц BYTE	year;	// год } TIME;
    private static final int TIME = 0x00008426;
    //TIMEost;		// +0x2C – сколько осталось времени до измерения при повторных замерах
    // (только чтение). Определение структуры TIMEost Typedef struct {	BYTE	hour;	// час
    // BYTE	min;	// минута BYTE	sec;	// секунда } TIMEost;
    private static final int TIME_OST = 0x0000842C;
    //6.	С адреса 0x00008800 – операционные регистры
    //U8 Op;		// +0x00 – запуск процесса (только запись): 1 – старт исследования,
    // 2 – запуск обнуления датчика давления, 3 – запуск инициализации (для проведения инициализации
    // послать команду дважды), 4 – обнуление состояния датчика SOSTDAT, 5 – выключить датчик.
    private static final int OP = 0x00008800;
    //U16 Sostdat	// +0x02 – состояние датчика SOSTDAT (только чтение): 0 – датчик ничего не делает,
    // 1 – датчик периодически замеряет данные.
    private static final int SOST_DAT = 0x00008802;
    //7.	С адреса 0x70000000 – располагается страница последнего измерения из фрам памяти,
    // которая может быть частично занята измерениями.0
    //Размер страницы задан в общих регистрах. Адрес последнего (текущего) измерения в этой странице
    // adrtek задан в общих регистрах. Если adrtek==0xFFFF, то записей во фрам и флэш памяти нет.
    // Если ((0x8000&re.adrtek)==0x8000), то записей во фрам памяти нет. 7FFF&adrtek указывает на
    // текущую запись в странице флэш памяти по указателям ukstr и ukbl. Для перехода к предыдущему
    // измерению необходимо считать адрес предыдущей записи adrpr, которая считывается из заголовка
    // исследования по адресу 7FFF&adrtek. Если adrpr==0x7FFF, то это первое измерение. Массив данных
    // переменной длинны, но заголовок исследования плюс массив данных не превышают размер страницы.
    private static final int LAST_MEASUREMENT = 0x70000000;
    //	С адреса 0x80000000 – заголовки исследований, символьные отчеты и данные из флеш памяти.
    // Память поделена на страницы и блоки. Размер страницы и блока заданы в общих регистрах. Адрес
    // последнего (текущего) блока ukbl и адрес последней (текущей) страницы ukstr заданы в общих регистрах.
    // Адрес рассчитывается по формуле 0x82000000+page*(ukstr + ukbl*kolstr). Предыдущие блоки и
    // страницы располагается по адресу меньше последних на block и page и т.д. Если номер блока
    // равен нулю, то предыдущий блок располагается по адресу 0x82000000 + page*kolstr*block.
    // Распределение памяти в блоке Заголовок исследования
    // N – номер байта
    // 0	U8		// - идентификатор наличия символьного отчета, если 0 или 0xFF, то нет символьного отчета
    // 1	U16 ksum	// - контрольная сумма символьного отчета и всех точек
    // 3	U16 adrpr	// – адрес предыдущей записи в блоке
    // 5	U16 koltoch	// – количество точек
    // koltoch=0 одиночный замер
    // koltoch=1 автоматический замер происходит запись первого измерения
    // koltoch=2,3,..,10 автоматический замер последующих измерений
    // 7	U16 kolstrt	// - количество страниц флэш памяти в текущем замере 1,2,3,..
    // 9	U16 kolblt	// - количество блоков флэш памяти в текущем замере 0,1,2,..
    // 11	U16 nomislt	// - номер текущего исследования 1,2,3,..
    // 13	U8 kolpar	// - количество измеряемых параметров
    // kolpar = 1 давление
    // kolpar = 2 давление, температура
    // kolpar = 3 давление, температура  внутренняя, температура внешняя
    // kolpar = 3 давление, температура  внутренняя, сопротивление моста для ТКД, если вид исследования = 5.
    // 14	U8 Vissl	// – вид исследования
    // Char kust[5]	// - номер куста
    // Char skv[6]	// - номер скважины
    // U16 field	// - код месторождения
    // U16 shop	// - номер цеха
    // U16 operator	// - номер оператора
    // 32	time[6]		// - TIME время начала записи
    // Определение структуры TIME Typedef struct {	BYTE	hour;	// час BYTE	min;	// минута
    // BYTE	sec;	// секунда BYTE date;	// число BYTE month; // месяц BYTE	year;	// год } TIME;
    // 38	U32 Interval;	// – интервал замера. Дискрет 100мкс
    // 42	Float Dav;	  // – давление (только чтение)
    // 46	Float Temp 	  // - температура внутренняя (только чтение), если kolpar=2,3
    // 50	Float ExTemp	  // - температура внешняя (только чтение), если kolpar=3
    // Если давление и температура типа long, то умноженные на 1000. Далее, если количество точек > 1
    // Если количество точек больше единицы, то если kolpar=1, то следующие данные по 4 байта на
    // точку (давление). Если kolpar=2, то следующие данные по 8 байт на точку (давление, температура).
    // Если kolpar=3, то по 12 байт на точку (давление, температура, температура внешняя).
    // Вторая точка
    // Float Dav;	  // – давление (только чтение)
    // Float Temp 	  // - температура внутренняя (только чтение)
    // Float ExTemp	  // - температура внешняя (только чтение)
    // ---------------------------------------
    // ---------------------------------------
    // Десятая точка
    private static final int MEASUREMENTS = 0x80000000;

    private final DeviceStream mStream;
    private final byte mDeviceAddress;


    public Umt(DeviceStream stream, byte deviceAddress) {
        mStream = stream;
        mDeviceAddress = deviceAddress;
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

    public short getAdrTek() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, ADR_TEK);
    }

    public short getUkStr() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, UK_STR);
    }

    public short getUkUb() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, UK_UB);
    }

    public short getPage() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, PAGE);
    }

    public short getKolStr() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, KOL_STR);
    }

    public short getKolBl() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, KOL_BL);
    }

    public void setBoundRate(byte number) throws Exception {
        LiteModbus.writeByte(mStream, mDeviceAddress, BOUND_RATE, number);
    }

    public String getProgramVersion() throws Exception {
        return LiteModbus.readText(mStream, mDeviceAddress, PROGRAM_VERSION);
    }

    public short getProgramVersionSize() throws Exception {
        return LiteModbus.readShort(mStream, mDeviceAddress, PROGRAM_VERSION_SIZE);
    }

    public byte getIssl() throws Exception {
        return LiteModbus.readByte(mStream, mDeviceAddress, PROGRAM_VERSION_SIZE);
    }

    public void setIssl(byte issl) throws Exception {
        LiteModbus.writeByte(mStream, mDeviceAddress, V_ISSL, issl);
    }


}
