package com.m.moviememoir;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class SplashActivity extends XPermissionActivity {
    private final static int SWITCH_MAINACTIVITY = 1000;
    private Context mContext;
    public Handler mHandler = new Handler() {
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case SWITCH_MAINACTIVITY:
                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, LoginActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        mContext = this;
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(2000);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(2000);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        imageView.startAnimation(animationSet);
        translateAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationEnd(Animation animation) {
                requireAll();
            }
        });
    }

    private void requireAll() {
        requestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CALL_PHONE
        }, new XPermissionActivity.PermissionHandler() {
            @Override
            public void onGranted() {
                mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY, 1000);
            }

            @Override
            public boolean onNeverAsk() {
                new AlertDialog.Builder(mContext)
                        .setTitle("Apply Permission")
                        .setMessage("Enable permissions in Settings - application - permissions to ensure proper use of functionality")
                        .setPositiveButton("To Setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }
}
