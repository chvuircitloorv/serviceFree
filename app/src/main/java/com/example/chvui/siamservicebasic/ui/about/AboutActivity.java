package com.example.chvui.siamservicebasic.ui.about;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.ui.base.BaseActivity;
import com.example.chvui.siamservicebasic.utils.NavigatorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.call)
    LinearLayout mCallLayout;
    @BindView(R.id.web)
    LinearLayout mWebLayout;
    @BindView(R.id.map)
    LinearLayout mMapLayout;
    @BindView(R.id.description)
    LinearLayout mDescriptionLayout;
    @BindView(R.id.fab)
    FloatingActionButton mMailButton;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setUnBinder(ButterKnife.bind(this));

        checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mCallLayout.setEnabled(false);
                }
                setOrientationEnabled(true);
                break;
        }
    }

    @Override
    protected void setUp() {

    }

    @OnClick({R.id.call, R.id.map, R.id.web, R.id.fab, R.id.toolbar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                NavigatorUtils.navigateToCallActivity(this, getString(R.string.phone_number));
                break;
            case R.id.map:
                NavigatorUtils.navigateToMapActivity(this, getString(R.string.company_latitude),
                        getString(R.string.company_longitude));
                break;
            case R.id.web:
                NavigatorUtils.navigateToWebActivity(this, getString(R.string.web_value));
                break;
            case R.id.fab:
                NavigatorUtils.navigateToSendMailActivity(this, getString(R.string.email));
                break;
            case R.id.toolbar:
                finish();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        if (!hasPermission(Manifest.permission.CALL_PHONE)) {
            setOrientationEnabled(false);
            String[] permissions = { Manifest.permission.CALL_PHONE };
            requestPermissionsSafely(permissions, REQUEST_CALL_PERMISSION);
        }
    }
}
