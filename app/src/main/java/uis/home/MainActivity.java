package uis.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import uis.Communication.SttFragment;
import uis.Favorite.FavoriteFragment;
import uis.Manage.AppManager;
import uis.Question.QuestionFragment;
import uis.Setting.SettingsFragment;
import uis.festival.LoadingActivity;
import uis.festival.R;
/*
 * 어플리케이션의 전반을 담당하는 MainActivity
 * MainActivity 위에서 HomeFragment를 시작으로 전반적인 Fragments들이 동작한다
 * BottomBavigationBar를 관리한다.
 * @author : 조재영
 */
public class MainActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            String exit = AppManager.getInstance().getAppNotice().getExit();
            Toast.makeText(getApplicationContext(), exit,Toast.LENGTH_SHORT).show();
            return;
        } if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            SttFragment sttFragment = new SttFragment();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_communication:
                    transaction.replace(R.id.content, sttFragment).commit();
                    return true;
                case R.id.navigation_favorite:
                    transaction.replace(R.id.content, new FavoriteFragment()).commit();
                    return true;
                case R.id.navigation_question:
                    transaction.replace(R.id.content, new QuestionFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    transaction.replace(R.id.content, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // loading activity
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new HomeFragment()).commit();

    }

}

