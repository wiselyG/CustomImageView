package org.wisely.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by wisely on 2017/4/7.
 */
public class ClipImageView extends ImageView {

    private static final String TAG = "ClipImageView";

    private final Paint mBitmapPaint = new Paint();

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static BitmapFactory.Options mOptions;

    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingRight;
    private int mWidth;
    private int mHeight;
    private int mClipGravity;
    private int mResId;

    private boolean mReady;
    private boolean mSetupPending;

    private Bitmap mBitmap;
    private Bitmap clipBitmap;
    private BitmapShader mBitmapShader;


    public ClipImageView(Context context) {
        super(context);
        init();
    }

    public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClipImageView, defStyleAttr, 0);
        mPaddingLeft = a.getInt(R.styleable.ClipImageView_civ_padding_left, -1);
        mPaddingTop = a.getInt(R.styleable.ClipImageView_civ_padding_top, -1);
        mPaddingRight = a.getInt(R.styleable.ClipImageView_civ_padding_right, -1);
        mPaddingBottom = a.getInt(R.styleable.ClipImageView_civ_padding_bottom, -1);
        mHeight = a.getInt(R.styleable.ClipImageView_civ_height, -1);
        mWidth = a.getInt(R.styleable.ClipImageView_civ_width, -1);
        mClipGravity = a.getInt(R.styleable.ClipImageView_civ_clip_gravity, -1);
        mResId = a.getResourceId(R.styleable.ClipImageView_civ_src, -1);
        a.recycle();
        init();
    }

    public ClipImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    public void setCivSrc(int resId) {
        this.mResId = resId;
        setup();
    }

    public int getCivSrc() {
        return mResId;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = BitmapFactory.decodeResource(getResources(), resId, mOptions);
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    private void initOptions() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = dm.densityDpi;
        options.inScreenDensity = dm.densityDpi;
        options.inTargetDensity = dm.densityDpi;
        this.mOptions = options;
    }


    private void init() {
        if (mWidth == -1 || mHeight == -1) {
            throw new IllegalArgumentException("civ_width or civ_height is not set");
        }
        setScaleType(SCALE_TYPE);
        initOptions();

        if (mResId != -1) {
            mBitmap = BitmapFactory.decodeResource(getResources(), mResId, mOptions);
        }
        mReady = true;
        setup();
        mSetupPending = false;
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }
        if (mBitmap == null) {
            invalidate();
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        int startX = 0;
        int startY = 0;
        int width = 0;
        int height = 0;

        width = mBitmap.getWidth();
        height = mBitmap.getHeight();
        if (mWidth > width || mHeight > height) {
            if (mWidth > width && mHeight > height) {
                float origanScale = width / (height * 1.0f);
                float clipScale = mWidth / (mHeight * 1.0f);
                if (clipScale > origanScale) {
                    mWidth = width-1;
                    mHeight = mWidth * height / width-1;
                } else {
                    mHeight = height-1;
                    mWidth = width * mHeight / height-1;
                }
            }
            if (mWidth > width) {
                mWidth = width-1;
                mHeight = mWidth * height / width-1;
            }
            if (mHeight > height) {
                mHeight = height-1;
                mWidth = width * mHeight / height-1;
            }
        }
        if (mPaddingBottom != -1) {
            startY = height - mHeight - mPaddingBottom;
            startY = notOutStartY(startY, false, height);
        }
        if (mPaddingRight != -1) {
            startX = width - mPaddingRight - mWidth;
            startX = notOutStartX(startX, false, width);
        }
        if (mPaddingLeft != -1) {
            startX = mPaddingLeft;
            startX = notOutStartX(startX, true, width);
        }
        if (mPaddingTop != -1) {
            startY = mPaddingTop;
            startY = notOutStartY(startY, true, height);
        }
        if (mClipGravity == 12) {
            startX = (width - mWidth) / 2;
            startY = (height - mHeight) / 2;
        }

        clipBitmap = Bitmap.createBitmap(mBitmap, startX, startY, mWidth, mHeight);

        setImageBitmap(clipBitmap);
        invalidate();
    }

    private int notOutStartX(int startX, boolean edge, int width) {
        if (startX < 0 || startX > width) {
            startX = edge ? 0 : width - mWidth;
        }
        return startX;
    }

    private int notOutStartY(int startY, boolean edge, int height) {
        if (startY < 0 || startY > height) {
            startY = edge ? 0 : height - mHeight;
        }
        return startY;
    }
}
