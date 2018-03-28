package cn.edots.slug.ui.binder;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import cn.edots.slug.listener.OnTextWatcher;

/**
 * @author Parck.
 * @date 2017/9/25.
 * @desc
 */
@Deprecated
public class Slugger<V extends View> implements Serializable {

    private static final long serialVersionUID = 8213641004923326776L;

    private V view;
    private String text;

    public V getView() {
        return view;
    }

    public void setView(final V view) {
        this.view = view;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (this.view instanceof EditText) {
            if (!this.view.hasFocus()) {
                ((EditText) this.view).setText(String.valueOf(text == null ? "" : text));
            }
        } else if (this.view instanceof TextView) {
            ((TextView) this.view).setText(String.valueOf(text == null ? "" : text));
        }
        this.text = text;
    }

    public void addTextChangedListener(OnTextWatcher watcher) {
        ((TextView) view).addTextChangedListener(watcher);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        view.setOnClickListener(onClickListener);
    }

    public void clearFocus() {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        }
    }

    public void requestFocus() {
        view.requestFocus();
        if (view instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public boolean isEmpty() {
        return text == null || "".equals(text);
    }

    public void clearText() {
        ((TextView) this.view).setText("");
        this.text = "";
    }
}
