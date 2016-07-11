package com.xueyan.personal.updateui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.io.IOException;




/*使用handler 更新ui*/
public class ThreadHandlerActivity extends Activity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private Button button;
    private static final  int SUCCESS=0;
    private static final  int FAILURE=1;

    private Thread thread;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    Toast.makeText(ThreadHandlerActivity.this, "成功获取图片", Toast.LENGTH_LONG).show();
                    break;
                case FAILURE:
                    Toast.makeText(ThreadHandlerActivity.this, "获取图片失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        imageView= (ImageView) findViewById(R.id.image);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        button= (Button) findViewById(R.id.button);

        Bitmap download = Util.getBitmap(this, "photo");
        if (download==null) {
            imageView.setImageResource(R.mipmap.aaaa);
        }else {
            imageView.setImageBitmap(download);
        }

    }
    public void onClick(View view) {
        if (thread==null){
            thread = new Thread(runnable);
            thread.start();                                     //
        }else {
            Toast.makeText(this,"开始下载",Toast.LENGTH_SHORT).show();
        }


    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            HttpClient hc=new DefaultHttpClient();
            HttpGet hg = new HttpGet("http://i1.sinaimg.cn/gm/2013/0611/U2456P115DT20130611152339.jpg");
            final Bitmap bitmap;
            try {
                HttpResponse hr = hc.execute(hg);
                bitmap = BitmapFactory.decodeStream(hr.getEntity().getContent());
                Util.saveDate(ThreadHandlerActivity.this,bitmap,"photo");
            } catch (IOException e) {
                handler.obtainMessage(FAILURE).sendToTarget();
                e.printStackTrace();
                return;
            }
            handler.obtainMessage(SUCCESS,bitmap).sendToTarget();

//            imageView.post(new Runnable() {
//                @Override
//                public void run() {
//                    imageView.setImageBitmap(bitmap);
//                }
//            });
        }
    };
}
