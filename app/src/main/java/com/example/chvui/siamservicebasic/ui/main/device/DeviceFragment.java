package com.example.chvui.siamservicebasic.ui.main.device;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.di.component.ActivityComponent;
import com.example.chvui.siamservicebasic.ui.base.BaseFragment;
import com.example.chvui.siamservicebasic.ui.discovery.DiscoveryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DeviceFragment extends BaseFragment implements DeviceView {

    public static final String TAG = DeviceFragment.class.getSimpleName();

    @BindView(R.id.image_du)
    ImageView mDuImageView;
    @BindView(R.id.state_du)
    TextView mDuStateTextView;
    @BindView(R.id.voltage_du)
    TextView mDuVoltageTextView;
    @BindView(R.id.disconnect_du)
    ImageView mDuDisconnectImageView;
    @BindView(R.id.image_ddin)
    ImageView mDdinImageView;
    @BindView(R.id.state_ddin)
    TextView mDdinStateTextView;
    @BindView(R.id.voltage_ddin)
    TextView mDdinVoltageTextView;
    @BindView(R.id.disconnect_ddin)
    ImageView mDdinDisconnectImageView;
    @BindView(R.id.image_automate)
    ImageView mAutomateImageView;
    @BindView(R.id.state_automate)
    TextView mAutomateStateTextView;
    @BindView(R.id.voltage_automate)
    TextView mAutomateVoltageTextView;
    @BindView(R.id.disconnect_automate)
    ImageView mAutomateDisconnectImageView;

    private int mDuState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            //mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {

    }


    @Override
    public void setDuState(int state) {
        mDuState = state;
        switch (mDuState) {
            case DeviceView.NO_DEVICE:
                mDuImageView.setColorFilter(Color.GRAY);
                mDuStateTextView.setText(getString(R.string.no_device));
                mDuVoltageTextView.setText(getString(R.string.no_device_info));
                mDuDisconnectImageView.setVisibility(View.GONE);
                break;
            case DeviceView.CONNECTED:
                mDuImageView.setColorFilter(Color.GREEN);
                mDuStateTextView.setText(getString(R.string.connected));
                mDuDisconnectImageView.setVisibility(View.VISIBLE);
                break;
            case DeviceView.CONNECTING:
                mDuImageView.setColorFilter(Color.YELLOW);
                mDuStateTextView.setText(getString(R.string.connecting));
                mDuVoltageTextView.setText("");
                mDuDisconnectImageView.setVisibility(View.GONE);
                break;
            case DeviceView.CONNECTION_LOST:
                mDuImageView.setColorFilter(Color.RED);
                mDuStateTextView.setText(getString(R.string.connection_lost));
                mDuVoltageTextView.setText(getString(R.string.connection_lost_info));
                mDuDisconnectImageView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void showDuVoltage(String voltage) {
        mDuVoltageTextView.setText(voltage);
    }

    @Override
    public void setDdinState(int state) {

    }

    @Override
    public void showDdinVoltage(String voltage) {

    }

    @Override
    public void setDuAutomateState(int state) {
        
    }

    @Override
    public void showDuAutomateVoltage(String voltage) {

    }

    @OnClick(R.id.du)
    void onDuClick() {
        Intent intent = new Intent(getActivity(), DiscoveryActivity.class);
        startActivity(intent);
    }
}
