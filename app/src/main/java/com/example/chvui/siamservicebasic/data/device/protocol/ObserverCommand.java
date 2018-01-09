package com.example.chvui.siamservicebasic.data.device.protocol;

import com.example.chvui.siamservicebasic.utils.SensorCommandsUtils;

/**
 * Created by chvui on 26.10.2017.
 */

public interface ObserverCommand {
    void update(SensorCommandsUtils.CommandsList commands, String result);
}
