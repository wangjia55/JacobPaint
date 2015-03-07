package com.jacob.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Package : com.jacob.paint
 * Author : jacob
 * Date : 15-3-6
 * Description : 这个类是用来xxx
 */
public class GuaGuaKa extends View {
    private Paint mPaint;
    private Path mPath;
    private Bitmap mBitmapMask;
    private Canvas mCanvas;
    private Paint mPaintText;
    private String text = "500,000";


    private int textW;
    private int textH;

    public GuaGuaKa(Context context) {
        this(context, null);
    }

    public GuaGuaKa(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mPath = new Path();


        mPaintText = new Paint();
        mPaintText.setTextSize(35);
        mPaintText.setStrokeJoin(Paint.Join.ROUND);
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);
        Rect rect = new Rect();
        mPaintText.getTextBounds(text, 0, text.length(), rect);

        textW = rect.width();
        textH = rect.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(250, 150);
        setupMaskBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawText(text, getMeasuredWidth() / 2 - textW / 2, getMeasuredHeight() / 2 + textH / 2, mPaintText);
        drawPath();
        canvas.drawBitmap(mBitmapMask, 0, 0, null);
    }

    private void setupMaskBitmap() {
        mBitmapMask = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapMask);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    private void drawPath() {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mPaint);
    }


    float x = 0;
    float y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                mPath.lineTo(x, y);
                break;
        }
        invalidate();
        return true;
//        return super.onTouchEvent(event);
    }
}
