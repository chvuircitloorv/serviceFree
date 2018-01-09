package com.example.chvui.siamservicebasic.ui.settings;

import com.example.chvui.siamservicebasic.ui.base.mvp.Presenter;

/**
 * Created by chvui on 11.10.2017.
 */

public interface SettingsPresenter<V extends SettingsView> extends Presenter<V> {

    void onViewInitialized();

    void onMailChanged(String mail);

    void onExportFormatChanged(boolean isDbt);
}
