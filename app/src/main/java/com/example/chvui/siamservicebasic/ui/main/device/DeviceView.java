package com.example.chvui.siamservicebasic.ui.main.device;

import com.example.chvui.siamservicebasic.ui.base.mvp.View;

/**
 * Created by chvui on 12.10.2017.
 */

public interface DeviceView extends View {

    int NO_DEVICE = 1;

    int CONNECTED = 2;

    int CONNECTING = 3;

    int CONNECTION_LOST = 4;

    void setDuState(int state);

    void showDuVoltage(String voltage);

    void setDdinState(int state);

    void showDdinVoltage(String voltage);

    void setDuAutomateState(int state);

    void showDuAutomateVoltage(String voltage);
}
