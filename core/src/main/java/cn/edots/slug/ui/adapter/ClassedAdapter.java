package cn.edots.slug.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import java.util.List;

import cn.edots.slug.model.Classable;


/**
 * @author Parck.
 * @date 2017/12/6.
 * @desc
 */

public class ClassedAdapter extends RecyclerViewAdapter<Classable> {

    public ClassedAdapter(Context context, @LayoutRes int layoutId, List<Classable> data) {
        super(context, layoutId, data);
    }

    public ClassedAdapter(Context context, @LayoutRes int[] layoutIds, List<Classable> data) {
        super(context, layoutIds, data);
    }

    @Override
    protected void binding(ViewHolder holder, Classable data, int position) {
        data.holding(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }
}
