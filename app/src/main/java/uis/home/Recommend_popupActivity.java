package uis.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uis.Communication.TranslateTask;
import uis.DataBase.BitmapDownloaderTask;
import uis.DataBase.FestivalInformationVO;
import uis.Manage.AppManager;
import uis.festival.Information;
import uis.festival.R;

public class Recommend_popupActivity extends Activity {
    ConstraintLayout constraintLayout;
    private FestivalInformationVO data;
    TextView todayRecommend;
    TextView festv_Name;
    TextView festv_date;
    ImageView recommend_Img;
    CheckBox check;
    BitmapDownloaderTask task;
    Button exit;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task = null;
    }

    @Override
    public void onBackPressed() {
        return;
    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        return false;
    }
    Button go_info;
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.recommend_popup);
        constraintLayout = findViewById(R.id.popup_layout);

        todayRecommend = (TextView)findViewById(R.id.today_recommend);
        todayRecommend.setText(AppManager.getInstance().getAppData().getTodaysRecommed());

        Intent intent = getIntent();
        data = (FestivalInformationVO)intent.getExtras().get("data");

        //축제이름 설정하기
        festv_Name = findViewById(R.id.recommend_Name);
        festv_Name.setText(data.getTitle());
        //축제이름 해당하는 언어로 번역
        AppManager.getInstance().setTranslatedView(festv_Name);
        translate(data.getTitle());

        festv_date = findViewById(R.id.festv_date);
        festv_date.setText(data.getStart_Date() + "~" + data.getEnd_Date());

        recommend_Img = (ImageView)findViewById(R.id.recommend_Img);

        check = (CheckBox) findViewById(R.id.check);
        check.setText(AppManager.getInstance().getAppData().getCheckBoxText());

        check.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                check.setChecked(true);
                //눌리면 추천기능 플래그값 변경
                SharedPreferences pref = getSharedPreferences("recommend_info",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",true);
                editor.commit();
                finish();
            }
        });

        exit = (Button)findViewById(R.id.popup_exit);
        exit.bringToFront();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        task = new BitmapDownloaderTask(recommend_Img);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data.getMain_Img());
        go_info = findViewById(R.id.go_Info);

        go_info.setText(AppManager.getInstance().getAppData().getGotoInfo());

        go_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informationIntent = new Intent(getApplicationContext(),Information.class);
                informationIntent.putExtra("data",data);
                startActivity(informationIntent);
                finish();
            }
        });
    }
    public void translate(String string){
        String target = new String();
        switch (AppManager.getInstance().getLanguage()){
            case 0:
                target = "ko";
                break;
            case 1:
                target = "en";
                break;
            case 2:
                target = "ja";
                break;
            case 3:
                target = "zh-CN";
                break;
        }
        TranslateTask asyncTask = new TranslateTask("ko", target, 1);
        asyncTask.execute(string);  // NaverTranslateTask에서 결과 적용
    }
}