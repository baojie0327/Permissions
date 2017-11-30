package com.jackson.permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class Main2Activity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks{

    //相机
    private static final String[] CAMERAS =
            {Manifest.permission.CAMERA};

    private static final int RC_CAMERA_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.single).setOnClickListener(this);
        findViewById(R.id.mutiply).setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                applySingle();
                break;
            case R.id.mutiply:
                //   applMutiply();
                break;
        }
    }

    /**
     * 申请单个权限
     */
    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void applySingle() {
        if ( hasCameraPermission(CAMERAS)){
            Toast.makeText(this, "Have Camera Permission", Toast.LENGTH_SHORT).show();
        }else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    CAMERAS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(Main2Activity.this, "同意了权限:" + +requestCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(Main2Activity.this, "拒绝了权限:" + requestCode, Toast.LENGTH_SHORT).show();
    }

}
