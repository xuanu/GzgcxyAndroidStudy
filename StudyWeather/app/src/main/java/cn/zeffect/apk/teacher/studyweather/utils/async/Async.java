package cn.zeffect.apk.teacher.studyweather.utils.async;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;


import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;


public abstract class Async<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    protected final WeakReference<Context> mTarget;
    private final WeakReference<SweetAlertDialog> dialog;

    public Async(Context pWeakTarget) {
        if (pWeakTarget == null) {
            throw new NullPointerException("weak target is null");
        }
        mTarget = new WeakReference<Context>(pWeakTarget);
        SweetAlertDialog tmp = new SweetAlertDialog(mTarget.get(), SweetAlertDialog.PROGRESS_TYPE);
        tmp.setContentText("");
        tmp.setTitleText("loading……");
        tmp.setCanceledOnTouchOutside(false);
        tmp.setCancelable(false);
        dialog = new WeakReference<>(tmp);
    }

    public void showDialog(String title) {
        if (!TextUtils.isEmpty(title)) {
            dialog.get().setTitleText(title);
        }
        if (!dialog.get().isShowing()) {
            dialog.get().show();
        }
    }

    public void showDialog(String title, boolean canCancel) {
        if (!TextUtils.isEmpty(title)) {
            dialog.get().setTitleText(title);
        }
        dialog.get().setCancelable(canCancel);
        dialog.get().setCanceledOnTouchOutside(canCancel);
        if (!dialog.get().isShowing()) {
            dialog.get().show();
        }
    }

    @Override
    protected final void onPreExecute() {
        super.onPreExecute();
        final Context target = mTarget.get();
        if (target != null) {
            try {
                this.onPreExecute(target);//运行前的准备
            } catch (Exception e) {
            }
        } else {
        }
    }


    protected void onPreExecute(Context pTarget) throws Exception {
    }

    @Override
    protected final Result doInBackground(Params... paramses) {
        final Context target = mTarget.get();
        if (target != null) {
            try {
                return this.doInBackground(target, paramses);//后台运行中
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }


    protected abstract Result doInBackground(Context pTarget, Params... params) throws Exception;


    @Override
    protected final void onPostExecute(Result result) {
        super.onPostExecute(result);
        final Context target = mTarget.get();
        if (target != null) {
            try {
                this.onPostExecute(target, result);
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (dialog.get() != null) {
                    if (dialog.get().isShowing()) {
                        dialog.get().dismiss();
                    }
                }
            }
        } else {
        }
    }


    protected void onPostExecute(Context pTarget, Result pResult) throws Exception {

    }

    @Override
    protected final void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        final Context target = mTarget.get();
        if (target != null) {
            try {
                this.onProgressUpdate(target, values);
            } catch (Exception e) {
            }
        } else {
        }
    }


    protected void onProgressUpdate(Context pTarget, Progress... values) throws Exception {
    }

}
