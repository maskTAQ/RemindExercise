package com.remindexercise.remindexercise.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.remindexercise.remindexercise.R;

import java.util.Calendar;

public class Dial extends View {
    //表盘 宽高 半径
    private int containerWidth, containerHeight, containerRadius;
    private int padding = 30;
    //画笔
    private Paint
            pathwayCirclePaint, dialContainerPaint, pointPaint, scaleContainerPaint,
            scaleLinePaint, scaleTextPaint, dialTitleContainerPaint, dialTitlePaint,
            contentTextPain;
    private static final String TAG = "Dial";
    private Calendar calendar;

    public Dial(Context context, AttributeSet attrs) {

        super(context, attrs);
        initCanvasPaint();
    }

    //初始化 画笔
    public void initCanvasPaint() {
        pathwayCirclePaint = new Paint();
        pathwayCirclePaint.setColor(getResources().getColor(R.color.pathway));
        pathwayCirclePaint.setStrokeWidth(22);
        pathwayCirclePaint.setStyle(Paint.Style.STROKE);

        dialContainerPaint = new Paint();
        dialContainerPaint.setColor(getResources().getColor(R.color.dialContainer));

        pointPaint = new Paint();
        pointPaint.setStrokeWidth(12);

        pointPaint.setColor(getResources().getColor(R.color.point));
        pointPaint.setAntiAlias(true);

        scaleContainerPaint = new Paint();
        scaleContainerPaint.setColor(getResources().getColor(R.color.scale_container));

        scaleLinePaint = new Paint();
        scaleLinePaint.setStrokeWidth(4);
        scaleLinePaint.setColor(getResources().getColor(R.color.scale));

        scaleTextPaint = new Paint();
        scaleTextPaint.setTextSize(52);
        scaleTextPaint.setColor(getResources().getColor(R.color.scale));

        dialTitleContainerPaint = new Paint();
        dialTitleContainerPaint.setColor(getResources().getColor(R.color.dial_title_container));

        dialTitlePaint = new Paint();
        dialTitlePaint.setTextSize(52);
        dialTitlePaint.setColor(Color.BLACK);

        contentTextPain = new Paint();
        contentTextPain.setTextSize(152);
        contentTextPain.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        containerWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        containerHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        containerRadius = (containerWidth > containerHeight ? containerHeight / 2 : containerWidth / 2) - padding * 2;


        Log.i(TAG, "containerRadius:" + containerRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float posX = containerWidth / 2, poxY = containerHeight / 2;
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR), minute = calendar.get(Calendar.MINUTE);

        //绘制表盘背景
        canvas.drawCircle(posX, poxY, containerRadius, dialContainerPaint);
        //绘制表盘轨道
        canvas.drawCircle(posX, poxY, containerRadius, pathwayCirclePaint);


        float rotateAngle = (hour * 60 + minute) / 12f / 60 * 360;

        //绘制表盘指针
        canvas.save();
        //以画布为中心 旋转
        canvas.rotate(rotateAngle, posX, poxY);
        canvas.drawLine(posX, poxY - containerRadius - 50, posX, poxY, pointPaint);
        canvas.restore();


        float scaleContainerRadius = containerRadius * 3 / 5;
        //绘制 刻度容器
        canvas.drawCircle(posX, poxY, scaleContainerRadius, scaleContainerPaint);
        //绘制时针
        for (int i = 1; i < 13; i++) {

            canvas.save();
            canvas.rotate(360 / 12 * i, posX, poxY);
            canvas.drawLine(posX, poxY - scaleContainerRadius, posX, poxY - scaleContainerRadius - 40, scaleLinePaint);

            if (i % 2 == 0) {
                canvas.save();
                //canvas.rotate(360 - (360 / 12 * i), posX - 10, poxY - scaleContainerRadius - 60);
                //canvas.rotate(20);
                canvas.drawText(i + "", posX - 20, poxY - scaleContainerRadius - 60, scaleTextPaint);
                canvas.restore();
            }
            //绘制时刻
            for (int moment = 1; moment < 3; moment++) {
                canvas.save();
                canvas.rotate(10 * moment, posX, poxY);
                canvas.drawLine(posX, poxY - scaleContainerRadius, posX, poxY - scaleContainerRadius - 10, scaleLinePaint);
                canvas.restore();
            }
            canvas.restore();
        }

        //绘制标题
        RectF oval3 = new RectF(posX - 150, poxY * 4 / 5, posX + 150, poxY * 4 / 5 + 80);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 40, 40, dialTitleContainerPaint);
        canvas.drawText("本时段运动", posX - 130, poxY * 4 / 5 + 58, dialTitlePaint);

        int step = 1024;
        int numberPlaceholderSize = 42;
        float valueWidth = String.valueOf(step).length() * numberPlaceholderSize;
        canvas.drawText(1024 + "", posX - valueWidth, poxY + 100, contentTextPain);
        canvas.drawText("步", posX + valueWidth + 20, poxY + 100, dialTitlePaint);
    }
}
