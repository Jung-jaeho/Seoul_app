package uis.Setting;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class PagerModel {

    String id;
    int imgId;
    Context context;
    Drawable drawable;

    public PagerModel(Context context, String id, int imgId){
        this.id=id;
        this.imgId=imgId;
        this.context = context;
        this.drawable = this.context.getResources().getDrawable(this.imgId);
    }

    public Drawable getImg(){
        return this.drawable;
    }
    public void setimg(int imgId){
        this.imgId = imgId;
        this.drawable = this.context.getResources().getDrawable(this.imgId);;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }

}
