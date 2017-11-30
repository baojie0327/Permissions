package com.jackson.permission;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    //定位和联系人权限
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};

    //相机
    private static final String[] CAMERAS =
            {Manifest.permission.CAMERA};

    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.single).setOnClickListener(this);
        findViewById(R.id.mutiply).setOnClickListener(this);
        findViewById(R.id.fragment).setOnClickListener(this);
        findViewById(R.id.encapsulation).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                applySingle();
                break;
            case R.id.mutiply:
                applMutiply();
                break;
            case R.id.encapsulation:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
            case R.id.fragment:

                break;

        }
    }

    /**
     * 申请单个权限
     */
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void applySingle() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "Have Camera Permission", Toast.LENGTH_SHORT).show();
        } else {   // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    CAMERAS);
        }
    }

    /**
     * 申请多个权限
     */
    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void applMutiply() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!
            Toast.makeText(this, "Have All Permissions", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "同意了权限:" + +requestCode + ":" + perms.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "拒绝了权限:" + requestCode + ":" + perms.size(), Toast.LENGTH_SHORT).show();
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasCameraPermission() ? yes : no,
                            hasLocationAndContactsPermissions() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 是否已经开启摄像头权限
     *
     * @return
     */
    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, CAMERAS);
    }

    /**
     * 是否已经开启定位和联系人权限
     *
     * @return
     */
    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }


}
