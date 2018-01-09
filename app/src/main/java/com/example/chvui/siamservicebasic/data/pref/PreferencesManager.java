package com.example.chvui.siamservicebasic.data.pref;

/**
 * Created by chvui on 10.10.2017.
 */

public interface PreferencesManager {

    void setRecipientMail(String mail);

    String getRecipientMail();

    void setExportFormat(boolean isDbt);

    boolean isDbtExportFormat();

    void setDuAddress(String address);

    String getDuAddress();

}
