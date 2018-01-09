package com.example.chvui.siamservicebasic.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsView {

    @BindView(R.id.mail)
    EditText mMailEditText;
    @BindView(R.id.dbt)
    RadioButton mDbtRadioButton;
    @BindView(R.id.e)
    RadioButton mERadioButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    SettingsPresenter<SettingsView> mPresenter;

    public static Intent getCallingActivity(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mPresenter.onMailChanged(mMailEditText.getText().toString());
                }
                return false;
            }
        });

        mPresenter.onViewInitialized();
    }

    @Override
    public void showRecipientMail(String mail) {
        mMailEditText.setText(mail);
    }

    @Override
    public void showExportType(boolean isDbt) {
        if (isDbt) {
            mDbtRadioButton.setChecked(true);
        } else {
            mERadioButton.setChecked(true);
        }
    }

    @OnClick({R.id.dbt, R.id.e})
    void onClick() {
        mPresenter.onExportFormatChanged(mDbtRadioButton.isChecked());
    }
}
