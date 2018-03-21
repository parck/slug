package cn.edots.slug;

import android.content.Context;
import java.io.Serializable;
import cn.edots.slug.model.ViewModel;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public abstract class Controller<VM extends ViewModel> implements Serializable {

    protected VM viewModel;

    protected Context context;

    public void destroy() {

    }

    public VM getViewModel() {
        return viewModel;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}