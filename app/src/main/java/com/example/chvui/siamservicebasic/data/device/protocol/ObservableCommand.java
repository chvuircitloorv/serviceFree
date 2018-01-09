package com.example.chvui.siamservicebasic.data.device.protocol;

import com.example.chvui.siamservicebasic.utils.SensorCommandsUtils;

/**
 * Created by chvui on 26.10.2017.
 */

public interface ObservableCommand {

    void register(ObserverCommand observer);
    void remove(ObserverCommand observer);
    void notifyObservers(SensorCommandsUtils.CommandsList command, String result);

}
