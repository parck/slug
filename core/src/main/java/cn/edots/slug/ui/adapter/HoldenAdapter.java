package cn.edots.slug.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import cn.edots.slug.model.Holdable;


/**
 * @author Parck.
 * @date 2017/12/6.
 * @desc
 */

public class HoldenAdapter extends RecyclerViewAdapter<Holdable> {

    public HoldenAdapter(Context context, @LayoutRes int layoutId, List<Holdable> data) {
        super(context, layoutId, data);
    }

    public HoldenAdapter(Context context, @LayoutRes int[] layoutIds, List<Holdable> data) {
        super(context, layoutIds, data);
    }

    @Override
    protected void binding(ViewHolder holder, Holdable data, int position) {
        data.holding(holder);
    }
}
