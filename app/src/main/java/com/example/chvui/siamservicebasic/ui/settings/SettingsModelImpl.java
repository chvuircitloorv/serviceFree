package com.example.chvui.siamservicebasic.ui.settings;

import com.example.chvui.siamservicebasic.data.pref.PreferencesManager;

import javax.inject.Inject;

/**
 * Created by chvui on 10.10.2017.
 */

public class SettingsModelImpl implements SettingsModel {

    @Inject
    PreferencesManager mPreferencesManager;

    @Inject
    SettingsModelImpl() {

    }

    @Override
    public void setRecipientMail(String mail) {
        mPreferencesManager.setRecipientMail(mail);
    }

    @Override
    public String getRecipientMail() {
        return mPreferencesManager.getRecipientMail();
    }

    @Override
    public void setExportFormat(boolean isDbt) {
        mPreferencesManager.setExportFormat(isDbt);
    }

    @Override
    public boolean isDbtExportFormat() {
        return mPreferencesManager.isDbtExportFormat();
    }
}
