package uis.festival;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import uis.DataBase.FestivalInformationVO;
import uis.DataBase.ManageFestivalData;
import uis.Manage.AppManager;
import uis.home.Recommend_popupActivity;


public class LoadingActivity extends Activity {
    Thread thread;
    Thread AnimationThread;
    public ArrayList<FestivalInformationVO> translatedArrayList = new ArrayList<>();
    ImageView loading;
    private static int count=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
        startAnimation();
    }
    private void startAnimation(){
        AnimationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Animation anim = AnimationUtils.loadAnimation(
                        getApplicationContext(),
                        R.anim.rotate_anim);
                loading = findViewById(R.id.loading);
                loading.startAnimation(anim);
            }
        });
        AnimationThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(count==0) {
            CheckRecommend();
            count++;
        }
    }

    private void startLoading(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!AppManager.getInstance().getPasreFlag()) {
                    // 진 추가

                }
                //랜덤한 추천결과 뽑아내기
                Random random = new Random();
                int dataSize = ManageFestivalData.getInstance().getDataSize()+1;
                int rand = random.nextInt(dataSize);
                AppManager.getInstance().setRandom_Recommend(rand);
                finish();

            }
        });
        thread.start();

    }
    public void CheckRecommend(){
        SharedPreferences pref = this.getSharedPreferences("recommend_info", MODE_PRIVATE);
        String date = pref.getString("date","");
        //오늘 날짜 불러오기
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String Today = String.valueOf(year)+String.valueOf(month)+String.valueOf(day);

        if(date.equals("")){//만약 데이터베이스에 날짜정보가 아예 없다면
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("date",Today);//현재 날짜 저장
            editor.putBoolean("flag",false);//오늘 보지않기 체크할 flag저장
            editor.commit();
            PopupActivityStart();
        }
        else if(date.equals(Today)){//데이터베이스에 날짜 정보가 '오늘'일 경우

            boolean flag = pref.getBoolean("flag",false);
            if(flag) {//플래그 확인 (체크 : true)
                ;
            }
            else{ // (체크x : false)
                PopupActivityStart();
            }

        }
        else{//데이터베이스에 날짜 정보가 있는데 오늘이 아닐경우
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("flag",false); //플래그값 초기화
            editor.remove("date"); //원래있던 정보 삭제
            editor.putString("date",Today); //오늘 정보로 초기화
            editor.commit(); //커밋
            PopupActivityStart();//팝업창 띄우기
        }

    }
    public void PopupActivityStart(){
        Intent intent = new Intent(getApplicationContext(), Recommend_popupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("data", ManageFestivalData.getInstance().festivalInformationVOArrayList.get(AppManager.getInstance().getRandom_Recommend()));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        thread.interrupt();
        finishAffinity();
    }
}