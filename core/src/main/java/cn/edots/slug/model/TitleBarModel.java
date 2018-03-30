package cn.edots.slug.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * @Author WETOOP
 * @Date 2018/3/30.
 * @Description
 */

public class TitleBarModel extends DataModel {

    private boolean withoutToolBar;
    private int toolBarBackground;
    private boolean hideLeftButton;
    private Drawable leftButtonDrawable;
    private String leftTextButton;
    private String leftTitleText;
    private String centerTitleText;
    private boolean showRightImageButton;
    private Drawable rightButtonDrawable;
    private String rightTextButton;
    private boolean hideBottomLine;

    public boolean isWithoutToolBar() {
        return withoutToolBar;
    }

    public void setWithoutToolBar(boolean withoutToolBar) {
        this.withoutToolBar = withoutToolBar;
    }

    public int getToolBarBackground() {
        return toolBarBackground;
    }

    public void setToolBarBackground(int toolBarBackground) {
        this.toolBarBackground = toolBarBackground;
    }

    public boolean isHideLeftButton() {
        return hideLeftButton;
    }

    public void setHideLeftButton(boolean hideLeftButton) {
        this.hideLeftButton = hideLeftButton;
    }

    public Drawable getLeftButtonDrawable() {
        return leftButtonDrawable;
    }

    public void setLeftButtonDrawable(Drawable leftButtonDrawable) {
        this.leftButtonDrawable = leftButtonDrawable;
    }

    public String getLeftTextButton() {
        return leftTextButton;
    }

    public void setLeftTextButton(String leftTextButton) {
        this.leftTextButton = leftTextButton;
    }

    public String getLeftTitleText() {
        return leftTitleText;
    }

    public void setLeftTitleText(String leftTitleText) {
        this.leftTitleText = leftTitleText;
    }

    public String getCenterTitleText() {
        return centerTitleText;
    }

    public void setCenterTitleText(String centerTitleText) {
        this.centerTitleText = centerTitleText;
    }

    public boolean isShowRightImageButton() {
        return showRightImageButton;
    }

    public void setShowRightImageButton(boolean showRightImageButton) {
        this.showRightImageButton = showRightImageButton;
    }

    public Drawable getRightButtonDrawable() {
        return rightButtonDrawable;
    }

    public void setRightButtonDrawable(Drawable rightButtonDrawable) {
        this.rightButtonDrawable = rightButtonDrawable;
    }

    public String getRightTextButton() {
        return rightTextButton;
    }

    public void setRightTextButton(String rightTextButton) {
        this.rightTextButton = rightTextButton;
    }

    public boolean isHideBottomLine() {
        return hideBottomLine;
    }

    public void setHideBottomLine(boolean hideBottomLine) {
        this.hideBottomLine = hideBottomLine;
    }
}
