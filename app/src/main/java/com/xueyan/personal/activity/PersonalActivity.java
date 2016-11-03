package com.xueyan.personal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xueyan.personal.R;
import com.xueyan.personal.util.LoadLocalImageUtil;

public class PersonalActivity extends BaseActivity {
    private ImageView image1, image2, image3;
    DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        options = new DisplayImageOptions.Builder()
                // .showImageOnLoading(R.mipmap.film_cover_loading) //设置图片在下载期间显示的图片
                // .showImageForEmptyUri(R.mipmap.film_cover_loading_faile)//设置图片Uri为空或是错误的时候显示的图片
                //  .showImageOnFail(R.mipmap.film_cover_loading_faile)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                //.delayBeforeLoading(100)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(0))//是否设置为圆角，弧度为多少
                //  .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        initView();
        initDate();
    }


    private void initView() {
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);

    }

    private void initDate() {
        LoadLocalImageUtil.getInstance().displayFromDrawable(R.mipmap.photo,image1);
        LoadLocalImageUtil.getInstance().displayFromDrawable(R.mipmap.photo1,image2);
        LoadLocalImageUtil.getInstance().displayFromDrawable(R.mipmap.photo2,image3);

    }

    public void onClick(View view) {
        startActivity(new Intent(PersonalActivity.this, MainActivity.class));
        finish();
    }
}
