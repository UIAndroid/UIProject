package net.nc.uialert.widget;

import net.nc.uialert.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类名：NumberProgressBar<br>
 * 类描述：数字进度条<br>
 * 创建人：howtoplay<br>
 */
public class NumberProgressBar extends View {

    /**
     * The max progress, default is 100
     */
    private int mMax = 100;

    /**
     * current progress, can not exceed the max progress.
     */
    private int mProgress = 0;

    /**
     * the progress area bar color
     */
    private int mReachedBarColor;

    /**
     * the bar unreached area color.
     */
    private int mUnreachedBarColor;

    /**
     * the progress text color.
     */
    private int mTextColor;

    /**
     * the progress text size
     */
    private float mTextSize;

    /**
     * the height of the reached area
     */
    private float mReachedBarHeight;

    /**
     * the height of the unreached area
     */
    private float mUnreachedBarHeight;

    /**
     * the suffix of the number.
     */
    private String mSuffix = "%";

    /**
     * the prefix.
     */
    private String mPrefix = "";

    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66,145,241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    private static final int PROGRESS_TEXT_VISIBLE = 0;

    /**
     * the width of the text that to be drawn
     */
    private float mDrawTextWidth;

    /**
     * the drawn text start
     */
    private float mDrawTextStart;

    /**
     *the drawn text end
     */
    private float mDrawTextEnd;

    /**
     * the text that to be drawn in onDraw()
     */
    private String mCurrentDrawText;

    /**
     * the Paint of the reached area.
     */
    private Paint mReachedBarPaint;
    /**
     * the Painter of the unreached area.
     */
    private Paint mUnreachedBarPaint;
    /**
     * the Painter of the progress text.
     */
    private Paint mTextPaint;

    /**
     * Unreached Bar area to draw rect.
     */
    private RectF mUnreachedRectF = new RectF(0,0,0,0);
    /**
     * reached bar area rect.
     */
    private RectF mReachedRectF = new RectF(0,0,0,0);

    /**
     * the progress text offset.
     */
    private float mOffset;

    /**
     * determine if need to draw unreached area
     */
    private boolean mDrawUnreachedBar = true;

    private boolean mDrawReachedBar = true;

    private boolean mIfDrawText = true;

    public enum ProgressTextVisibility{
        Visible,Invisible
    };

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.numberProgressBarStyle);

        default_reached_bar_height = dp2px(1.5f);
        default_unreached_bar_height = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);

        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
        		R.attr.numberProgressBarStyle, 0);

        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_reached_color, default_reached_color);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_unreached_color,default_unreached_color);
        mTextColor = attributes.getColor(R.styleable.NumberProgressBar_progress_text_color,default_text_color);
        mTextSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_size, default_text_size);

        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_height,default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_height,default_unreached_bar_height);
        mOffset = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_offset,default_progress_text_offset);

        int textVisible = attributes.getInt(R.styleable.NumberProgressBar_progress_text_visibility,PROGRESS_TEXT_VISIBLE);
        if(textVisible != PROGRESS_TEXT_VISIBLE){
            mIfDrawText = false;
        }

        setProgress(attributes.getInt(R.styleable.NumberProgressBar_progress,0));
        setMax(attributes.getInt(R.styleable.NumberProgressBar_max, 100));
        //
        attributes.recycle();

        initializePainters();

    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int)mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int)mTextSize,Math.max((int)mReachedBarHeight,(int)mUnreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true), measure(heightMeasureSpec,false));
    }

    private int measure(int measureSpec,boolean isWidth){
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth?getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom();
        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if(mode == MeasureSpec.AT_MOST){
                if(isWidth) {
                    result = Math.max(result, size);
                }
                else{
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mIfDrawText){
            calculateDrawRectF();
        }else{
            calculateDrawRectFWithoutProgressText();
        }

        if(mDrawReachedBar){
            canvas.drawRect(mReachedRectF,mReachedBarPaint);
        }

        if(mDrawUnreachedBar) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }

        if(mIfDrawText)
            canvas.drawText(mCurrentDrawText,mDrawTextStart,mDrawTextEnd,mTextPaint);
    }

    private void initializePainters(){
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }


    private void calculateDrawRectFWithoutProgressText(){
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight()/2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight() )/(getMax()*1.0f) * getProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight()/2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight()/2.0f +  - mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight()/2.0f  + mUnreachedBarHeight / 2.0f;
    }

    private void calculateDrawRectF(){

        mCurrentDrawText = String.format("%d" ,getProgress()*100/getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if(getProgress() == 0){
            mDrawReachedBar = false;
            mDrawTextStart = getPaddingLeft();
        }else{
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight()/2.0f - mReachedBarHeight / 2.0f;
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight() )/(getMax()*1.0f) * getProgress() - mOffset + getPaddingLeft();
            mReachedRectF.bottom = getHeight()/2.0f + mReachedBarHeight / 2.0f;
            mDrawTextStart = (mReachedRectF.right + mOffset);
        }

        mDrawTextEnd =  (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f)) ;

        if((mDrawTextStart + mDrawTextWidth )>= getWidth() - getPaddingRight()){
            mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
            mReachedRectF.right = mDrawTextStart - mOffset;
        }

        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        if(unreachedBarStart >= getWidth() - getPaddingRight()){
            mDrawUnreachedBar = false;
        }else{
            mDrawUnreachedBar = true;
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = getWidth() - getPaddingRight();
            mUnreachedRectF.top = getHeight()/2.0f +  - mUnreachedBarHeight / 2.0f;
            mUnreachedRectF.bottom = getHeight()/2.0f  + mUnreachedBarHeight / 2.0f;
        }
    }
    /**
     * get progress text color
     * @return progress text color
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * get progress text size
     * @return progress text size
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mProgress;
    }

    public int getMax() {
        return mMax;
    }

    public float getReachedBarHeight(){
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight(){
        return mUnreachedBarHeight;
    }

    public void setProgressTextSize(float TextSize) {
        this.mTextSize = TextSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int TextColor) {
        this.mTextColor = TextColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int BarColor) {
        this.mUnreachedBarColor = BarColor;
        mUnreachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int ProgressColor) {
        this.mReachedBarColor = ProgressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height){
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height){
        mUnreachedBarHeight = height;
    }

    public void setMax(int Max) {
        if(Max > 0){
            this.mMax = Max;
            invalidate();
        }
    }

    public void setSuffix(String suffix){
        if(suffix == null){
            mSuffix = "";
        }else{
            mSuffix = suffix;
        }
    }

    public String getSuffix(){
        return mSuffix;
    }

    public void setPrefix(String prefix){
        if(prefix == null)
            mPrefix = "";
        else{
            mPrefix = prefix;
        }
    }

    public String getPrefix(){
        return mPrefix;
    }

    public void incrementProgressBy(int by){
        if(by > 0){
            setProgress(getProgress() + by);
        }
    }

    public void setProgress(int Progress) {
        if(Progress <= getMax()  && Progress >= 0){
            this.mProgress = Progress;
            invalidate();
        }
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public float sp2px(float sp){
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(ProgressTextVisibility visibility){
        mIfDrawText = visibility == ProgressTextVisibility.Visible;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }
}