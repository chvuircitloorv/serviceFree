package com.example.chvui.siamservicebasic.ui.settings;

import com.example.chvui.siamservicebasic.ui.base.mvp.View;

/**
 * Created by chvui on 11.10.2017.
 */

public interface SettingsView extends View {

    void showRecipientMail(String mail);

    void showExportType(boolean isDbt);
}
