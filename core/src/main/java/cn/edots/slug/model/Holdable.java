package cn.edots.slug.model;

import android.databinding.ViewDataBinding;

import java.io.Serializable;

import cn.edots.slug.ui.adapter.RecyclerViewAdapter;


/**
 * @author Parck.
 * @date 2017/12/6.
 * @desc
 */

public interface Holdable<VDB extends ViewDataBinding> extends Serializable {

    void holding(RecyclerViewAdapter.ViewHolder<VDB> holder);

}
