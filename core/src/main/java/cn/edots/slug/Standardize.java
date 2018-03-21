package cn.edots.slug;

import android.support.annotation.Nullable;

import java.util.Map;

/**
 * @author Parck.
 * @date 2017/10/10.
 * @desc
 */

public interface Standardize {

    /**
     * 执行优先级1
     */
    void setupData(@Nullable Map<String, Object> intentData);

    /**
     * 执行优先级2
     */
    void initView();

    /**
     * 执行优先级3
     */
    void setListeners();

    /**
     * 执行优先级4
     */
    void onCreateLast();

}
