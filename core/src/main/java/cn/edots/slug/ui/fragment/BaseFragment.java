package cn.edots.slug.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edots.slug.BuildConfig;
import cn.edots.slug.core.FinishParameter;
import cn.edots.slug.R;
import cn.edots.slug.Standardize;
import cn.edots.slug.annotation.BindLayout;
import cn.edots.slug.core.log.Logger;
import cn.edots.slug.ui.BaseActivity;
import cn.edots.slug.ui.TitleBarActivity;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public abstract class BaseFragment<VDB extends ViewDataBinding> extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();

    protected Fragment THIS;
    protected Activity activity;
    protected View rootView;
    protected Logger logger;
    protected
    @DrawableRes
    int defaultBackIconRes = R.drawable.default_back_icon;
    protected boolean defaultDebugMode = BuildConfig.DEBUG;
    protected VDB viewDataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        THIS = this;
        activity = THIS.getActivity();
        try {
            ApplicationInfo appInfo = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                int resId = metaData.getInt(TitleBarActivity.DEFAULT_BACK_ICON);
                if (resId != 0) defaultBackIconRes = resId;
                defaultDebugMode = metaData.getBoolean(BaseActivity.DEFAULT_DEBUG_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger = Logger.getInstance(TAG, defaultDebugMode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        BindLayout layoutResId = this.getClass().getAnnotation(BindLayout.class);
        if (layoutResId != null && layoutResId.value() != 0) {
            viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId.value(), container, false);
            rootView = viewDataBinding.getRoot();
        } else return null; // throw exception
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) activity.getIntent().getSerializableExtra(BaseActivity.INTENT_DATA));
            ((Standardize) THIS).initView();
            ((Standardize) THIS).setListeners();
            ((Standardize) THIS).onCreateLast();
        }
    }

    public <V extends View> V findViewById(@IdRes int id) {
        return (V) rootView.findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    public void TOAST(CharSequence message) {
        Toast.makeText(THIS.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
        Toast.makeText(THIS.getActivity(), message, duration).show();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        THIS.startActivity(new Intent(THIS.getActivity(), clazz));
    }

    public void startActivity(Class<? extends Activity> clazz, HashMap<String, Object> data) {
        Intent intent = new Intent(THIS.getActivity(), clazz);
        intent.putExtra(BaseActivity.INTENT_DATA, data);
        THIS.startActivity(intent);
    }

    public boolean isBackAndExit() {
        return false;
    }

    public void onBack() {
        this.getActivity().finish();
    }

    public void onExit() {
        Intent finishIntent = new Intent();
        finishIntent.setAction(BaseActivity.EXIT_ACTION);
        finishIntent.putExtra(BaseActivity.FINISH_PARAMETER_INTENT_DATA, new FinishParameter());
        THIS.getActivity().sendBroadcast(finishIntent);
    }

    public VDB getViewDataBinding() {
        return this.viewDataBinding;
    }
}
