package vn.edu.stu.thanhsang.managecar.utils;

import android.app.Activity;
import android.widget.Toast;

public class ViewHelper {
    public static void showToasts(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
