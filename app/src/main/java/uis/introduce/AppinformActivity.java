package uis.introduce;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uis.Manage.AppManager;
import uis.festival.R;

public class AppinformActivity extends AppCompatActivity {

    private Button backBtn;
    private TextView title, version, developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appinformation);
        backBtn = (Button)findViewById(R.id.appinfo_back_btn);
        title = (TextView) findViewById(R.id.Top);
        String[] settingName = AppManager.getInstance().getAppData().getSettingName();
        title.setText(settingName[1]);
        version = (TextView) findViewById(R.id.textView4);
        version.setText(AppManager.getInstance().getAppData().getVersion());
        developer = (TextView) findViewById(R.id.textView3);
        developer.setText(AppManager.getInstance().getAppData().getDeveloper());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}