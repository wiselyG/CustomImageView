package org.wisely.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by wisely on 2017/4/7.
 */
public class AnimationImageView extends ImageView {

    private static final String TAG = "AnimationImageView";

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final boolean DEFAULT_ONESHOT = false;
    private static final boolean DEFAULT_RUNONSTART = false;

    private AnimationDrawable aDrawable;
    private Bitmap mBitmap;
    private int mAnimationList;

    private boolean mOneShot;
    private boolean mDisableCircularTransformation;
    private boolean mSetupPending;
    private boolean mReady;
    private boolean runOnStart;


    public AnimationImageView(Context context) {
        super(context);
        init();
    }

    public AnimationImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimationImageView, defStyleAttr, 0);
        mOneShot = a.getBoolean(R.styleable.AnimationImageView_aiv_oneshot, DEFAULT_ONESHOT);
        mAnimationList = a.getResourceId(R.styleable.AnimationImageView_aiv_animationlist, 0);
        runOnStart=a.getBoolean(R.styleable.AnimationImageView_aiv_runonshow,DEFAULT_RUNONSTART);
        a.recycle();

        init();
    }

    public AnimationImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        setScaleType(SCALE_TYPE);
        aDrawable = new AnimationDrawable();
        if (mAnimationList != 0) {
            setAnimationList(mAnimationList);
        }
        setOneshot(mOneShot);
        mReady = true;
        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }
        if (aDrawable.getNumberOfFrames() > 0) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                setBackgroundDrawable(aDrawable);
            } else {
                setBackground(aDrawable);
            }
            if(runOnStart){
                aDrawable.start();
            }
        }
        invalidate();
    }

    public void setOneshot(boolean f) {
        aDrawable.setOneShot(f);
        setup();
    }

    public boolean isRunOnStart() {
        return runOnStart;
    }

    public void setRunOnStart(boolean runOnStart) {
        this.runOnStart = runOnStart;
    }

    public void addFrame(int resId, int duration) {
        if (aDrawable != null) {
            Drawable draw = getResources().getDrawable(resId);
            aDrawable.addFrame(draw, duration);
        }
        setup();
    }

    public void addFrame(int resId) {
        addFrame(resId, 160);
    }

    public void setAnimationList(int resId) {
        setBackgroundResource(resId);
        aDrawable = (AnimationDrawable) getBackground();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (aDrawable.isRunning()) {
                aDrawable.stop();
            } else {
                aDrawable.start();
            }
            return true;
        }

        return super.onTouchEvent(event);
    }
}
