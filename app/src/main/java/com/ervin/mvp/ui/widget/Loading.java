package com.ervin.mvp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.ervin.mvp.R;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;




public class Loading extends Dialog {

    private TextView tvLoadingText;

    public Loading(Context context) {
        super(context, R.style.LoadingDialog);
        setContentView(R.layout.dialog_loading);
        tvLoadingText = findViewById(R.id.loading_text);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
        setCancelable(false);
    }

    public void setLoadingText(String loadingText) {
        this.tvLoadingText.setText(loadingText);
    }

    public void setLoadingText(int loadingText) {
        this.tvLoadingText.setText(loadingText);
    }
}
