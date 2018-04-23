package com.ervin.mvp.ui.widget.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.ervin.mvp.ui.widget.Loading;


/**
 * Created by Laiyimin
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Loading mLoading;
    private ProgressCancelListener mCancelListener;

    private Context context;

    private String mContent;
    private boolean mCancelable;

    public ProgressDialogHandler(Context context) {
        this(context, null, false, null);
    }

    public ProgressDialogHandler(Context context, String content) {
        this(context, content, false, null);
    }

    public ProgressDialogHandler(Context context, boolean cancelable) {
        this(context, null, cancelable, null);
    }

    public ProgressDialogHandler(Context context, String content, boolean cancelable, ProgressCancelListener cancelListener) {
        super();
        this.context = context;
        this.mCancelable = cancelable;
        this.mCancelListener = cancelListener;
        this.mContent = content;
    }


    private void initProgressDialog() {
        if (mLoading == null) {
            mLoading = new Loading(context);
            mLoading.setCanceledOnTouchOutside(false);
            mLoading.setLoadingText(mContent);
            mLoading.setCancelable(mCancelable);
            if (mCancelable && mCancelListener != null) {
                mLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mCancelListener.onCancelProgress();
                    }
                });
            }

            if (!mLoading.isShowing()) {
                mLoading.show();
            }
        }
    }


    private void dismissProgressDialog() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
        context = null;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
