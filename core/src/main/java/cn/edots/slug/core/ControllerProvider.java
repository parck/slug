package cn.edots.slug.core;


import cn.edots.slug.Controller;
import cn.edots.slug.core.cache.AppCachePool;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public class ControllerProvider {

    protected static AppCachePool<String, Controller> cache = AppCachePool.getInstance().newContainer(ControllerProvider.class.getSimpleName());

    public static <T extends Controller> T get(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = (T) cache.get(clazz.getSimpleName());
        if (t == null) {
            t = clazz.newInstance();
            cache.put(clazz.getSimpleName(), t);
        }
        return t;
    }
}
