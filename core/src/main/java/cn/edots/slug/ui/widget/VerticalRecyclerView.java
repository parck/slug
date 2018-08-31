package cn.edots.slug.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Parck.
 * @date 2017/10/20.
 * @desc
 */

public class VerticalRecyclerView extends RecyclerView {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private GestureDetector detector;
    private boolean scrollable = true;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public VerticalRecyclerView(Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                View childView = VerticalRecyclerView.this.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(childView, VerticalRecyclerView.this.getChildPosition(childView));
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return scrollable;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.setLayoutManager(layoutManager);
        super.setAdapter(adapter);
        this.addOnItemTouchListener(new SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && onItemClickListener != null && detector.onTouchEvent(e)) {
                    onItemClickListener.onClick(child, rv.getChildPosition(child));
                    return true;
                }
                return super.onInterceptTouchEvent(rv, e);
            }
        });
    }

    /**
     * 禁止滚动
     *
     * @return
     */
    public void disableScroll() {
        this.scrollable = false;
        this.setHasFixedSize(true);
        this.setNestedScrollingEnabled(false);
    }

    // =============================================================================================
    // inner class
    // =============================================================================================

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(View v, int position);
    }
}