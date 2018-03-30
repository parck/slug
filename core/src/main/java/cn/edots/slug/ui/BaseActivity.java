package cn.edots.slug.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.edots.slug.BuildConfig;
import cn.edots.slug.Controller;
import cn.edots.slug.core.FinishParameter;
import cn.edots.slug.core.FinishReceiver;
import cn.edots.slug.Standardize;
import cn.edots.slug.annotation.BindLayout;
import cn.edots.slug.core.ControllerProvider;
import cn.edots.slug.core.log.Logger;
import cn.edots.slug.model.Protocol;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener {

    public static final String EXIT_ACTION = "EXIT_ACTION";
    public static final String FINISH_PARAMETER_INTENT_DATA = "FINISH_PARAMETER_INTENT_DATA";

    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String VIEW_PROTOCOL = "VIEW_PROTOCOL";
    public static final String DEFAULT_DEBUG_MODE = "DEBUG_MODE";

    private Protocol protocol;
    private Controller controller;

    protected final Activity THIS = this;
    protected final String TAG = this.getClass().getSimpleName();
    protected final FinishReceiver finishReceiver = new FinishReceiver();

    protected Logger logger;
    protected boolean defaultDebugMode = BuildConfig.DEBUG;
    protected VDB viewDataBinding;

    public BaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isTranslucentStatus()) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (isFeatureNoTitle()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        BindLayout layoutResId = this.getClass().getAnnotation(BindLayout.class);
        if (layoutResId != null && layoutResId.value() != 0)
            viewDataBinding = DataBindingUtil.setContentView(this, layoutResId.value());
        else return; // throw exception
        logger = Logger.getInstance(TAG, defaultDebugMode);
        init(savedInstanceState);
        registerFinishBroadcast();
    }

    private void init(Bundle savedInstanceState) {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                defaultDebugMode = metaData.getBoolean(DEFAULT_DEBUG_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        protocol = (Protocol) getIntent().getSerializableExtra(VIEW_PROTOCOL);
        if (protocol != null && protocol.getController() != null)
            try {
                controller = ControllerProvider.get(protocol.getController());
                controller.setViewDataModel(viewDataBinding);
                controller.setContext(this);
                controller.onCreate(savedInstanceState);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        if (controller instanceof Standardize) {
            ((Standardize) controller).setupData((Map<String, Object>) getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) controller).initView();
            ((Standardize) controller).setListeners();
            ((Standardize) controller).onCreateLast();
        } else if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) THIS).initView();
            ((Standardize) THIS).setListeners();
            ((Standardize) THIS).onCreateLast();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterFinishBroadcast();
        if (controller != null) controller.onDestroy();
        viewDataBinding = null;
        protocol = null;
        System.gc();
    }

    private void registerFinishBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_ACTION);
        registerReceiver(finishReceiver, filter);
    }

    private void unregisterFinishBroadcast() {
        unregisterReceiver(finishReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                if (isBackAndExit()) onExit();
                else onBack();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onExit() {
        Intent exitIntent = new Intent();
        exitIntent.setAction(EXIT_ACTION);
        THIS.sendBroadcast(exitIntent);
    }

    public void onBack() {
        finish();
    }

    @Override
    public void onClick(View v) {
    }

    public void TOAST(CharSequence message) {
        Toast.makeText(THIS, message, Toast.LENGTH_SHORT).show();
    }

    public void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
        Toast.makeText(THIS, message, duration).show();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        THIS.startActivity(new Intent(THIS, clazz));
    }

    public void startActivity(Class<? extends Activity> clazz, HashMap<String, Object> data) {
        Intent intent = new Intent(THIS, clazz);
        intent.putExtra(INTENT_DATA, data);
        THIS.startActivity(intent);
    }

    public void startActivity(Class<? extends Activity> clazz, Protocol protocol) {
        Intent intent = new Intent(THIS, clazz);
        intent.putExtra(VIEW_PROTOCOL, protocol);
        THIS.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public boolean isTranslucentStatus() {
        return false;
    }

    public boolean isFeatureNoTitle() {
        return false;
    }

    public boolean isBackAndExit() {
        return false;
    }

    public void finishWith(Collection<String> pages) {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new FinishParameter(pages));
        THIS.sendBroadcast(finishIntent);
    }

    public void finishWith(Class clazz) {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new FinishParameter(null).add(clazz));
        THIS.sendBroadcast(finishIntent);
    }

    public void addFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void replaceFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Controller getController() {
        return this.controller;
    }

    public VDB getViewDataBinding() {
        return this.viewDataBinding;
    }

}