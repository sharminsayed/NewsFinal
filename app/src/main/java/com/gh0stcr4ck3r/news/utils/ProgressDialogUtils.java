package com.gh0stcr4ck3r.news.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
