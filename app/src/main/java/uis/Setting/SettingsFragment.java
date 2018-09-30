package uis.Setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import uis.Communication.ResultAdapter;
import uis.IntroActivity;
import uis.Manage.AppData;
import uis.Manage.AppManager;
import uis.Manage.AppNotice;
import uis.festival.R;
import uis.home.MainActivity;
import uis.introduce.AppinformActivity;

public class SettingsFragment extends Fragment {

    private TextView setting;
    private String settingText = AppManager.getInstance().getAppData().getSetting();
    private Button button;
    private ListView listView;
    private String[] settingName = AppManager.getInstance().getAppData().getSettingName();
    private int language;
    // LanSelectDialog
    final String[] items = {"한국어", "English", "Japen(日本)", "China(中国)"};

    // 빈 생성자
    public SettingsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.festival, container, false );
        this.language = language;
        setting =  view.findViewById(R.id.category);
        setting.setText(settingText);
        button = view.findViewById(R.id.festival_backbtn);
        button.setVisibility(View.INVISIBLE);
        listView = view.findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(getContext(), settingName);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(settingName[0]);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppManager.getInstance().setLanguage(which);
                                AppManager.getInstance().setAppData(new AppData(which));
                                AppManager.getInstance().setAppNotice(new AppNotice(which));
                                dialog.dismiss();

                                // 프래그먼트 새로고침
                                FragmentManager fragmentManager = getFragmentManager();
                                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.content, new SettingsFragment()).commit();
                                String changeLang = AppManager.getInstance().getAppNotice().getChangeLang();
                                Toast.makeText(getContext(), changeLang, Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        break;
                    case 1:
                        Intent appInform = new Intent(getActivity(), AppinformActivity.class);
                        startActivity(appInform);
                        break;
                    case 2:
                        Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.help_pager_layout);

                        List<PagerModel> pagerArr = new ArrayList<>();
                        switch (AppManager.getInstance().getLanguage()) {
                            case 0:
                                pagerArr.add(new PagerModel(getContext(), "1", R.drawable.slide_1));
                                pagerArr.add(new PagerModel(getContext(), "2", R.drawable.slide_2));
                                pagerArr.add(new PagerModel(getContext(), "3", R.drawable.slide_3));
                                break;
                            case 1:
                                pagerArr.add(new PagerModel(getContext(), "1", R.drawable.slide_1_eng));
                                pagerArr.add(new PagerModel(getContext(), "2", R.drawable.slide_2_eng));
                                pagerArr.add(new PagerModel(getContext(), "3", R.drawable.slide_3_eng));
                                break;
                            case 2:
                                pagerArr.add(new PagerModel(getContext(), "1", R.drawable.slide_1_jpn));
                                pagerArr.add(new PagerModel(getContext(), "2", R.drawable.slide_2_jpn));
                                pagerArr.add(new PagerModel(getContext(), "3", R.drawable.slide_3_jpn));
                                break;
                            case 3:
                                pagerArr.add(new PagerModel(getContext(), "1", R.drawable.slide_1_chn));
                                pagerArr.add(new PagerModel(getContext(), "2", R.drawable.slide_2_chn));
                                pagerArr.add(new PagerModel(getContext(), "3", R.drawable.slide_3_chn));
                                break;
                        }
                        HelpPagerAdapter adapter = new HelpPagerAdapter(getContext(), pagerArr);

                        AutoScrollViewPager pager = (AutoScrollViewPager)dialog.findViewById(R.id.pager);
                        pager.setAdapter(adapter);

                        CirclePageIndicator pageIndicator =(CirclePageIndicator) dialog.findViewById(R.id.page_indicator);
                        pageIndicator.setViewPager(pager);
                        pageIndicator.setCurrentItem(0);

                        dialog.show();
                        break;
                }
            }
        });

        return view;
    }

    public class ListAdapter extends BaseAdapter{
        Context context;
        LayoutInflater inflater = null;
        private String[] settingName = {"설정1", "설정2"};
        TextView settingItem;

        public ListAdapter(Context context, String[] settingName) {
            this.context = context;
            this.settingName = settingName;
        }

        @Override
        public int getCount() {
            return settingName.length;
        }

        @Override
        public Object getItem(int position) {
            return settingName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
            {
                final Context context = parent.getContext();
                if (inflater == null)
                {
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                convertView = inflater.inflate(R.layout.list_settings, parent, false);
            }

            settingItem = (TextView) convertView.findViewById(R.id.settingItem);
            settingItem.setText(settingName[position]);

            return convertView;
        }
    }

}