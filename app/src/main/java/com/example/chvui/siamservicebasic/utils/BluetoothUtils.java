package com.example.chvui.siamservicebasic.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.Set;

/**
 * Created by chvui on 25.09.2017.
 */

public final class BluetoothUtils {

    private BluetoothUtils() {

    }

    private static BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();

    public static boolean isBluetoothAvailable() {
        return mAdapter != null;
    }

    public static boolean isBluetoothEnabled() {
        return mAdapter.isEnabled();
    }

    public static void startDiscovery() {
        if (!isBluetoothAvailable()) {
            return;
        }

        if (mAdapter.isDiscovering()) {
            mAdapter.cancelDiscovery();
        }
        mAdapter.startDiscovery();
    }

    public static Set<BluetoothDevice> getBoundedDevice() {
        return mAdapter.getBondedDevices();
    }

    public static Intent enableBluetooth() {
        return new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    }
}
