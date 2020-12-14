package utils;

import android.util.TypedValue;
import android.view.View;

public class UtilDimention
{
    /**
     * sp转px
     * @param sp
     * @return
     */
    public static int sp2px(int sp, View view)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,view.getResources().getDisplayMetrics());
    }
}
