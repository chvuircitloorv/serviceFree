package com.example.chvui.siamservicebasic.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by chvui on 10.10.2017.
 */
@Singleton
public class AppPreferences implements PreferencesManager {

    private static final String PREF_NAME = "siam_service_basic_pref";

    private static final String PREF_KEY_RECIPIENT_MAIL = "PREF_KEY_RECIPIENT_MAIL";
    private static final String PREF_KEY_EXPORT_FORMAT = "PREF_KEY_EXPORT_FORMAT";
    private static final String PREF_KEY_DU_ADDRESS = "PREF_KEY_DU_ADDRESS";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferences(Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setRecipientMail(String mail) {
        mPrefs.edit().putString(PREF_KEY_RECIPIENT_MAIL, mail).apply();
    }

    @Override
    public String getRecipientMail() {
        return mPrefs.getString(PREF_KEY_RECIPIENT_MAIL, null);
    }

    @Override
    public void setExportFormat(boolean isDbt) {
        mPrefs.edit().putBoolean(PREF_KEY_EXPORT_FORMAT, isDbt).apply();
    }

    @Override
    public boolean isDbtExportFormat() {
        return mPrefs.getBoolean(PREF_KEY_EXPORT_FORMAT, true);
    }

    @Override
    public void setDuAddress(String address) {
        mPrefs.edit().putString(PREF_KEY_DU_ADDRESS, address).apply();
    }

    @Override
    public String getDuAddress() {
        return mPrefs.getString(PREF_KEY_DU_ADDRESS, null);
    }
}
