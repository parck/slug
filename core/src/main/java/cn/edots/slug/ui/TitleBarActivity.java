package cn.edots.slug.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import cn.edots.slug.BuildConfig;
import cn.edots.slug.Controller;
import cn.edots.slug.core.FinishReceiver;
import cn.edots.slug.R;
import cn.edots.slug.Standardize;
import cn.edots.slug.annotation.BindLayout;
import cn.edots.slug.core.ControllerProvider;
import cn.edots.slug.core.cache.Session;
import cn.edots.slug.core.log.Logger;
import cn.edots.slug.databinding.ActivityBaseTitleBarBinding;
import cn.edots.slug.model.Protocol;
import cn.edots.slug.model.TitleBarModel;
import cn.edots.slug.ui.fragment.EmptyFragment;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class TitleBarActivity<VDB extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener {

    public static final int _24SP = 24;
    public static final int _23SP = 23;
    public static final int _22SP = 22;
    public static final int _21SP = 21;
    public static final int _20SP = 20;
    public static final int _19SP = 19;
    public static final int _18SP = 18;
    public static final int _17SP = 17;
    public static final int _16SP = 16;
    public static final int _15SP = 15;
    public static final int _14SP = 14;
    public static final int _13SP = 13;
    public static final int _12SP = 12;
    public static final int _11SP = 11;
    public static final int _10SP = 10;
    public static final String DEFAULT_BACK_ICON = "BACK_ICON";

    protected final String TAG = this.getClass().getSimpleName();

    private Protocol protocol;
    private Controller controller;

    protected Logger logger;
    protected boolean defaultDebugMode = BuildConfig.DEBUG;

    protected final Activity THIS = this;
    protected
    @DrawableRes
    int defaultBackIconRes = R.drawable.default_back_icon;
    protected final FinishReceiver finishReceiver = new FinishReceiver();

    protected ActivityBaseTitleBarBinding titleBarViewBinding;
    protected VDB viewDataBinding;
    protected TitleBarModel titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_title_bar);
        initTitleBar();
        initData();
        initView();
        initListener();

        BindLayout layoutResId = this.getClass().getAnnotation(BindLayout.class);
        if (layoutResId != null && layoutResId.value() != 0)
            viewDataBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutResId.value(), titleBarViewBinding.contentLayout, true);
        else return; // throw exception
        logger = Logger.getInstance(TAG, defaultDebugMode);
        init(savedInstanceState);
        registerFinishBroadcast();
    }

    protected void initTitleBar() {
        this.titleBar = new TitleBarModel();
        titleBarViewBinding.setTitleBar(titleBar);
    }

    private void initData() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                int resId = metaData.getInt(DEFAULT_BACK_ICON);
                if (resId != 0) {
                    defaultBackIconRes = resId;
                    titleBar.setLeftButtonDrawable(getResources().getDrawable(defaultBackIconRes));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        if (isHideBackButton()) {
            titleBar.setHideLeftButton(true);
        }

        if (isHideBottomLine()) {
            titleBar.setHideBottomLine(true);
        }

        setLeftButtonImageResource(defaultBackIconRes);
    }

    private void initListener() {
        setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackAndExit()) onExit();
                else onBack();
            }
        });

        setOnLeftTextButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackAndExit()) onExit();
                else onBack();
            }
        });
    }

    private void init(Bundle savedInstanceState) {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                defaultDebugMode = metaData.getBoolean(BaseActivity.DEFAULT_DEBUG_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        protocol = (Protocol) getIntent().getSerializableExtra(BaseActivity.VIEW_PROTOCOL);
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
            ((Standardize) controller).setupData((Map<String, Object>) getIntent().getSerializableExtra(BaseActivity.INTENT_DATA));
            ((Standardize) controller).initView();
            ((Standardize) controller).setListeners();
            ((Standardize) controller).onCreateLast();
        } else if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) getIntent().getSerializableExtra(BaseActivity.INTENT_DATA));
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

    public void setTitleBarColor(@ColorRes int resId) {
        titleBarViewBinding.toolBar.setBackgroundColor(THIS.getResources().getColor(resId));
    }

    public void setTitleLayoutHeight(@DimenRes int resId) {
        ViewGroup.LayoutParams layoutParams = titleBarViewBinding.titleLayout.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(resId);
        titleBarViewBinding.titleLayout.setLayoutParams(layoutParams);
    }

    public void setTitleLayoutPixelSizeHeight(int pixel) {
        ViewGroup.LayoutParams layoutParams = titleBarViewBinding.titleLayout.getLayoutParams();
        layoutParams.height = pixel;
        titleBarViewBinding.titleLayout.setLayoutParams(layoutParams);
    }

    public void setBottomLineShapeResource(@DrawableRes int resId) {
        titleBarViewBinding.bottomLine.setBackgroundResource(resId);
    }

    public void showEmpty() {
        showEmpty(0);
    }

    public void showEmpty(@DrawableRes int resId) {
        showEmpty(resId, null);
    }

    public void showEmpty(@DrawableRes int resId, CharSequence text) {
        showEmpty(resId, text, null);
    }

    public void showEmpty(@DrawableRes int resId, CharSequence text, View.OnClickListener listener) {
        EmptyFragment emptyFragment = new EmptyFragment();
        emptyFragment.setResId(resId);
        emptyFragment.setText(text);
        emptyFragment.setOnClickListener(listener);
        Session.setAttribute(EmptyFragment.class.getClass().getSimpleName(), emptyFragment);
        addFragment(R.id.content_layout, (Fragment) Session.getAttribute(EmptyFragment.class.getClass().getSimpleName()));
    }

    public void hideEmpty() {
        removeFragment((Fragment) Session.getAttribute(EmptyFragment.class.getClass().getSimpleName()));
    }

    //===============================================================
    // 重载方法
    //===============================================================

    /*设置左边图片*/
    public void setLeftButtonImageResource(@DrawableRes int resId) {
        titleBar.setLeftButtonDrawable(getResources().getDrawable(resId));
    }

    public void setOnLeftButtonClickListener(View.OnClickListener listener) {
        titleBarViewBinding.leftButton.setOnClickListener(listener);
    }
    /*设置左边图片**/

    /*设置左边text**/
    public void setLeftButtonTextContent(CharSequence text) {
        setLeftButtonTextContent(text, R.color.default_text_color);
    }

    public void setLeftButtonTextContent(CharSequence text, @ColorRes int resId) {
        setLeftButtonTextContent(text, resId, _16SP);
    }

    public void setLeftButtonTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        titleBar.setLeftTextButton(String.valueOf(text));
        titleBar.setHideLeftButton(true);
        titleBarViewBinding.leftButtonText.setTextColor(THIS.getResources().getColor(resId));
        titleBarViewBinding.leftButtonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnLeftTextButtonClickListener(View.OnClickListener listener) {
        titleBarViewBinding.leftButtonText.setOnClickListener(listener);
    }
    /*设置左边text*/

    /*设置左边title*/
    public void setLeftTitleContent(CharSequence title) {
        setLeftTitleContent(title, R.color.default_text_color);
    }

    public void setLeftTitleContent(CharSequence title, @ColorRes int resId) {
        setLeftTitleContent(title, resId, _18SP);
    }

    public void setLeftTitleContent(CharSequence title, @ColorRes int resId, int spSize) {
        titleBar.setLeftTitleText(String.valueOf(title));
        titleBarViewBinding.leftTitleText.setVisibility(View.VISIBLE);
        titleBarViewBinding.leftTitleText.setTextColor(THIS.getResources().getColor(resId));
        titleBarViewBinding.leftTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }
    /*设置左边title*/

    /*设置中间title*/
    public void setCenterTitleContent(CharSequence title) {
        setCenterTitleContent(title, R.color.default_text_color);
    }

    public void setCenterTitleContent(CharSequence title, @ColorRes int resId) {
        setCenterTitleContent(title, resId, _18SP);
    }

    public void setCenterTitleContent(CharSequence title, @ColorRes int resId, int spSize) {
        titleBar.setCenterTitleText(String.valueOf(title));
        titleBarViewBinding.centerTitleTextView.setVisibility(View.VISIBLE);
        titleBarViewBinding.centerTitleTextView.setTextColor(THIS.getResources().getColor(resId));
        titleBarViewBinding.centerTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }
    /*设置中间title*/


    /*设置右边图片*/
    public void setRightButtonImageResource(@DrawableRes int resId) {
        titleBar.setShowRightImageButton(true);
        titleBar.setRightButtonDrawable(getResources().getDrawable(resId));
    }

    public void setOnRightButtonListener(View.OnClickListener listener) {
        titleBarViewBinding.rightImageBtn.setOnClickListener(listener);
    }
    /*设置右边图片*/

    /*设置右边text*/
    public void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId) {
        setRightTextContent(text, resId, _16SP);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        titleBar.setRightTextButton(String.valueOf(text));
        titleBar.setShowRightImageButton(false);
        titleBarViewBinding.rightTextBtn.setVisibility(View.VISIBLE);
        titleBarViewBinding.rightTextBtn.setTextColor(THIS.getResources().getColor(resId));
        titleBarViewBinding.rightTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnRightTextListener(View.OnClickListener listener) {
        titleBarViewBinding.rightTextBtn.setOnClickListener(listener);
    }
    /*设置右边text*/

    public boolean isHideBackButton() {
        return false;
    }

    public boolean isHideBottomLine() {
        return false;
    }

    public boolean isBackAndExit() {
        return false;
    }

    public void onExit() {
        Intent exitIntent = new Intent();
        exitIntent.setAction(BaseActivity.EXIT_ACTION);
        THIS.sendBroadcast(exitIntent);
    }

    public void onBack() {
        finish();
    }

    @Override
    public void onClick(View v) {
    }

    public TitleBarModel getTitleBar() {
        return this.titleBar;
    }

    public void addFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    protected void registerFinishBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BaseActivity.EXIT_ACTION);
        registerReceiver(finishReceiver, filter);
    }

    protected void unregisterFinishBroadcast() {
        unregisterReceiver(finishReceiver);
    }

}
