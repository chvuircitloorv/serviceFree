package com.example.chvui.siamservicebasic.di.module;

import com.example.chvui.siamservicebasic.di.PerActivity;
import com.example.chvui.siamservicebasic.ui.settings.SettingsModel;
import com.example.chvui.siamservicebasic.ui.settings.SettingsModelImpl;
import com.example.chvui.siamservicebasic.ui.settings.SettingsPresenter;
import com.example.chvui.siamservicebasic.ui.settings.SettingsPresenterImpl;
import com.example.chvui.siamservicebasic.ui.settings.SettingsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chvui on 25.09.2017.
 */

@Module
public class ActivityModule {

    @Provides
    @PerActivity
    SettingsModel provideSettingsModel(
            SettingsModelImpl model) {
        return model;
    }

    @Provides
    @PerActivity
    SettingsPresenter<SettingsView> provideSettingsPresenter(
            SettingsPresenterImpl<SettingsView> presenter) {
        return presenter;
    }
}
