package com.example.chvui.siamservicebasic.ui.base;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.utils.BluetoothUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chvui on 25.09.2017.
 */

public abstract class BluetoothActivity extends BaseActivity {

    private static int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_COARSE_PERMISSION = 2;
    private Set<BluetoothDevice> mDetectedDevices = new HashSet<>();

    public interface BluetoothDiscoveryListener {
        void onActionChanged(String action);
        void discoveredDevices(Set<BluetoothDevice> devices);
    }

    @Nullable
    private BluetoothDiscoveryListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkBluetoothModule();
        registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkBluetoothState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_CANCELED) {
                checkBluetoothState();
            }
            setOrientationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_COARSE_PERMISSION:
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    finish();
                }
                setOrientationEnabled(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
        mListener = null;
    }

    protected void startDiscovering() {
        BluetoothUtils.startDiscovery();
    }

    protected void setListener(@Nullable BluetoothDiscoveryListener listener) {
        mListener = listener;
    }

    protected Set<BluetoothDevice> getBoundedDevicies() {
        return BluetoothUtils.getBoundedDevice();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(mReceiver, intentFilter);
    }


    private void checkBluetoothModule() {
        if (!BluetoothUtils.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext(), getString(R.string.bluetooth_access_error), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            checkPermission();
        }
    }

    private void checkBluetoothState() {
        if (!BluetoothUtils.isBluetoothEnabled()) {
            setOrientationEnabled(false);
            startActivityForResult(BluetoothUtils.enableBluetooth(), REQUEST_ENABLE_BT);
        }
    }

    private void checkPermission() {
        if (!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            setOrientationEnabled(false);
            String[] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION };
            requestPermissionsSafely(permissions, REQUEST_COARSE_PERMISSION);
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        private String mAction = "";

        public void onReceive(Context context, Intent intent) {
            String newAction = intent.getAction();
            actionChangeCheck(newAction);
            mAction = newAction;

            if (BluetoothDevice.ACTION_FOUND.equals(mAction)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDetectedDevices.add(device);
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(mAction)) {
                if(mListener != null) {
                    mListener.discoveredDevices(mDetectedDevices);
                }
            }
        }

        private void actionChangeCheck(String newAction) {
            if (!mAction.equals(newAction)) {
                if (mListener != null) {
                    mListener.onActionChanged(newAction);
                }
            }
        }
    };
}
