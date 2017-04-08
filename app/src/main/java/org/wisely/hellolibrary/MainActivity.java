package org.wisely.hellolibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.wisely.library.ClipImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AnimationImageView animationImageView= (AnimationImageView) findViewById(R.id.ani_iv);
//        animationImageView.addFrame(R.drawable.wifi_0);
//        animationImageView.addFrame(R.drawable.wifi_1);
//        animationImageView.addFrame(R.drawable.wifi_2);
//        animationImageView.addFrame(R.drawable.wifi_3);
//        animationImageView.addFrame(R.drawable.wifi_4);
//        animationImageView.addFrame(R.drawable.wifi_5);
//        animationImageView.setAnimationList(R.drawable.wifi_animation);
//        animationImageView.setOneshot(false);

//        ImageView iv= (ImageView) findViewById(R.id.image_bak);
//        Drawable imgdrawable=iv.getDrawable();
//        if(imgdrawable instanceof BitmapDrawable){
//            Log.d(TAG, "onCreate: is bitmapDrawable");
//        }
//        Log.d(TAG, "onCreate: "+String.format("imageview[%s,%s] drawable[%s,%s]",iv.getWidth(),iv.getHeight(),imgdrawable.getIntrinsicWidth(),imgdrawable.getIntrinsicHeight()));
        ClipImageView cview= (ClipImageView) findViewById(R.id.clip_iv);
//        cview.setCivSrc(R.drawable.image_bak);
    }
}
