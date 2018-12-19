package com.jt.ylcarousel;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author 高延荣
 * @date 2018/12/18 17:04
 * 描述: 暂时支持如
 * ￥ 100/20期，其中 ”￥ 100“ 的颜色 和  ”/20期“  的颜色不同 字号不同，另外 文字绘制时，调整baseline，使得文字底部相同
 */
public class YLDiscolorTextView extends android.support.v7.widget.AppCompatTextView {

    private String beforeText = "";
    private int beforeColor;
    private float beforeSize;


    private String afterText = "";
    private int afterColor;
    private float afterSize;

    /**
     * 前面文字的宽度
     */
    private float beforeWidth;
    /**
     * 后面文字的宽度
     */
    private float afterWidth;
    /**
     * 画笔
     */
    private TextPaint beforePaint;
    /**
     * 画笔
     */
    private TextPaint afterPaint;
    /**
     * 资源
     */
    private final Resources resources;


    public YLDiscolorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        resources = context.getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YLDiscolorTextView);
        beforeText = typedArray.getString(R.styleable.YLDiscolorTextView_beforeText);
        beforeColor = typedArray.getColor(R.styleable.YLDiscolorTextView_beforeTextColor, resources.getColor(R.color._F86161));
        beforeSize = typedArray.getDimension(R.styleable.YLDiscolorTextView_beforeTextSize, YLUtil.dp2px(context, 14));
        afterText = typedArray.getString(R.styleable.YLDiscolorTextView_afterText);
        afterColor = typedArray.getColor(R.styleable.YLDiscolorTextView_afterTextColor, resources.getColor(R.color._333));
        afterSize = typedArray.getDimension(R.styleable.YLDiscolorTextView_afterTextSize, YLUtil.dp2px(context, 12));
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        beforePaint = new TextPaint();
        afterPaint = new TextPaint();

        beforePaint.setAntiAlias(true);
        beforePaint.setTextSize(beforeSize);
        beforePaint.setColor(beforeColor);

        afterPaint.setAntiAlias(true);
        afterPaint.setTextSize(afterSize);
        afterPaint.setColor(afterColor);

        beforeWidth = beforePaint.measureText(beforeText);
        afterWidth = afterPaint.measureText(afterText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            width = (int) (beforeWidth + afterWidth) + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            height = (int) Math.max(beforeSize, afterSize) + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TextPaint textPaint = beforeSize < afterSize ? afterPaint : beforePaint;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float y = -fontMetrics.ascent;
        canvas.drawText(beforeText, 0, y, beforePaint);
        canvas.drawText(afterText, beforeWidth, y, afterPaint);

        //  baseLine
        Paint paint = new Paint();
        paint.setColor(resources.getColor(R.color._238E23));
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        canvas.drawLine(0, y, beforeWidth + afterWidth, y, paint);
    }

}
