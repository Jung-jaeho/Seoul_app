package uis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import uis.DataBase.ManageFestivalData;
import uis.DataBase.mShared;
import uis.Manage.AppData;
import uis.Manage.AppManager;
import uis.Manage.AppNotice;
import uis.festival.R;
import uis.home.MainActivity;
import uis.slider.IntroSliderActivity;

/*
 * 어플리케이션 시작 시 가장 처음으로 나타나는 액티비티 (LOGO)
 * MainActivity 를 실행시키며, 언어선택을 할 수 있다.
 * @author : 조재영
 */
public class IntroActivity extends Activity {
    Spinner spinner;
    Intent intent;
    TextView button;
    private Context mCon;
    public static AppData appData;
    public static AppNotice appNotice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AppManager.getInstance().setDisplaySize(getApplicationContext());//현재 디스플레이 정보 저장
        ManageFestivalData.getInstance();//이걸 안해주면 첫번째 눌렀을 때 파싱안댐

        mCon = this;

        spinner = findViewById(R.id.introspinner);
        button = findViewById(R.id.introstart);
        Log.e("size",spinner.getWidth()+"");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 언어 글자 색 하얀색으로 설정
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                //state pattern적용 by AppManager - sttFragment와 작용하도록 설정
                switch (position) {
                    case 0:
                        AppManager.getInstance().setLanguage(0);
                        break;
                    case 1:
                        AppManager.getInstance().setLanguage(1);
                        break;
                    case 2:
                        AppManager.getInstance().setLanguage(2);
                        break;
                    case 3:
                        AppManager.getInstance().setLanguage(3);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData = new AppData(AppManager.getInstance().getLanguage());
                AppManager.getInstance().setAppData(appData);
                appNotice = new AppNotice(AppManager.getInstance().getLanguage());
                AppManager.getInstance().setAppNotice(appNotice);


                if(mShared.isFirstIn(mCon))
                    intent = new Intent(mCon, IntroSliderActivity.class);
                else
                    intent = new Intent(mCon, MainActivity.class);

                startActivity(intent);
                finish();

            }
        });
    }
}
