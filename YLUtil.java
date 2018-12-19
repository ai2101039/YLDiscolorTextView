package com.jt.ylcarousel;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author 高延荣
 * @date 2018/12/11 17:27
 * 描述:
 */
public class YLUtil {
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}
