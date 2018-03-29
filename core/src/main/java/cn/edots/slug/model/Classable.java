package cn.edots.slug.model;

import android.databinding.ViewDataBinding;

/**
 * @author Parck.
 * @date 2017/10/23.
 * @desc
 */

public interface Classable<VDB extends ViewDataBinding> extends Holdable<VDB> {

    void setType(int type);

    int getType();

}
