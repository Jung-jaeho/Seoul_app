package uis.search;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uis.Communication.TranslateTask;
import uis.Manage.AppManager;
import uis.festival.R;

public class SearchDialog extends Dialog {
    private TextView textView;
    private EditText mContentView;
    private Button search_Btn;
    private Button exit_Btn;
    private onResultfromDialog listener;
    private String searchFestival = AppManager.getInstance().getAppData().getSearchFestival();
    private String search = AppManager.getInstance().getAppNotice().getSearch();


    public SearchDialog(@NonNull Context context, onResultfromDialog listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_custom_dialog);

        textView = (TextView) findViewById(R.id.textView4);
        textView.setText(searchFestival);
        mContentView = (EditText) findViewById(R.id.txt_content);
        search_Btn = (Button) findViewById(R.id.btn_search);
        exit_Btn = (Button) findViewById(R.id.btn_exit);


        exit_Btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dismiss();
           }
       });
        search_Btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mContentView.getText().toString().isEmpty())
                   Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();
               else {
                    String result = mContentView.getText().toString();
                    //추천검색기능 만들던중 (조재영)
                   translate(result,listener);
                   dismiss();
               }
           }
       });

    }

    public void translate(String string,onResultfromDialog listener){
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
        TranslateTask asyncTask = new TranslateTask(target, "ko", 2,listener);
        asyncTask.execute(string);  // NaverTranslateTask에서 결과 적용
    }


}
