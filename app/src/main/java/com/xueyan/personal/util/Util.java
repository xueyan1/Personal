package com.xueyan.personal.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xueyan.personal.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Administrator on 2016/6/29.
 */
public class Util {

    private static long exitTime = 0;

    /*退出应用*/
    public static void exit(Activity activity, CoordinatorLayout coordinatorLayout) {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Snackbar.make(coordinatorLayout, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            //Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            activity.finish();
            System.exit(0);
        }
    }

    //生成选择对话框
    public static Dialog onCreateDialog(final Context context, final TextView textview) {
        final String[] items = {"男", "女"};
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请选择性别");
        final ChoiceOnClickListener choiceListener = new ChoiceOnClickListener();

        builder.setSingleChoiceItems(items, 0, choiceListener);

        DialogInterface.OnClickListener btnListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        int choiceWhich = choiceListener.getWhich();
                        String hobbyStr = items[choiceWhich];
                                /*保存起来*/
                        SharedPreferencesUtil.saveData(context, "sex", hobbyStr);
                        textview.setText(hobbyStr);
                    }
                };
        builder.setPositiveButton("确定", btnListener);
        dialog = builder.create();
        return dialog;
    }

    private static class ChoiceOnClickListener implements DialogInterface.OnClickListener {
        private int which = 0;

        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            this.which = which;
        }

        public int getWhich() {
            return which;
        }
    }


    public static Snackbar getSnackBar(final CoordinatorLayout coordinatorLayout) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "更换头像", Snackbar.LENGTH_LONG).setAction("选择方式", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "相册", Snackbar.LENGTH_LONG).setAction("打开", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(coordinatorLayout, "去点头像试试", Snackbar.LENGTH_SHORT).show();

                    }
                });
                setSnackbarMessageTextColor(snackbar, Color.parseColor("#FF0000"));
                snackbar.show();
            }
        });
        setSnackbarMessageTextColor(snackbar, Color.parseColor("#FFFFFF"));
        return snackbar;
    }


    /*获得SnackBar中右边textView的对象，然后设置字体颜色*/
    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

    /*SncakBar用的多了消耗内存，调用这个系统主动回收*/
    private static int messageShowCount = 0;
    private static int gcCount = 5;

    public static void count() {
        messageShowCount++;
        if (messageShowCount >= gcCount) {
            System.gc();
            messageShowCount = 0;
        }
    }

    /*保存图片*/
    public static void saveDate(Context context, Bitmap bitmap, String string) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        String imageString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        SharedPreferencesUtil.saveData(context, string, imageString);
    }
        /*获取bitmap*/
    public static Bitmap getBitmap(Context context, String string) {
        Drawable image = null;
        String photo = SharedPreferencesUtil.getData(context, string, null);
        if (photo != null) {
            byte[] imageBytes = Base64.decode(photo.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            image = Drawable.createFromStream(byteArrayInputStream, "image");
            drawable2Bitmap(image);
        }
        return drawable2Bitmap(image);
    }

    /*设置图片*/
    public static void setBitmap(Context context, ImageView view, String string) {
        String photo = SharedPreferencesUtil.getData(context, string, null);
        if (photo != null) {
            byte[] imageBytes = Base64.decode(photo.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            view.setImageDrawable(Drawable.createFromStream(byteArrayInputStream, "image"));
        } else {
            view.setImageDrawable(context.getResources().getDrawable(R.mipmap.aaaa));
        }
    }

    //文件存储根目录
    public static String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable==null) return null ;
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);

        return bitmap;
    }

    // Bitmap转换成Drawable
    public Drawable bitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable d = (Drawable) bd;
        return d;
    }
}
