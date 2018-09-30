package uis.Setting;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uis.festival.R;

public class HelpPagerAdapter extends PagerAdapter{

    Context context;
    List<PagerModel> pagerArr;
    LayoutInflater inflater;


    public HelpPagerAdapter(Context context, List<PagerModel> pagerArr){
        this.context= context;
        this.pagerArr=pagerArr;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount(){
        return pagerArr.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        View view = inflater.inflate(R.layout.help_pager_list_item,container,false);

        ImageView iv = (ImageView) view.findViewById(R.id.img);

        view.setTag(position);

        ((ViewPager)container).addView(view);

        PagerModel model = pagerArr.get(position);

        iv.setImageDrawable(model.getImg());
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view ==((View)object);
    }
    @Override
    public void destroyItem(ViewGroup container,int position, Object object){

        ((ViewPager)container).removeView((View)object);
    }
}
