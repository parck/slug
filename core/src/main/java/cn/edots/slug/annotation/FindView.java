package cn.edots.slug.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Parck.
 * @date 2017/9/25.
 * @desc
 */
@Deprecated
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FindView {
    @IdRes int value();
}
