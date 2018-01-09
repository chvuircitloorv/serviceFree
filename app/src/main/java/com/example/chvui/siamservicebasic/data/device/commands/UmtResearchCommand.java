package com.example.chvui.siamservicebasic.data.device.commands;

import com.example.chvui.siamservicebasic.data.device.protocol.Command;
import com.example.chvui.siamservicebasic.data.device.sensors.Umt;

/**
 * Created by chvui on 14.11.2017.
 */

public class UmtResearchCommand implements Command {

    private final Umt mUmt;
    private byte mResearch;

    public UmtResearchCommand(Umt umt) {
        mUmt = umt;
    }

    @Override
    public void execute() throws Exception {
        mResearch = mUmt.getIssl();
    }

    @Override
    public String result() {
        return null;
    }
}
