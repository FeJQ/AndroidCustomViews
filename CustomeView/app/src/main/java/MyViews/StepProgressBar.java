package MyViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.Image;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

import static utils.UtilDimention.sp2px;

public class StepProgressBar extends View
{
    enum Cap
    {
        PLANE(0),
        ROUND_CORNER(1);

        private final int id;

        Cap(int id)
        {
            this.id = id;
        }

        public int getValue()
        {
            return id;
        }
    }

    enum TextType
    {
        NONE(0),
        VALUE(1),
        PERCENT(2);

        private final int id;

        TextType(int id)
        {
            this.id = id;
        }

        public int getValue()
        {
            return id;
        }
    }

    private Cap mCap = Cap.PLANE;
    private int mMaxValue = 100;
    private int mValue = 0;
    private TextType mTextType = TextType.NONE;
    private int mTextSize = 20;
    private int mTextColor = Color.BLACK;
    private int mBorderWidth = 5;
    private int mInnerColor = Color.BLUE;
    private int mOuterColor = R.color.pink;
    private int mSideLength = 0;

    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;

    public StepProgressBar(Context context)
    {
        this(context, null);
    }

    public StepProgressBar(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceAsColor")
    public StepProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StepProgressBar);
        mCap = Cap.values()[typedArray.getInt(R.styleable.StepProgressBar_CapRound, mCap.getValue())];
        mMaxValue = typedArray.getInt(R.styleable.StepProgressBar_maxValue, mMaxValue);
        mValue = typedArray.getInt(R.styleable.StepProgressBar_value, mValue);
        mTextType = TextType.values()[typedArray.getInt(R.styleable.StepProgressBar_TextType, mTextType.getValue())];
        mTextSize = (int) typedArray.getDimension(R.styleable.StepProgressBar__textSize, mTextSize);
        mTextColor = typedArray.getColor(R.styleable.StepProgressBar__textColor, mTextColor);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.StepProgressBar_borderWidth, mBorderWidth);
        mInnerColor = typedArray.getColor(R.styleable.StepProgressBar_innerColor, mInnerColor);
        mOuterColor = typedArray.getColor(R.styleable.StepProgressBar_outerColor, mOuterColor);
        mSideLength = sp2px((int) typedArray.getDimension(R.styleable.StepProgressBar_sideLength, mSideLength), this);
        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //if(widthMode==MeasureSpec.AT_MOST)
        //{
        //    widthSize=mSideLength;
        //}
        //if(heightMode==MeasureSpec.AT_MOST)
        //{
        //    heightSize=mSideLength;
        //}
        if (mSideLength != 0)
        {
            widthSize = mSideLength;
            heightSize = mSideLength;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas)
    {
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        switch (mCap)
        {
            case PLANE: mInnerPaint.setStrokeCap(Paint.Cap.BUTT);break;
            case ROUND_CORNER: mInnerPaint.setStrokeCap(Paint.Cap.ROUND);break;
        }
        RectF innerRectF = new RectF(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth);
        canvas.drawArc(innerRectF, 135, 270, false, mInnerPaint);

        mOuterPaint=new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        switch (mCap)
        {
            case PLANE: mOuterPaint.setStrokeCap(Paint.Cap.BUTT);break;
            case ROUND_CORNER: mOuterPaint.setStrokeCap(Paint.Cap.ROUND);break;
        }
        RectF outerRectF = new RectF(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth);
        canvas.drawArc(outerRectF, 135,(float)mValue/(float)mMaxValue*270, false, mOuterPaint);

    }
}
