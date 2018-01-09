package com.example.chvui.siamservicebasic;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.chvui.siamservicebasic.data.device.protocol.ObserverCommand;
import com.example.chvui.siamservicebasic.data.pref.PreferencesManager;
import com.example.chvui.siamservicebasic.di.component.ApplicationComponent;
import com.example.chvui.siamservicebasic.di.component.DaggerApplicationComponent;
import com.example.chvui.siamservicebasic.di.module.ApplicationModule;
import com.example.chvui.siamservicebasic.service.SensorService;
import com.example.chvui.siamservicebasic.ui.settings.SettingsActivity;
import com.example.chvui.siamservicebasic.utils.Constant;
import com.example.chvui.siamservicebasic.utils.SensorCommandsUtils;

import javax.inject.Inject;

/**
 * Created by chvui on 25.09.2017.
 */

public class App extends Application {

    private ApplicationComponent mApplicationComponent;
    private SensorService mService;

    @Inject
    PreferencesManager mPreferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        Intent intent = new Intent(this, SensorService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpNotification();
        }*/
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Inject
    public SensorService getService() {
        return mService;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive (Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                        == BluetoothAdapter.STATE_OFF)
                    Toast.makeText(App.this, "Turned off", Toast.LENGTH_SHORT).show();
            }

        }

    };

    ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SensorService.LocalBinder binder = (SensorService.LocalBinder) service;
            mService = binder.getService();
            mService.addDuObserver(mDuObservable);

            String address = mPreferencesManager.getDuAddress();
            //address = "00:16:A4:70:83:6E"; - umt
            //address = "00:16:A4:0F:C0:94"; - level
            if (address != null) {
                mService.startDu(address);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(Constant.TAG, "Can't start service " + String.valueOf(name));
        }
    };


/*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(this, SettingsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action action = new Notification.Action.Builder(R.drawable.ic_du_24dp, "Action", pendingIntent).build();
        builder.addAction(action);
        builder.setContentTitle("Заголовок").setContentText("Текст").setSmallIcon(R.drawable.ic_du_24dp);
        notificationManager.notify(1, builder.build());
    }*/

    ObserverCommand mDuObservable = new ObserverCommand() {
        @Override
        public void update(SensorCommandsUtils.CommandsList commands, String result) {
            Log.d(Constant.TAG, "Command = " + commands.toString() + " Result = " + result);
        }
    };
}
