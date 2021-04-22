package cn.com.changjiu.library.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import cn.com.changjiu.library.R;


/**
 * -----------------------------------------------
 * 作    者：高延荣
 * 电    话：18963580395
 * 创建日期：2019/4/11 - 10:54 AM
 * 描    述：1999元/天，其中 ”1999“ 的颜色 和  ”元/天“  的颜色不同 字号不同，另外 文字绘制时，调整baseline，使得文字底部相同
 * 修订历史：
 * -----------------------------------------------
 */
public class YLDiscolorTextView extends androidx.appcompat.widget.AppCompatTextView {

    private String beforeText = "";
    private int beforeColor;
    private float beforeSize;


    private String afterText = "";
    private int afterColor;
    private float afterSize;

    /**
     * 前面文字的宽度、高度
     */
    private float beforeWidth;
    private float beforeHeight;
    /**
     * 后面文字的宽度、高度
     */
    private float afterWidth;
    private float afterHeight;
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
        beforeColor = typedArray.getColor(R.styleable.YLDiscolorTextView_beforeTextColor, resources.getColor(R.color.lib_000));
        beforeSize = typedArray.getDimension(R.styleable.YLDiscolorTextView_beforeTextSize, SizeUtils.dp2px(14));
        afterText = typedArray.getString(R.styleable.YLDiscolorTextView_afterText);
        afterColor = typedArray.getColor(R.styleable.YLDiscolorTextView_afterTextColor, resources.getColor(R.color.lib_000));
        afterSize = typedArray.getDimension(R.styleable.YLDiscolorTextView_afterTextSize, SizeUtils.dp2px(12));
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

        measureText();
    }

    private void measureText() {
        if (!TextUtils.isEmpty(beforeText)){
            beforeWidth = beforePaint.measureText(beforeText);
            Paint.FontMetrics fontMetrics = beforePaint.getFontMetrics();
            beforeHeight = fontMetrics.bottom - fontMetrics.top;
        }
        if (!TextUtils.isEmpty(afterText)){
            afterWidth = afterPaint.measureText(afterText);
            Paint.FontMetrics fontMetrics = afterPaint.getFontMetrics();
            afterHeight = fontMetrics.bottom - fontMetrics.top;
        }



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);

        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == View.MeasureSpec.AT_MOST || widthMode == View.MeasureSpec.UNSPECIFIED) {
            width = (int) (beforeWidth + afterWidth) + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == View.MeasureSpec.AT_MOST || heightMode == View.MeasureSpec.UNSPECIFIED) {
            height = (int) Math.max(beforeHeight, afterHeight) + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TextPaint textPaint = beforeHeight < afterHeight ? afterPaint : beforePaint;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float y = -fontMetrics.ascent;
        if (!TextUtils.isEmpty(beforeText)){
            canvas.drawText(beforeText, 0, y, beforePaint);
        }

        if (!TextUtils.isEmpty(afterText)){
            canvas.drawText(afterText, beforeWidth, y, afterPaint);
        }

    }

    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
        measureText();
        requestLayout();
    }

    public void setAfterText(String afterText) {
        this.afterText = afterText;
        measureText();
        requestLayout();
    }
}
