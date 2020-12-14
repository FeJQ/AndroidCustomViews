package MyViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

import static utils.UtilDimention.sp2px;

public class MyTextView extends View
{
    private String mText = "";
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;
    private Paint mPaint;


    /**
     * 不指定布局文件加载控件时调用
     *
     * @param context
     */
    public MyTextView(Context context)
    {
        this(context, null);
    }


    /**
     * 在布局中加载控件
     *
     * @param context
     * @param attrs
     */
    public MyTextView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    /**
     * 在布局中加载控件，并使用了style样式表时，调用该构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        // 获取布局文件的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mText = typedArray.getString(R.styleable.MyTextView_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_textSize, sp2px(mTextSize, this));
        mTextColor = typedArray.getColor(R.styleable.MyTextView_textColor, mTextColor);
        typedArray.recycle();
        initialize();
    }

    /**
     * 初始化画笔
     */
    private void initialize()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }


    /**
     * 测量方法
     * 用于对控件的宽高进行测量并设置
     *
     * @param widthMeasureSpec  复合值，高2位表示 widthMode，低30位表示 widthSize
     * @param heightMeasureSpec 复合值，高2位表示 heightMode，低30位表示 heightSize
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取text画刷的矩形范围
        Rect textRect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), textRect);

        //处理wrap_content的情况
        if (widthMode == MeasureSpec.AT_MOST)
        {
            widthSize = textRect.width();
        }
        if (heightMode == MeasureSpec.AT_MOST)
        {
            heightSize = textRect.height();
        }
        // 加上padding值
        widthSize += getPaddingLeft() + getPaddingRight();
        heightSize += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();

        // 计算绘画区域x轴起始位置
        int x = getPaddingLeft();

        // 计算基线值
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(mText, x, baseLine, mPaint);
    }
}
