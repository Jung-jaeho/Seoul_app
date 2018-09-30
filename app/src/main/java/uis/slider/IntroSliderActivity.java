package uis.slider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import uis.Manage.AppManager;
import uis.festival.R;
import uis.home.MainActivity;
import uis.DataBase.mShared;


public class IntroSliderActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager pager;
    private CircleIndicator indicator;
    private ArrayList<Fragment> views;
    private VPagerAdapter adapter;
    private TextView skip,next;

    private RelativeLayout container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introslider);

        pager = (ViewPager)findViewById(R.id.pager);
        indicator = (CircleIndicator)findViewById(R.id.indicator);
        skip = (TextView)findViewById(R.id.skipbtn);
        next = (TextView)findViewById(R.id.nextbtn);
        container = (RelativeLayout)findViewById(R.id.pager_container);


        skip.setOnClickListener(this);
        next.setOnClickListener(this);


        initView();

    }
    void initView(){

        views = new ArrayList<>();
        int tutoimgs[] =new int[3];
        switch (AppManager.getInstance().getLanguage()){
            case 0:
                tutoimgs[0] = R.drawable.slide_1;
                tutoimgs[1] = R.drawable.slide_2;
                tutoimgs[2] = R.drawable.slide_3;
                break;
            case 1 :
                tutoimgs[0] = R.drawable.slide_1_eng;
                tutoimgs[1] = R.drawable.slide_2_eng;
                tutoimgs[2] = R.drawable.slide_3_eng;
                break;
            case 2:
                tutoimgs[0] = R.drawable.slide_1_jpn;
                tutoimgs[1] = R.drawable.slide_2_jpn;
                tutoimgs[2] = R.drawable.slide_3_jpn;
                break;
            case 3 :
                tutoimgs[0] = R.drawable.slide_1_chn;
                tutoimgs[1] = R.drawable.slide_2_chn;
                tutoimgs[2] = R.drawable.slide_3_chn;
                break;
        }


        views.add(tuto_Fragment_one.newInstance(tutoimgs[0]));
        views.add(tuto_Fragment_two.newInstance(tutoimgs[1]));
        views.add(tuto_Fragment_three.newInstance(tutoimgs[2]));


        adapter = new VPagerAdapter(getSupportFragmentManager(),views);

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) { }
            @Override
            public void onPageSelected(int position) {

                if(adapter.getCount() -1 == position){
                    // 뷰페이저가 마지막 화면일때  SKIP 버튼 비활성화 및  NEXT 버튼 텍스트 변경
                    skip.setVisibility(View.GONE);
                    next.setText("FINISH");
                } else{
                    // 뷰페이저가 마지막 화면이 아닐때  SKIP 버튼 활성화 및  NEXT 버튼 복구
                    skip.setVisibility(View.VISIBLE);
                    next.setText("NEXT");
                }

            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextbtn:
                if(pager.getCurrentItem() == pager.getAdapter().getCount() -1) {
                    mShared.setFirstIn(this);
                    Intent init = new Intent(IntroSliderActivity.this,MainActivity.class);
                    startActivity(init);
                    finish();

                } else // 마지막 화면이 아닐때  다음페이지 넘어가는 기능
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                break;
            case R.id.skipbtn:
                mShared.setFirstIn(this);
                Intent init = new Intent(IntroSliderActivity.this,MainActivity.class);
                startActivity(init);
                finish();

                break;

            default:
                pager.setCurrentItem(0);
                container.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
                break;
        }
    }
}