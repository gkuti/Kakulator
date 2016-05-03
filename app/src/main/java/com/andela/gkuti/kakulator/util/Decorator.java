package com.andela.gkuti.kakulator.util;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.graphics.Paint;

import com.andela.gkuti.kakulator.R;

/**
 * Decorator class
 */
public class Decorator extends RecyclerView.ItemDecoration {

    private Context context;

    /**
     * Constructor for Decorator class
     */
    public Decorator(Context context) {
        this.context = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(4.0f);

        float startX = 20.0f;

        for (int i = 0, count = parent.getChildCount(); i < count; ++i) {
            View child = parent.getChildAt(i);

            float startY = child.getBottom();
            float stopX = child.getRight() - 20.0f;

            c.drawLine(startX, startY, stopX, startY, paint);
        }

    }
}
