package cn.edots.slug.core.cache;

import android.support.v4.app.Fragment;

import cn.edots.slug.ui.fragment.BaseFragment;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public class FragmentPool {

    protected static AppCachePool<String, BaseFragment> cache = AppCachePool.getInstance().newContainer(FragmentPool.class.getSimpleName());

    public static <T extends Fragment> T getFragment(Class<T> clazz) {
        if (cache.get(clazz.getSimpleName()) == null) {
            try {
                cache.put(clazz.getSimpleName(), (BaseFragment) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) cache.get(clazz.getSimpleName());
    }

    public static void remove(String key) {
        cache.remove(key);
    }

    public static void clear() {
        cache.clear();
    }

}
