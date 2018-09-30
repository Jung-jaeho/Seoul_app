package uis.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import uis.DataBase.FestivalInformationVO;
import uis.DataBase.ManageFestivalData;
import uis.Manage.AppManager;
import uis.festival.Festival;
import uis.festival.R;
import uis.search.SearchDialog;
import uis.search.onResultfromDialog;

/*
 * MainActivity 실행시 처음으로 나타나게 되며
 * 상단바를 가지며, ViewPager를통해 MapFragment와 HomeFragment를 연결해주는 Fragment
 * @author : 조재영
 */
public class HomeFragment extends Fragment implements onResultfromDialog {


    private static SearchDialog dlg;
    View view;


    private ImageView search_btn;

    private ArrayList<FestivalInformationVO> data;

    private String theme = AppManager.getInstance().getAppData().getTheme();
    private String map = AppManager.getInstance().getAppData().getMap();

    // 빈 생성자
    public HomeFragment() {
        data = new ArrayList<>();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dlg = new SearchDialog(getActivity(),this);
        data.addAll(ManageFestivalData.getInstance().festivalInformationVOArrayList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view =
                    inflater.inflate(R.layout.fragment_home, container, false);

            // 뷰페이져 연결
            MyPagerAdapter mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());

            ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
            mViewPager.setAdapter(mPagerAdapter);

            // 탭 연결
            TabLayout mTab = (TabLayout) view.findViewById(R.id.tabs);
            mTab.setupWithViewPager(mViewPager);


            //탭 설정


            //검색 버튼
            search_btn = (ImageView) view.findViewById(R.id.search);
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dlg.show();
                }
            });

        }

        return view;
    }

    /*
     * Theme와 Map을 띄워 줄 Viewpager를 관리하는 내부 AdapterClass
     * @author : 조재영
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {
        private final Fragment fragment[] = {new ThemeFragment(), new MapFragment()};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragment[position];
                case 1:
                    return fragment[position];
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return theme;
                case 1:
                    return map;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragment.length;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResult(String text,String originText) {
        ArrayList<FestivalInformationVO> datas = new ArrayList<>();

        if (data == null){
            return;
        }
        for (FestivalInformationVO obj : data){
            if (obj.getTitle().contains(text)){
                datas.add(obj);
            }
        }
        Intent intent = new Intent(getActivity(),Festival.class);
        intent.putExtra("category",text+"("+originText+")");
        intent.putExtra("data",datas);
        startActivity(intent);
    }


}