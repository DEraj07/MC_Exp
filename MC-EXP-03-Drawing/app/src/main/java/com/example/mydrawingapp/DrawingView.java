package com.example.mydrawingapp;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private Path drawPath;
    private Paint drawPaint;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK); // Default Pen Color
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15f); // Default Brush Size
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void clearCanvas() {
        drawPath.reset();
        invalidate();
    }

    public void setBrushColor(int newColor) {
        drawPaint.setColor(newColor);
    }

    public void setBrushSize(float newSize) {
        drawPaint.setStrokeWidth(newSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            default: return false;
        }
        invalidate();
        return true;
    }
}