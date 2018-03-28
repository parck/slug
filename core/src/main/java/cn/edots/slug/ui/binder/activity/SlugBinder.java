package cn.edots.slug.ui.binder.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.edots.slug.annotation.BindLayout;
import cn.edots.slug.annotation.ClickView;
import cn.edots.slug.ui.binder.BindHelper;

/**
 * @author Parck.
 * @date 2017/9/25.
 * @desc
 */
@Deprecated
public class SlugBinder {

    private Activity object;
    private Field[] fields;
    private View.OnClickListener clickListener;
    private Object viewModel;

    public static SlugBinder getInstance(Activity object, Object viewModel) {
        return init(object, viewModel);
    }

    @NonNull
    private static SlugBinder init(Activity object, Object viewModel) {
        SlugBinder binder = new SlugBinder();
        binder.object = object;
        binder.fields = object.getClass().getDeclaredFields();
        binder.viewModel = viewModel;
        try {
            if (object instanceof View.OnClickListener)
                binder.clickListener = (View.OnClickListener) object;
            BindLayout slug = binder.object.getClass().getAnnotation(BindLayout.class);
            if (slug != null) binder.object.setContentView(slug.value());
            binder.binding();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private void binding() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        BindHelper.byFields(object);
        BindHelper.byFields(viewModel, object);
        if (clickListener != null) {
            Method method = object.getClass().getMethod("onClick", View.class);
            ClickView clickView = method.getAnnotation(ClickView.class);
            if (clickView != null) {
                for (int id : clickView.value()) {
                    View view = object.findViewById(id);
                    view.setOnClickListener(clickListener);
                }
            }
        }
    }

    public void finish() {
        fields = null;
        object = null;
    }

    public void setViewModel(Object viewModel) {
        this.viewModel = viewModel;
    }
}
