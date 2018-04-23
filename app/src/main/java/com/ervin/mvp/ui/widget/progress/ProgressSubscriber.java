package com.ervin.mvp.ui.widget.progress;

import android.content.Context;
import android.ervin.mvp.R;
import android.widget.Toast;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public abstract class ProgressSubscriber<T> extends ResourceSubscriber<T> implements ProgressCancelListener {

    private static final String TAG = ProgressSubscriber.class.getName();

    protected ProgressDialogHandler mProgressDialogHandler;
    protected Context mContext;

    /**
     * 自定义提示语{@link ProgressSubscriber#ProgressSubscriber(Context, String)}
     */
    public ProgressSubscriber(Context context) {
        this(context, null, false, false);
    }

    /**
     * @param context
     * @param content ProgressDialog的提示语
     */
    public ProgressSubscriber(Context context, String content) {
        this(context, content, false, false);
    }

    public ProgressSubscriber(Context context, boolean cancelable) {
        this(context, null, cancelable, false);
    }

    public ProgressSubscriber(Context context, boolean cancelable, boolean hideProgress) {
        this(context, null, cancelable, hideProgress);
    }


    /**
     * @param context
     * @param cancelable false 是否可取消
     */
    public ProgressSubscriber(Context context, String content, boolean cancelable, boolean hideProgress) {
        this.mContext = context;
        if (!hideProgress) {
            mProgressDialogHandler = new ProgressDialogHandler(context, content, cancelable, this);
        }
    }


    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        onCompleted0();

        dismissProgressDialog();
    }


    public void onCompleted0() {
    }

    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            onCompleted0();
            this.dispose();
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketException) {
            //连接失败
            Toast.makeText(mContext, mContext.getString(R.string.error_server_connect_fail), Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            //超时
            Toast.makeText(mContext, mContext.getString(R.string.error_server_connect_timeout), Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException) {
            //服务器出错
            HttpException httpException = (HttpException) e;
            int httpCode = httpException.code();
            if (httpCode >= 400 && httpCode < 500) {
                Toast.makeText(mContext, httpException.message(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.error_server_error), Toast.LENGTH_SHORT).show();
            }

        } else if (e instanceof UnknownHostException) {
            Toast.makeText(mContext, mContext.getString(R.string.error_no_network), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        onAllError(e);
        dismissProgressDialog();
    }

    protected void onAllError(Throwable e){}
}