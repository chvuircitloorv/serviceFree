package com.example.chvui.siamservicebasic.di.component;

import com.example.chvui.siamservicebasic.ui.main.MainActivity;
import com.example.chvui.siamservicebasic.di.PerActivity;
import com.example.chvui.siamservicebasic.di.module.ActivityModule;
import com.example.chvui.siamservicebasic.ui.main.device.DeviceFragment;
import com.example.chvui.siamservicebasic.ui.settings.SettingsActivity;

import dagger.Component;

/**
 * Created by chvui on 25.09.2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SettingsActivity activity);

    void inject(DeviceFragment fragment);
}
