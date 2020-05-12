package cn.zeffect.apk.teacher.studyweather.utils.async;

import android.content.Context;
import android.os.AsyncTask;

public abstract class Async<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {



    public Async(Context context) {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
    }
}
