package com.example.chvui.siamservicebasic.utils;

import com.example.chvui.siamservicebasic.data.device.commands.DuNameCommand;
import com.example.chvui.siamservicebasic.data.device.commands.DuStatusCommand;
import com.example.chvui.siamservicebasic.data.device.commands.DuVoltageCommand;
import com.example.chvui.siamservicebasic.data.device.protocol.Command;
import com.example.chvui.siamservicebasic.data.device.sensors.Du;

/**
 * Created by chvui on 25.10.2017.
 */

public final class SensorCommandsUtils {

    public enum CommandsList { DuVoltage, DuStatus, DuName }

    private static Command sDuVoltage;
    private static Command sDuStatus;
    private static Command sDuName;

    public static Command getCommand(CommandsList command) {
        switch (command) {
            case DuVoltage:
                return sDuVoltage;
            case DuStatus:
                return sDuStatus;
            case DuName:
                return sDuName;
            default:
                return null;
        }
    }

    public static CommandsList getCommand(Command command) {
        if (command instanceof DuVoltageCommand) {
            return CommandsList.DuVoltage;
        } else if (command instanceof DuStatusCommand) {
            return CommandsList.DuStatus;
        } else {
            return CommandsList.DuName;
        }
    }

    public static void initDuCommands(Du du) {
        sDuVoltage = new DuVoltageCommand(du);
        sDuStatus = new DuStatusCommand(du);
        sDuName = new DuNameCommand(du);
    }

}
