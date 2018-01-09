package com.example.chvui.siamservicebasic.di.module;

import android.app.Application;
import android.content.Context;

import com.example.chvui.siamservicebasic.data.pref.AppPreferences;
import com.example.chvui.siamservicebasic.data.pref.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chvui on 25.09.2017.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }


    @Singleton
    @Provides
    PreferencesManager providePreferencesManager(AppPreferences appPreferences) {
        return appPreferences;
    }
}
