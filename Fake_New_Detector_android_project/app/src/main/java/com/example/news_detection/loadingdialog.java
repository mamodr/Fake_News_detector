package com.example.news_detection;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loadingdialog {
    private Activity activity;
    private AlertDialog dialog;
    loadingdialog (Activity myActivity) {
        activity = myActivity;
    }
    void startLoadingDialog() {
        AlertDialog. Builder builder = new AlertDialog. Builder (activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate (R.layout.loading_dialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
        void dismissDialog() {
            dialog.dismiss();
        }
}
