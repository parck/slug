package cn.edots.slug.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Parck.
 * @date 2017/10/20.
 * @desc
 */

public class VerticalRecyclerView extends RecyclerView {

    private Context context;

    public VerticalRecyclerView(Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.setLayoutManager(layoutManager);
        super.setAdapter(adapter);
    }
}
