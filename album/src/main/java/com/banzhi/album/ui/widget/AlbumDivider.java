package com.banzhi.album.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/8.
 * @desciption :
 * @version :
 * </pre>
 */

public class AlbumDivider extends RecyclerView.ItemDecoration {
    Drawable mDivider;
    int mDividerHeight;
    int mDividerWidth;

    public AlbumDivider() {
        super();
        mDivider = new ColorDrawable(Color.WHITE);

    }

    public AlbumDivider(int dividerSize) {
        this();
        mDividerWidth = dividerSize;
        mDividerHeight = dividerSize;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    /**
     * 绘制横向分割线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        c.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int top = child.getBottom();
            int right = child.getRight();
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
        c.restore();
    }

    /**
     * 绘制竖向分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        c.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getRight();
            int top = child.getTop();
            int right = left + mDividerWidth;
            int bottom = child.getBottom();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
        c.restore();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        int count = parent.getAdapter().getItemCount();
        int spanCount = getSpanCount(parent);

        boolean firstRaw = isFirstRaw(position, spanCount);
        boolean lastRaw = isLastRaw(position, spanCount, count);
        boolean firstColumn = isFirstColumn(position, spanCount);
        boolean lastColumn = isLastColumn(position, spanCount);

        if (spanCount == 1) {
            if (firstRaw) {   //第一行
                outRect.set(mDividerWidth, mDividerHeight, mDividerWidth, mDividerHeight / 2);
            } else if (lastRaw) { //最后一行
                outRect.set(mDividerWidth, mDividerHeight / 2, mDividerWidth, mDividerHeight);
            } else {
                outRect.set(mDividerWidth, mDividerHeight / 2, mDividerWidth, mDividerHeight / 2);
            }
        } else {
            if (firstRaw && firstColumn) {//第一行第一列
                outRect.set(mDividerWidth, mDividerHeight, mDividerWidth /2, mDividerHeight / 2);
            } else if (firstRaw && lastColumn) {//第一行最后一列
                outRect.set(mDividerWidth / 2, mDividerHeight, mDividerWidth, mDividerHeight / 2);
            } else if (lastRaw && firstColumn) {//最后一行 第一列
                outRect.set(mDividerWidth, mDividerHeight / 2, mDividerWidth / 2, mDividerHeight);
            } else if (lastRaw && lastColumn) {//最后一行 最后一列
                outRect.set(mDividerWidth / 2, mDividerHeight / 2, mDividerWidth, mDividerHeight);
            } else if (firstRaw) {//第一行
                outRect.set(mDividerWidth / 2, mDividerHeight, mDividerWidth / 2, mDividerHeight / 2);
            } else if (firstColumn) {//第一列
                outRect.set(mDividerWidth, mDividerHeight / 2, mDividerWidth /2, mDividerHeight / 2);
            } else if (lastColumn) {//最后一列
                outRect.set(mDividerWidth / 2, mDividerHeight / 2, mDividerWidth, mDividerHeight / 2);
            } else if (lastRaw) {//最后一行
                outRect.set(mDividerWidth / 2, mDividerHeight / 2, mDividerWidth / 2, mDividerHeight);
            } else {
                outRect.set(mDividerWidth /2, mDividerHeight / 2, mDividerWidth / 2, mDividerHeight / 2);
            }
        }

    }

    /**
     * 是否是第一行
     *
     * @param position
     * @param columnCount
     * @return
     */
    private boolean isFirstRaw(int position, int columnCount) {
        return position < columnCount;
    }

    /**
     * 是否是最后一行
     *
     * @param position
     * @param columnCount
     * @param childCount
     * @return
     */
    private boolean isLastRaw(int position, int columnCount, int childCount) {
        if (columnCount == 1)
            return position + 1 == childCount;
        else {
            int lastRawItemCount = childCount % columnCount;
            int rawCount = (childCount - lastRawItemCount) / columnCount + (lastRawItemCount > 0 ? 1 : 0);

            int rawPositionJudge = (position + 1) % columnCount;
            if (rawPositionJudge == 0) {
                int rawPosition = (position + 1) / columnCount;
                return rawCount == rawPosition;
            } else {
                int rawPosition = (position + 1 - rawPositionJudge) / columnCount + 1;
                return rawCount == rawPosition;
            }
        }
    }

    /**
     * 是否是第一列
     *
     * @param position
     * @param columnCount
     * @return
     */
    private boolean isFirstColumn(int position, int columnCount) {
        if (columnCount == 1)
            return true;
        return position % columnCount == 0;
    }

    /**
     * 是否是最后一列
     *
     * @param position
     * @param columnCount
     * @return
     */
    private boolean isLastColumn(int position, int columnCount) {
        if (columnCount == 1)
            return true;
        return (position + 1) % columnCount == 0;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }
}
