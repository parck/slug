package cn.edots.slug.ui.binder.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private Activity activity;
    private Fragment object;
    private Field[] fields;
    private View.OnClickListener clickListener;
    private View contentView;
    private Object viewModel;

    public static SlugBinder getInstance(Fragment object, ViewGroup container, Object viewModel) {
        return init(object, container, viewModel);
    }

    private static SlugBinder init(Fragment object, ViewGroup container, Object viewModel) {
        SlugBinder binder = new SlugBinder();
        binder.object = object;
        binder.activity = object.getActivity();
        binder.fields = object.getClass().getDeclaredFields();
        binder.viewModel = viewModel;
        try {
            if (object instanceof View.OnClickListener)
                binder.clickListener = (View.OnClickListener) object;
            BindLayout slug = object.getClass().getAnnotation(BindLayout.class);
            if (slug != null)
                binder.contentView = LayoutInflater.from(binder.activity).inflate(slug.value(), container, false);
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

    private void binding() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        BindHelper.byFields(object, contentView);
        BindHelper.byFields(viewModel, contentView);
        if (clickListener != null) {
            Method method = object.getClass().getMethod("onClick", View.class);
            ClickView clickView = method.getAnnotation(ClickView.class);
            if (clickView != null) {
                for (int id : clickView.value()) {
                    View view = contentView.findViewById(id);
                    view.setOnClickListener(clickListener);
                }
            }
        }
    }

    public View getContentView() {
        return contentView;
    }

    public void setViewModel(Object viewModel) {
        this.viewModel = viewModel;
    }

    public void finish() {
        fields = null;
        object = null;
        activity = null;
    }
}
