package com.jackson.permission; /**
 * PermissionUtil  2017-11-29
 * Copyright (c) 2017 KL Co.Ltd. All right reserved.
 */

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * class description here
 *
 * @author Jackson
 * @version 1.0.0
 *          since 2017 11 29
 */
public class PermissionUtil implements EasyPermissions.PermissionCallbacks{


    public static final int RC_CAMERA_PERM = 0;  //相机权限



    /**
     * 请求单个权限
     */
    public static void requsetSingle(Activity activity, String[] perm, String permission, int code,PermissionResult mPermissionResult) {

        //是否有权限
        if (EasyPermissions.hasPermissions(activity, perm)) {
            // Have permission, do the thing!
            mPermissionResult.havePermission();
        } else {
            EasyPermissions.requestPermissions(
                    activity,
                    permission,
                    code,
                    perm);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }




    /**
     * 申请权限结果
     */
    public interface PermissionResult {
        void havePermission();               //已经有权限

        void onPermissionsGranted(int requestCode);  //同意权限

        void onPermissionsDenied(int requestCode);   //拒绝权限
    }

}

