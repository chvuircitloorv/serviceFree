package com.example.chvui.siamservicebasic.data.device.protocol;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.chvui.siamservicebasic.data.device.sensors.Du;
import com.example.chvui.siamservicebasic.utils.Constant;
import com.example.chvui.siamservicebasic.utils.SensorCommandsUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by chvui on 23.10.2017.
 */

public class SensorThread implements ObservableCommand {

    public enum SensorType {Unknown, Du, Ddin, Automate}

    private static final UUID MY_UUID_SPP = UUID.fromString(Constant.BLUETOOTH_UUID);
    private static final long LONG_TIME_DELAY = 1500;
    private static final long SHORT_TIME_DELAY = 500;

    private BluetoothAdapter mAdapter;
    private BluetoothDevice mDevice;
    private ConnectThread mThread;
    private boolean mSearching;
    private SensorType mSensorType;

    private Queue<Command> mCommands;
    private List<ObserverCommand> mObserverList;

    public SensorThread(BluetoothAdapter mAdapter, SensorType sensorType) {
        this.mAdapter = mAdapter;
        this.mSensorType = sensorType;
        mCommands = new ArrayDeque<>();
        this.mObserverList = new ArrayList<>();
    }

    public void connect(String address) {
        mDevice = mAdapter.getRemoteDevice(address);
        if (mThread != null) {
            mThread.cancel();
        }
        mThread = new ConnectThread();
        mThread.start();
    }

    public synchronized boolean isConnected() {
        return mSearching;
    }

    private void setSearching(boolean isSearching) {
        mSearching = isSearching;
    }

    private class ConnectThread extends Thread {

        private BluetoothSocket mSocket;

        private Du mDu;

        public void run() {
            mAdapter.cancelDiscovery();
            createSocketConnection();
            connectingToSocket();

            commandProcessing();
            reconnect();
        }

        private void cancel() {
            try {
                if (mSocket != null) {
                    mSocket.close();
                }
                mSocket = null;
                mDu = null;
            } catch (IOException e) {
                Log.e(Constant.TAG, "close() of connect  socket failed", e);
            }
        }

        private void createSocketConnection() {
            try {
                mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID_SPP);
            } catch(IOException e) {
                Log.e(Constant.TAG, "Can't create socket connection", e);
            }
        }

        private void connectingToSocket() {
            setSearching(true);
            while (mSearching) {
                try {
                    mSocket.connect();
                    initSensor();
                    setSearching(false);
                } catch (Exception e) {
                    tryReconnectToSocket();
                }
            }
        }

        private void tryReconnectToSocket() {
            try {
                mSocket.close();
                createSocketConnection();
                Thread.sleep(LONG_TIME_DELAY);
                Log.d(Constant.TAG, "Can't connect to " + mDevice.getAddress());
            } catch (InterruptedException|IOException e) {
                Log.e(Constant.TAG, "Can't reconnect to socket", e);
            }
        }

        private void commandProcessing() {
            Command currentCommand;
            String result;
            while (!mSearching) {
                try {
                    if (mCommands.isEmpty()) {
                        Thread.sleep(LONG_TIME_DELAY);
                        mCommands.add(getStatusCommand());
                    } else {
                        Thread.sleep(SHORT_TIME_DELAY);

                        currentCommand = mCommands.poll();
                        currentCommand.execute();
                        result = currentCommand.result();

                        notifyObservers(SensorCommandsUtils.getCommand(currentCommand), result);

                        Log.d(Constant.TAG, "Command = " + currentCommand.toString() + " Result = " + result);
                    }
                } catch (Exception e) {
                    setSearching(true);
                }
            }
        }

        private void initSensor() throws IOException {
            switch (mSensorType) {
                case Unknown:
                    break;
                case Du:
                    mDu = new Du(new DeviceStream(mSocket.getInputStream(), mSocket.getOutputStream()), (byte) 1);
                    SensorCommandsUtils.initDuCommands(mDu);
                    break;
                case Ddin:
                    break;
                case Automate:
                    break;
            }
        }

        private Command getStatusCommand() {
            switch (mSensorType) {
                case Unknown:
                    return null;
                case Du:
                    return SensorCommandsUtils.getCommand(SensorCommandsUtils.CommandsList.DuVoltage);
                case Ddin:
                    return null;
                case Automate:
                    return null;
                default:
                    return null;
            }
        }

        private void reconnect() {
            cancel();
            connect(mDevice.getAddress());
        }
    }

    @Override
    public void register(ObserverCommand observer) {
        mObserverList.add(observer);
    }

    @Override
    public void remove(ObserverCommand observer) {
        mObserverList.remove(observer);
    }

    @Override
    public void notifyObservers(SensorCommandsUtils.CommandsList command, String result) {
        for(ObserverCommand observer: mObserverList) {
            observer.update(command, result);
        }
    }

    public synchronized void addCommand(SensorCommandsUtils.CommandsList command) {
        mCommands.add(SensorCommandsUtils.getCommand(command));
    }
}
