package com.example.chvui.siamservicebasic.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.chvui.siamservicebasic.App;
import com.example.chvui.siamservicebasic.di.component.ActivityComponent;
import com.example.chvui.siamservicebasic.di.component.DaggerActivityComponent;
import com.example.chvui.siamservicebasic.di.module.ActivityModule;
import com.example.chvui.siamservicebasic.ui.base.mvp.View;
import com.example.chvui.siamservicebasic.utils.CommonUtils;

import butterknife.Unbinder;

/**
 * Created by chvui on 25.09.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View, BaseFragment.Callback {

    protected abstract void setUp();

    private ProgressDialog mProgressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .applicationComponent(((App)getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }



    @Override
    public void showMessage(@StringRes int resId) {
        String message = getString(resId);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@StringRes int resId) {
        String message = getString(resId);
        showSnackBar(message);
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
/*        android.view.View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));*/
        snackbar.show();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    protected void setUnBinder(Unbinder unBinder) {
        mUnbinder = unBinder;
    }

    protected void setOrientationEnabled(boolean isOrientationEnabled) {
        if (isOrientationEnabled) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

}
