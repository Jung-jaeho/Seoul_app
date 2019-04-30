package uis;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil  {

    public static void setImageByGlide(Context context, ImageView imageView, String imageurl){

        Log.e("<MARTY>>>","Pre Convert URL : " + imageurl);
        if (imageurl.contains("HTTP://CULTURE.SEOUL.GO.KR/data/ci/")) {
            String imageBaseurl = "http://culture.seoul.go.kr/data/ci/";
            imageurl = imageurl.replace("HTTP://CULTURE.SEOUL.GO.KR/data/ci/","");
            imageurl = imageBaseurl + imageurl;
        }
        Log.e("<MARTY>>>","Post Convert URL : " + imageurl);

        Glide.with(context).load(imageurl).into(imageView);
    }
}
