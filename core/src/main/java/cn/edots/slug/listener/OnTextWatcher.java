package cn.edots.slug.listener;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author Parck.
 * @date 2017/9/25.
 * @desc
 */

public class OnTextWatcher implements TextWatcher {

    private Handler handler = new Handler();
    private boolean running = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        if (!running) {
            running = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayedTextChanged(s);
                    running = false;
                }
            }, 800);
        }
    }

    public void delayedTextChanged(Editable s) {

    }

}