package com.banzhi.album.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.banzhi.album.R;


public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loading_dialog_style);

    }

    private void init(@NonNull Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }
}
