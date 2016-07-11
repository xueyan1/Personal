package com.xueyan.personal.updateui;

;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xueyan.personal.R;
import com.xueyan.personal.util.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


/*使用AsyncTask更新ui*/
public class AsyncTaskActivity extends Activity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        imageView= (ImageView) findViewById(R.id.image);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        button= (Button) findViewById(R.id.button);

        Bitmap download = Util.getBitmap(this, "download");
        if (download==null) {
            imageView.setImageResource(R.mipmap.aaaa);
        }else {
            imageView.setImageBitmap(download);
        }
    }

    public void onClick(View view) {

        GetPhoto gp = new GetPhoto();
        gp.execute("http://img3.redocn.com/tupian/20151015/dongmanyouxigaoqingrenwusucai_5093588.jpg");
    }

    class GetPhoto extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected void onPreExecute() {
            imageView.setImageBitmap(null);
                progressBar.setProgress(0);
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            publishProgress(0);
            HttpClient hc = new DefaultHttpClient();
            publishProgress(30);
            HttpGet hg = new HttpGet( params[0]);
            final Bitmap bm;
            try {
                HttpResponse hr = hc.execute(hg);
                bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
                Util.saveDate(AsyncTaskActivity.this,bm,"download");    //保存下图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            publishProgress(100);
            return bm;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                Toast.makeText(AsyncTaskActivity.this, "成功获取图片", Toast.LENGTH_LONG).show();
                imageView.setImageBitmap(bitmap);
            }else {
                imageView.setImageResource(R.mipmap.butterfly);
                Toast.makeText(AsyncTaskActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            progressBar.setProgress(0);
        }
    }

}
