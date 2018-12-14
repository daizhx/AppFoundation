package smtech.com.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

//执行需要用户等待的耗时任务,弹出无法取消进度对话框，程序必须等待任务结束，才能进行下一步操作
public abstract class PrerequireTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Context context;
    private ProgressDialog progressDialog;
    //提示新
    private String hintMsg;

    protected PrerequireTask(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(context);
        if(hintMsg == null){
            hintMsg = context.getString(R.string.waitting);
        }
        progressDialog.setMessage(hintMsg);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    protected PrerequireTask(Context context, String msg) {
        this.context = context;
        hintMsg = msg;
        init();
    }

    @Override
    protected void onPreExecute() {
        //显示等待对话框
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        progressDialog.dismiss();
    }
}
