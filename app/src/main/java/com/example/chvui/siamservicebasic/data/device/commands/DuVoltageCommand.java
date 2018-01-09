package com.example.chvui.siamservicebasic.data.device.commands;

import com.example.chvui.siamservicebasic.data.device.sensors.Du;
import com.example.chvui.siamservicebasic.data.device.protocol.Command;

/**
 * Created by chvui on 25.10.2017.
 */

public class DuVoltageCommand implements Command {

    private final Du mDu;
    private short mVoltage;

    public DuVoltageCommand(Du du) {
        mDu = du;
    }

    @Override
    public void execute() throws Exception {
        mVoltage = mDu.getVoltage();
    }

    @Override
    public String result() {
        return String.valueOf(mVoltage);
    }
}
