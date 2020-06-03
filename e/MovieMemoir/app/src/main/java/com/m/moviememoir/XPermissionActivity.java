package com.m.moviememoir;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.m.moviememoir.Utils.PermissionUtils;


public class XPermissionActivity extends AppCompatActivity {

    private PermissionHandler mHandler;

    protected void requestPermission(String[] permissions, PermissionHandler handler) {
        if (PermissionUtils.hasSelfPermissions(this, permissions)) {
            handler.onGranted();
        } else {
            mHandler = handler;
            ActivityCompat.requestPermissions(this, permissions, 001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mHandler == null) return;

        if (PermissionUtils.verifyPermissions(grantResults)) {
            mHandler.onGranted();
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                if (!mHandler.onNeverAsk()) {
                    Toast.makeText(this, "Permission has been denied. Please open it in Settings - application - permission", Toast.LENGTH_SHORT).show();
                }

            } else {
                mHandler.onDenied();
            }
        }
    }


    public abstract class PermissionHandler {
        public abstract void onGranted();

        public void onDenied() {
        }

        public boolean onNeverAsk() {
            return false;
        }
    }
}
