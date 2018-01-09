package com.example.chvui.siamservicebasic.ui.base.mvp;

import android.support.annotation.StringRes;

/**
 * Created by chvui on 25.09.2017.
 */

public interface View {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void showMessage(@StringRes int resId);
}
