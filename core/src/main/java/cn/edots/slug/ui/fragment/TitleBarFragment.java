package cn.edots.slug.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edots.slug.R;
import cn.edots.slug.annotation.BindLayout;
import cn.edots.slug.databinding.FragmentBaseTitleBarBinding;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public abstract class TitleBarFragment<VDB extends ViewDataBinding> extends BaseFragment<VDB> {

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

    protected View emptyView;
    protected ImageView emptyImage;
    protected TextView emptyText;
    private FragmentBaseTitleBarBinding titleBarBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        titleBarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_title_bar, container, false);
        this.rootView = titleBarBinding.getRoot();
        titleBarBinding.contentLayout.removeAllViews();
        BindLayout layoutResId = this.getClass().getAnnotation(BindLayout.class);
        if (layoutResId != null && layoutResId.value() != 0) {
            viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId.value(), container, false);
            titleBarBinding.contentLayout.addView(viewDataBinding.getRoot(), titleBarBinding.contentLayout.getLayoutParams());
        }
        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        emptyView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty, titleBarBinding.contentLayout, false);
        emptyImage = (ImageView) emptyView.findViewById(R.id.empty_image);
        emptyText = (TextView) emptyView.findViewById(R.id.empty_text);

        if (isHideBackButton()) {
            titleBarBinding.leftButton.setVisibility(View.GONE);
        }

        if (isHideBottomLine()) {
            titleBarBinding.bottomLine.setVisibility(View.GONE);
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

    public void setTitleBarColor(@ColorRes int resId) {
        titleBarBinding.titleLayout.setBackgroundColor(THIS.getResources().getColor(resId));
    }

    public void setTitleLayoutHeight(@DimenRes int resId) {
        ViewGroup.LayoutParams layoutParams = titleBarBinding.titleLayout.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(resId);
        titleBarBinding.titleLayout.setLayoutParams(layoutParams);
    }

    public void setTitleLayoutPixelSizeHeight(int pixel) {
        ViewGroup.LayoutParams layoutParams = titleBarBinding.titleLayout.getLayoutParams();
        layoutParams.height = pixel;
        titleBarBinding.titleLayout.setLayoutParams(layoutParams);
    }

    public void setBottomLineShapeResource(@DrawableRes int resId) {
        titleBarBinding.bottomLine.setBackgroundResource(resId);
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
        if (resId != 0) emptyImage.setImageResource(resId);
        if (text != null) emptyText.setText(text);
        if (listener != null) {
            emptyImage.setOnClickListener(listener);
            emptyText.setOnClickListener(listener);
        }
        titleBarBinding.contentLayout.addView(emptyView);
    }

    public void hideEmpty() {
        titleBarBinding.contentLayout.removeView(emptyView);
    }

    //===============================================================
    // 重载方法
    //===============================================================

    /*设置左边图片*/
    public void setLeftButtonImageResource(@DrawableRes int resId) {
        titleBarBinding.leftButton.setImageResource(resId);
    }

    public void setOnLeftButtonClickListener(View.OnClickListener listener) {
        titleBarBinding.leftButton.setOnClickListener(listener);
    }
    /*设置左边图片**/

    /*设置左边text**/
    public void setLeftTextContent(CharSequence text) {
        setLeftTextContent(text, R.color.default_text_color);
    }

    public void setLeftTextContent(CharSequence text, @ColorRes int resId) {
        setLeftTextContent(text, resId, _16SP);
    }

    public void setLeftTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        titleBarBinding.leftButton.setVisibility(View.GONE);
        titleBarBinding.leftButtonText.setVisibility(View.VISIBLE);
        titleBarBinding.leftButtonText.setText(text);
        titleBarBinding.leftButtonText.setTextColor(THIS.getResources().getColor(resId));
        titleBarBinding.leftButtonText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnLeftTextButtonClickListener(View.OnClickListener listener) {
        titleBarBinding.leftButtonText.setOnClickListener(listener);
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
        titleBarBinding.leftTitleText.setVisibility(View.VISIBLE);
        titleBarBinding.leftTitleText.setText(title);
        titleBarBinding.leftTitleText.setTextColor(THIS.getResources().getColor(resId));
        titleBarBinding.leftTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
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
        titleBarBinding.centerTitleTextView.setVisibility(View.VISIBLE);
        titleBarBinding.centerTitleTextView.setText(title);
        titleBarBinding.centerTitleTextView.setTextColor(THIS.getResources().getColor(resId));
        titleBarBinding.centerTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }
    /*设置中间title*/


    /*设置右边图片*/
    public void setRightButtonImageResource(@DrawableRes int resId) {
        titleBarBinding.rightImageBtn.setVisibility(View.VISIBLE);
        titleBarBinding.rightImageBtn.setImageResource(resId);
    }

    public void setOnRightButtonListener(View.OnClickListener listener) {
        titleBarBinding.rightImageBtn.setOnClickListener(listener);
    }
    /*设置右边图片*/

    /*设置右边text*/
    public void setRightTextContent(CharSequence text) {
        setRightTextContent(text, R.color.default_text_color);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId) {
        setRightTextContent(text, R.color.default_text_color, _16SP);
    }

    public void setRightTextContent(CharSequence text, @ColorRes int resId, int spSize) {
        titleBarBinding.rightImageBtn.setVisibility(View.GONE);
        titleBarBinding.rightTextBtn.setVisibility(View.VISIBLE);
        titleBarBinding.rightTextBtn.setText(text);
        titleBarBinding.rightTextBtn.setTextColor(THIS.getResources().getColor(resId));
        titleBarBinding.rightTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize);
    }

    public void setOnRightTextListener(View.OnClickListener listener) {
        titleBarBinding.rightTextBtn.setOnClickListener(listener);
    }
    /*设置右边text*/

    public boolean isHideBackButton() {
        return false;
    }

    public boolean isHideBottomLine() {
        return false;
    }
}
