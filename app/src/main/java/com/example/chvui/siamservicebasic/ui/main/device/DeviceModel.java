package com.example.chvui.siamservicebasic.ui.main.device;

import com.example.chvui.siamservicebasic.ui.base.mvp.Model;

/**
 * Created by chvui on 05.12.2017.
 */

public interface DeviceModel extends Model {

    String getDuVoltage();
    int getDuState();
}
