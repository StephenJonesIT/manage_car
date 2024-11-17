package vn.edu.stu.thanhsang.managecar.utils;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ImageHelper {
    private static final int CODE_REQUEST_PERMISSION = 300;

    public static void checkPermissionAndOpenSelector(
            AppCompatActivity activity,
            ActivityResultLauncher<Intent> launcher
    ) {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        boolean isPermission = true;

        for (String permission: permissions) {
            if(activity.checkSelfPermission(permission)!=PERMISSION_GRANTED){
                isPermission = false;
                break;
            }
        }

        if (!isPermission)
            activity.requestPermissions(permissions,CODE_REQUEST_PERMISSION);
        else
            openImageSelector(activity, launcher);
    }

    public static void onRequestPermissionsResult(
            AppCompatActivity activity,
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults,
            ActivityResultLauncher<Intent> launcher
    ) {
        if(requestCode==CODE_REQUEST_PERMISSION){
            boolean allPermissionsGranted = true;
            for (int grantResult: grantResults) {
                if (grantResult != PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted)
                openImageSelector(activity,launcher);
        }else{
            activity.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


     @SuppressLint("IntentReset")
     private static void openImageSelector(AppCompatActivity activity, ActivityResultLauncher<Intent> launcher) {
        Intent intentGallery = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentGallery.setType("image/*");

        Intent intentCamera = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        Intent chooser = Intent.createChooser(intentGallery,"Select Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{intentCamera});
        launcher.launch(chooser);
    }
}
