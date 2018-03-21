package cn.edots.slug.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * @author Parck.
 * @date 2017/10/20.
 * @desc
 */

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    public SwipeRefreshLayout(Context context) {
        super(context);
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setColorSchemeColors(Color.parseColor("#00A2E8"), Color.parseColor("#E8E000"), Color.parseColor("#E1005A"));
        if (context instanceof OnRefreshListener) {
            this.setOnRefreshListener((OnRefreshListener) context);
        }
    }

    @Override
    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    public void startRefresh() {
        if (!isRefreshing()) setRefreshing(true);
    }

    public void stopRefresh() {
        if (isRefreshing()) setRefreshing(false);
    }
}
