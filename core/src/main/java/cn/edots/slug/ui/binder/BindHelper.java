package cn.edots.slug.ui.binder;

import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.edots.slug.annotation.BindView;
import cn.edots.slug.annotation.FindView;
import cn.edots.slug.listener.OnTextWatcher;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */
@Deprecated
public class BindHelper {

    public static void byFields(Object object, View provider) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (object == null || provider == null) return;
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields == null) return;
        for (Field field : fields) {
            FindView findView = field.getAnnotation(FindView.class);
            BindView bindView = field.getAnnotation(BindView.class);
            field.setAccessible(true);
            if (findView != null) {
                field.set(object, provider.findViewById(findView.value()));
            } else if (bindView != null) {
                Type type = null;
                Class clazz = (Class) field.getGenericType();
                final Method setView = clazz.getMethod("setView", View.class);
                final Method setText = clazz.getMethod("setText", String.class);
                final Object instance = clazz.newInstance();
                try {
                    type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                } catch (ClassCastException e) {
                    type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
                }
                if (TextView.class.equals(type)) {
                    TextView view = (TextView) provider.findViewById(bindView.value());
                    setView.invoke(instance, view);
                    field.set(object, instance);
                    setText.invoke(instance, view.getText().toString());
                } else if (EditText.class.equals(type)) {
                    final EditText view = (EditText) provider.findViewById(bindView.value());
                    setView.invoke(instance, view);
                    setText.invoke(instance, view.getText().toString());
                    view.addTextChangedListener(new OnTextWatcher() {
                        @Override
                        public void delayedTextChanged(Editable s) {
                            try {
                                setText.invoke(instance, s.toString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    field.set(object, instance);
                }
            }
        }
    }

    public static void byFields(Object object, Activity provider) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (object == null || provider == null) return;
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields == null) return;
        for (Field field : fields) {
            FindView findView = field.getAnnotation(FindView.class);
            BindView bindView = field.getAnnotation(BindView.class);
            field.setAccessible(true);
            if (findView != null) {
                field.set(object, provider.findViewById(findView.value()));
            } else if (bindView != null) {
                Type type = null;
                Class clazz = (Class) field.getGenericType();
                final Method setView = clazz.getMethod("setView", View.class);
                final Method setText = clazz.getMethod("setText", String.class);
                final Object instance = clazz.newInstance();
                try {
                    type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                } catch (ClassCastException e) {
                    type = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
                }
                if (TextView.class.equals(type)) {
                    TextView view = (TextView) provider.findViewById(bindView.value());
                    setView.invoke(instance, view);
                    field.set(object, instance);
                    setText.invoke(instance, view.getText().toString());
                } else if (EditText.class.equals(type)) {
                    final EditText view = (EditText) provider.findViewById(bindView.value());
                    setView.invoke(instance, view);
                    setText.invoke(instance, view.getText().toString());
                    view.addTextChangedListener(new OnTextWatcher() {
                        @Override
                        public void delayedTextChanged(Editable s) {
                            try {
                                setText.invoke(instance, s.toString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    field.set(object, instance);
                }
            }
        }
    }

    public static void byFields(Activity object) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        byFields(object, object);
    }
}
