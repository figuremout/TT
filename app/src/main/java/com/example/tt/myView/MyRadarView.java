package com.example.tt.myView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.tt.R;

/**
 * 使用内部类 自定义一个简单的View
 * 使用自定义类：
 * 1. 新建UI Component - Custom View
 * 2. 将自动建立的sample_my_radar_view.xml中的控件复制放到合适文件中，之后该文件可删除
 * @desciption: 雷达
 */
public class MyRadarView extends View {
    /**
     * 多边形点的个数
     */
    private int count = 5;
    /**
     * 多边形均等分角度
     */
    private float angle = (float) (Math.PI * 2 / count);

    /**
     * 网格最大半径
     */
    private float radius;
    /**
     * 中心x
     */
    private int centerX;
    /**
     * 中心y
     */
    private int centerY;
    /**
     * 数据最大值
     */
    private float maxValue = 100;
    /**
     * 各维度分值
     */
    private double[] data = {50, 50, 50, 50, 50};

    private String[] titles = {"四字成语", "四字成语", "四字成语", "四字成语", "四字成语"};

    /**
     * 雷达区画笔
     */
    private Paint mMainPaint;
    /**
     * 文本画笔
     */
    private Paint mTextPaint;
    /**
     * 数据区画笔
     */
    private Paint mValuePaint;

    public MyRadarView(Context context) {
        this(context, null);
    }

    public MyRadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mMainPaint = new Paint();
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStrokeWidth(3);
        mMainPaint.setStyle(Paint.Style.STROKE);
        mMainPaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        /**
         * 文字大小
         */
        mTextPaint.setTextSize(35);

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setColor(getResources().getColor(R.color.colorPrimary));
        mValuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //网格最大半径
        radius = (float) Math.min(h, w) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制正多边形
        drawPolygon(canvas);
        //绘制从中心到末端的直线
        drawLines(canvas);
        //绘制文本
        drawText(canvas);
        //绘制区域
        drawRegion(canvas);
    }

    /**
     * 绘制正多边形
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //蜘蛛丝之间的间距
        float r = radius / count;
        for (int i = 1; i <= count; i++) {
            //当前半径
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            //闭合路径
            path.close();
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制从中心到末端的直线
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            //计算最外侧蜘蛛丝上每个点的坐标
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制文本
     * 先计算出文本的长度，然后使起始绘制坐标向左偏移这个长度。
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            //计算最外侧蜘蛛丝上每个点的坐标
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            //第4象限
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {
                canvas.drawText(titles[i], x, y, mTextPaint);
            }
            //第3象限
            else if (angle * i >= Math.PI / 2 && angle * i <= Math.PI) {
                float dis = mTextPaint.measureText(titles[i]);
                canvas.drawText(titles[i], x - dis, y + dis, mTextPaint);
            }
            //第2象限
            else if (angle * i >= Math.PI && angle * i <= 3 * Math.PI / 2) {
                canvas.drawText(titles[i], x, y, mTextPaint);
            }
            //第1象限
            else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {
                canvas.drawText(titles[i], x, y, mTextPaint);
            }
        }
    }

    /**
     * 绘制区域
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        mValuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            //计算最外侧蜘蛛丝上每个点的坐标
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            canvas.drawCircle(x, y, 20, mValuePaint);
        }
        mValuePaint.setAlpha(127);
        //绘制填充区域
        canvas.drawPath(path, mValuePaint);
    }

    /**
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    /**
     * 各维度分值
     *
     * @param data data
     */
    public void setData(double[] data) {
        this.data = data;
    }

    /**
     * 数据最大值
     *
     * @param maxValue maxValue
     */
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置蜘蛛网颜色
     *
     * @param color
     */
    public void setMainPaintColor(int color) {
        mMainPaint.setColor(color);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTextPaintColor(int color) {
        mTextPaint.setColor(color);
    }

    /**
     * @param color
     */
    public void setValuePaintColor(int color) {
        mValuePaint.setColor(color);
    }
}
