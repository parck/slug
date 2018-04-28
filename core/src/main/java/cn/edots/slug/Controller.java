package cn.edots.slug;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.Serializable;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public abstract class Controller<CA extends Activity, VDM extends ViewDataBinding> implements Serializable, View.OnClickListener {

    protected VDM viewDataModel;

    protected CA context;

    public abstract void onCreate(@Nullable Bundle savedInstanceState);

    public void onDestroy() {
        viewDataModel = null;
        context = null;
    }

    public VDM getViewDataModel() {
        return viewDataModel;
    }

    public void setViewDataModel(VDM viewDataModel) {
        this.viewDataModel = viewDataModel;
    }

    public CA getContext() {
        return context;
    }

    public void setContext(CA context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
    }
}