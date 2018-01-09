package com.example.chvui.siamservicebasic.di.component;

import com.example.chvui.siamservicebasic.App;
import com.example.chvui.siamservicebasic.data.pref.PreferencesManager;
import com.example.chvui.siamservicebasic.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chvui on 25.09.2017.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    PreferencesManager getPreferencesManager();
}
