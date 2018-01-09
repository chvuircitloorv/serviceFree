package com.example.chvui.siamservicebasic.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.chvui.siamservicebasic.data.device.protocol.ObserverCommand;
import com.example.chvui.siamservicebasic.data.device.protocol.SensorThread;
import com.example.chvui.siamservicebasic.utils.SensorCommandsUtils;

public class SensorService extends Service {

    private SensorThread mDuThread;

    private final IBinder mBinder = new LocalBinder();

    public SensorService() {
        mDuThread = new SensorThread(BluetoothAdapter.getDefaultAdapter(), SensorThread.SensorType.Du);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    public class LocalBinder extends Binder {

        public SensorService getService() {
            return SensorService.this;
        }
    }

    public void startDu(String address) {
        mDuThread.connect(address);
    }

    public void addDuCommand(SensorCommandsUtils.CommandsList command) {
        mDuThread.addCommand(command);
    }

    public void addDuObserver(ObserverCommand observer) {
        mDuThread.register(observer);
    }

    public void removeDuObserver(ObserverCommand observer) {
        mDuThread.remove(observer);
    }
}
