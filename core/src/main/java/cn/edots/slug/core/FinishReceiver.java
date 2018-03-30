package cn.edots.slug.core;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edots.slug.ui.BaseActivity;

public class FinishReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BaseActivity.EXIT_ACTION.equals(intent.getAction())) {
            FinishParameter parameter = (FinishParameter) intent.getSerializableExtra(BaseActivity.FINISH_PARAMETER_INTENT_DATA);
            if (parameter.isExit()
                    || (parameter.getPages() != null
                    && parameter.getPages().contains(context.getClass().getSimpleName())))
                ((Activity) context).finish();
        }
    }
}