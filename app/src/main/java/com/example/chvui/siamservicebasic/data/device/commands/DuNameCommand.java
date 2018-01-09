package com.example.chvui.siamservicebasic.data.device.commands;

import com.example.chvui.siamservicebasic.data.device.sensors.Du;
import com.example.chvui.siamservicebasic.data.device.protocol.Command;

/**
 * Created by chvui on 25.10.2017.
 */

public class DuNameCommand implements Command {

    private final Du mDu;
    private String mName;

    public DuNameCommand(Du du) {
        mDu = du;
    }

    @Override
    public void execute() throws Exception {
        mName = mDu.getName();
    }

    @Override
    public String result() {
        return mName;
    }
}
