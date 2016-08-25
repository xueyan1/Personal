package com.xueyan.personal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xueyan.personal.R;
import com.xueyan.personal.qrcode.ImageUtil;
import com.xueyan.personal.qrcode.QRCodeUtil;
import com.xueyan.personal.service.FloatService;
import com.xueyan.personal.updateui.ThreadHandlerActivity;
import com.xueyan.personal.util.SharedPreferencesUtil;
import com.xueyan.personal.view.CircleImageView;
import com.xueyan.personal.util.Util;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity implements View.OnClickListener, ImageUtil.CropHandler {
    private ImageView back,qrcode,qrcode2;
    private TextView edit, title, edit_sex,edit_age;
    private EditText edit_name;
    private CircleImageView headView, rightHeadView;
    private CoordinatorLayout coordinatorLayout;
    private AlertDialog alertDialog = null;
    private Bitmap mBitmap;
    private   String neichen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        initDate();
        startService(new Intent(MainActivity.this, FloatService.class));
    }

    private void initDate() {
        neichen = SharedPreferencesUtil.getData(MainActivity.this, "neichen", null);
        edit_name.setText(SharedPreferencesUtil.getData(this, "neichen", null));        //昵称
        String sex = SharedPreferencesUtil.getData(this, "sex", null);                  //性别
        if (sex == null) {
            edit_sex.setText("男");
        } else {
            edit_sex.setText(sex);
        }
        String year = SharedPreferencesUtil.getData(this, "year", null);                //年龄
        if (year!=null){
            edit_age.setText(year);
        }else{
            edit_age.setText("0");
        }

        Util.setBitmap(this, headView,"photo");      //设置图片
        Util.setBitmap(this, qrcode,"qrcode");      //设置图片
        Util.setBitmap(this, qrcode2,"qrcode");      //设置图片


    }

    private void setListener() {
        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        edit_sex.setOnClickListener(this);
        headView.setOnClickListener(this);
        rightHeadView.setOnClickListener(this);
        edit_age.setOnClickListener(this);
        qrcode.setOnClickListener(this);
        qrcode2.setOnClickListener(this);
        edit_name.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.head).findViewById(R.id.back);//返回键
        qrcode = (ImageView) findViewById(R.id.qrcode);                     //二维码
        qrcode2 = (ImageView) findViewById(R.id.qrcode2);                     //二维码
        edit = (TextView) findViewById(R.id.edit);                          //标题右边的编辑
        title = (TextView) findViewById(R.id.title);                        //标题
        edit_sex = (TextView) findViewById(R.id.edit_sex);                   //性别
        edit_age = (TextView) findViewById(R.id.edit_age);                   //年龄
        edit_name = (EditText) findViewById(R.id.edit_name);               //昵称的输入
        headView = (CircleImageView) findViewById(R.id.imageHead);         //圆形头像
        rightHeadView = (CircleImageView) findViewById(R.id.rightHeadView); //右侧圆形头像
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);//SnackBar的容器

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Util.exit(this, coordinatorLayout);                           //退出
                break;
            case R.id.edit:                                                    //保存
                String trim = edit_name.getText().toString().trim();
                if (trim==null){
                    trim = edit_name.getHint().toString().trim();
                    SharedPreferencesUtil.saveData(this, "neichen", trim);
                }
                SharedPreferencesUtil.saveData(this, "neichen", trim);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_sex:
                Dialog dialog = Util.onCreateDialog(this, edit_sex);        //选择性别的对话
                dialog.show();
                break;
            case R.id.rightHeadView:                                             //点击更换头像，使用的是SnackBar
                Util.getSnackBar(coordinatorLayout).show();
                Util.count();
                break;
            case R.id.imageHead:

                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("选择照片").setSingleChoiceItems(new String[]{"拍照", "从相册选择"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent cameraIntent = ImageUtil
                                        .getCropHelperInstance()
                                        .buildCameraIntent();
                                startActivityForResult(cameraIntent,
                                        ImageUtil.REQUEST_CAMERA);
                                break;
                            case 1:
                                Intent galleryIntent = ImageUtil
                                        .getCropHelperInstance()
                                        .buildGalleryIntent();
                                startActivityForResult(galleryIntent,
                                        ImageUtil.REQUEST_GALLERY);
                                break;
                        }
                    }
                });
                alertDialog = build.create();
                alertDialog.show();
                break;
            case R.id.edit_age:

                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        DateFormat df= DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL, Locale.CHINA);//中国时区
                        String format = df.format(new Date());
                        int years= Integer.parseInt(format.substring(0,4));                                           //获取到年
                        int i = years - year;                                                                          //当前年减去设置的year即为年龄
                        edit_age.setText(i+"");
                        SharedPreferencesUtil.saveData(MainActivity.this,"year",i+"");                             //保存年龄

                    }
                },2000,1,2).show();
                break;
            case R.id.qrcode:
                final String filePath = Util.getFileRoot(MainActivity.this) + File.separator
                        + "qr_" + System.currentTimeMillis() + ".jpg";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        neichen= SharedPreferencesUtil.getData(MainActivity.this, "neichen", null);
                        if (neichen==null){
                            neichen = edit_name.getHint().toString().trim();
                        }

                        if (mBitmap==null){
                            mBitmap=  Util.getBitmap(MainActivity.this,"photo");
                            if (mBitmap==null){
                                mBitmap=  BitmapFactory.decodeResource(getResources(), R.mipmap.butterfly);
                            }
                        }
                        boolean success = QRCodeUtil.createQRImage(neichen, 800, 800,mBitmap , filePath);
                        if (success) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    qrcode2.setImageBitmap(BitmapFactory.decodeFile(filePath));
                                    qrcode.setImageBitmap(BitmapFactory.decodeFile(filePath));
                                    Util.saveDate(MainActivity.this,BitmapFactory.decodeFile(filePath),"qrcode");
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.qrcode2:
              //  final Intent intent = new Intent(this, WifiActivity.class);               //跳转到查看无线网密码
               // final Intent intent = new Intent(this, AsyncTaskActivity.class);          //异步下载图片
                final Intent intent = new Intent(this, ThreadHandlerActivity.class);    //线程下载图片，Handler更新UI
                startActivity(intent);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageUtil.getCropHelperInstance().sethandleResultListerner(MainActivity.this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Bitmap photo, int requestCode) {
        mBitmap = photo;
        Util.saveDate(this, photo,"photo");          //保存图片

        alertDialog.dismiss();
        switch (requestCode) {
            case ImageUtil.RE_GALLERY:
                rightHeadView.setImageBitmap(photo);
                headView.setImageBitmap(photo);
                break;
            case ImageUtil.RE_CAMERA:
                rightHeadView.setImageBitmap(photo);
                headView.setImageBitmap(photo);
                break;
        }
    }

    @Override
    public void onCropCancel() {

    }

    @Override
    public void onCropFailed(String message) {

    }

    @Override
    public Activity getContext() {
        return MainActivity.this;
    }


    /*返回键的实现*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Util.exit(this, coordinatorLayout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
