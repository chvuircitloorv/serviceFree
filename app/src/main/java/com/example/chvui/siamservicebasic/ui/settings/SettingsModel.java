package com.example.chvui.siamservicebasic.ui.settings;

/**
 * Created by chvui on 10.10.2017.
 */

public interface SettingsModel {

    void setRecipientMail(String mail);

    String getRecipientMail();

    void setExportFormat(boolean isDbt);

    boolean isDbtExportFormat();
}
