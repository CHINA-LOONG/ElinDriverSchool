package com.elin.elindriverschool.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.elin.elindriverschool.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by imac on 16/12/30.
 */

public class ImageLoaderHelper {


    public static final int IMG_LOAD_DELAY=200;
    private static ImageLoaderHelper imageLoaderHelper;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options;
    private Context mContext;

//    public static ImageLoaderHelper getInstance(){
//        if(imageLoaderHelper==null)
//            imageLoaderHelper = new ImageLoaderHelper();
//        return  imageLoaderHelper;
//    }

    public ImageLoaderHelper(Context mContext) {
        this.mContext = mContext;
        init();
    }

//    public ImageLoaderHelper(){
//
//    }


    private void init(){
//    	System.out.println("--BaseApplication.getApplication()->"+BaseApplication.getInstance());
//    	imageLoader.init(ImageLoaderConfiguration.createDefault(HomeMainActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_default)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.drawable.img_default)//应修改为默认加载图片
                .delayBeforeLoading(IMG_LOAD_DELAY)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)	 //设置图片的解码类型
                .build();
        imageLoader = ImageLoader.getInstance();
    }

    /**
     * 鍔犺浇鍥剧墖
     * @param url
     * @param imageView
     */
    public  void loadImage(String url,ImageView imageView){
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        if (url!=null) {
            url = url.trim();
            imageLoader.displayImage(url, imageView, options);
        }
    }
}
