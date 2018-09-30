package uis.Communication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import uis.Manage.AppManager;
import uis.festival.R;

public class TranslateDialog extends Dialog{

    Spinner sourceLang, targetLang;
    TextView sourceText, targetText;
    Button translateBtn, exitBtn;

    String string;

    int startPosition = 0;
    int endPosition = AppManager.getInstance().getLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.translatedialog);

        // Spinner
        sourceLang = (Spinner) findViewById(R.id.sourceLang);
        sourceLang.setSelection(startPosition);// 초기값 설정
        sourceLang.setEnabled(false);   // 번역 언어 선택 못함 (한국어 고정)
        targetLang = (Spinner) findViewById(R.id.targetLang);
        targetLang.setSelection(endPosition);// 초기값 설정
        targetLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sourceText = (TextView) findViewById(R.id.sourceText);
        sourceText.setText(string);
        targetText = (TextView) findViewById(R.id.targetText);
        AppManager.getInstance().setTranslatedView(targetText);
        translate(string);

        translateBtn = (Button) findViewById(R.id.translateBtn);
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translate(string);
            }
        });

        exitBtn = (Button) findViewById(R.id.exit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public TranslateDialog(Context context, String string) {
        super(context);
        this.string = string;
    }

    public void translate(String string){
        String target = new String();
        switch (endPosition){
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
