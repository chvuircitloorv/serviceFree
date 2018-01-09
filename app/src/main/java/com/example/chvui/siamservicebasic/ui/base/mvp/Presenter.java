package com.example.chvui.siamservicebasic.ui.base.mvp;

/**
 * Created by chvui on 25.09.2017.
 */

public interface Presenter<V extends View> {

    void onAttach(V mvpView);

    void onDetach();
}
