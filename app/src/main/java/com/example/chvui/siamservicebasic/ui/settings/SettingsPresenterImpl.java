package com.example.chvui.siamservicebasic.ui.settings;

import com.example.chvui.siamservicebasic.R;

import javax.inject.Inject;

/**
 * Created by chvui on 11.10.2017.
 */

public class SettingsPresenterImpl<V extends SettingsView> implements SettingsPresenter<V> {

    @Inject
    SettingsModel mModel;

    private SettingsView mView;

    @Inject
    SettingsPresenterImpl() {

    }

    @Override
    public void onAttach(V mvpView) {
        mView = mvpView;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void onViewInitialized() {
        if (mView != null) {
            mView.showRecipientMail(mModel.getRecipientMail());
            mView.showExportType(mModel.isDbtExportFormat());
        }
    }

    @Override
    public void onMailChanged(String mail) {
        mModel.setRecipientMail(mail);
        mView.showMessage(R.string.saved);
    }

    @Override
    public void onExportFormatChanged(boolean isDbt) {
        mModel.setExportFormat(isDbt);
    }
}
