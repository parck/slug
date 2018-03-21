package cn.edots.slug.ui.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edots.slug.R;


/**
 * @author Parck.
 * @date 2017/10/31.
 * @desc
 */

public class EmptyFragment extends Fragment {

    protected ImageView emptyImage;
    protected TextView emptyText;

    private
    @DrawableRes
    int resId;
    protected CharSequence text;
    protected View.OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_empty, container, false);
        emptyImage = (ImageView) contentView.findViewById(R.id.empty_image);
        emptyText = (TextView) contentView.findViewById(R.id.empty_text);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (resId != 0) emptyImage.setImageResource(resId);
        if (text != null) emptyText.setText(text);
        if (onClickListener != null) {
            emptyImage.setOnClickListener(onClickListener);
            emptyText.setOnClickListener(onClickListener);
        }
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
