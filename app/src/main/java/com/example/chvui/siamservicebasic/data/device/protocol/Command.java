package com.example.chvui.siamservicebasic.data.device.protocol;

/**
 * Created by chvui on 25.10.2017.
 */

public interface Command {

    void execute() throws Exception;

    String result();
}
