package uis.Communication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import uis.DataBase.FestivalInformationVO;
import uis.Manage.AppManager;
import uis.search.onResultfromDialog;


/*
    AsyncTask <Params, Progressm Result>
    Params - doInBackground 파라미터 타입이 되며, execute 메소드 인자 값이 됨
    Progress = doInBackground 작업 시 진행 단위의 타입으로 onProgressUpdate 파라미터 타입
    Result - doInBackground 리턴값으로 onPostExecute 파라미터 타입
 */
public class TranslateTask extends AsyncTask<String, Void, String > {

    // Naver Papago SMT api 인증
    private String clientId = "IV6puGrC9RTscZv0qMnq"; // 애플리케이션 클라이언트 아이디값
    private String clientSecret = "0_WFvhmicR";  // 애플리케이션 클라이언트 시크릿값

    private String sourceLang, targetLang;  // 언어 선택
    private onResultfromDialog listener;
    private int dataType = 0;   // 번역 데이터 구분

    private String sourceText;

    // Stt 데이터 번역용 생성자
    public TranslateTask(String source, String target){
        sourceLang = source;
        targetLang = target;
        //Log.e("langauage", source+" " +target);
    }

    // 앱 데이터 번역용 생성자
    public TranslateTask(String source, String target, int dataType){
        sourceLang = source;
        targetLang = target;
        this.dataType = dataType;
        //Log.e("langauage", source+" " +target);
    }
    //검색용 생성자
    public TranslateTask(String source, String target, int dataType,onResultfromDialog listener){
        sourceLang = source;
        targetLang = target;
        this.dataType = dataType;
        this.listener = listener;
        //Log.e("langauage", source+" " +target);
    }


    /* 백그라운드 작업을 실행하기 전 실행되는 부분
           AsyncTask 라는 single work thread가 이후 생성
        */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // thread가 작업이 끝났을 때의 동작
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        /* 번역 결과를 받아서 처리
           JSON 데이터를 자바 객체로 변환 -> Gson 사용
         */

        if(s != null) {
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObj = parser.parse(s.toString())
                    // 원하는 데이터까지 찾아 들어감
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result");

            // 결과 저장
            TranslatedItem item = gson.fromJson(rootObj.toString(), TranslatedItem.class);
            String result = item.getTranslatedText();

            if (dataType == 0) {
                translateSttData(result);
            } else if (dataType == 1){
                translateAppData(result);
            } else if (dataType == 2){
                translateSearchData(result);
            }

        }else if(sourceLang == targetLang){ // 같은 언어로 번역 예외 처리
            if(dataType == 0){
                translateSttData(sourceText);
            } else if (dataType == 1){
                translateAppData(sourceText);
            } else if (dataType == 2){
                translateSearchData(sourceText);
            }
        }else{
            Log.d("Error", "error");
        }

    }

    /* 새로 만든 thread 백그라운드 작업을 수행
       JSON 형태로 결과를 반환
    */
    @Override
    protected String doInBackground(String... strings) {

        sourceText = strings[0];

        if(sourceText != null && sourceLang != targetLang) {
            try {
                String text = URLEncoder.encode(sourceText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/language/translate";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                // post request
                String postParams = "source=" + sourceLang + "&target=" + targetLang + "&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());

                return response.toString();
            } catch (Exception e) {
                System.out.println(e);

                return null;
            }

        }

        return null;
    }

    public void translateSttData(String result){

        ArrayList<FestivalInformationVO> data;

        ArrayList<String> strings = AppManager.getInstance().getTranslateData();
        strings.add(result);
        int position = strings.size()-1;
        AppManager.getInstance().getmAdapter().notifyItemInserted(position);

        // Automatically scroll to bottom of RecyclerView
        position += AppManager.getInstance().getSttData().size();
        AppManager.getInstance().getmRecyclerView().scrollToPosition(position);

    }

    public void translateAppData(String result){
        TextView textView = AppManager.getInstance().getTranslatedView();
        textView.setText(result);
    }

    public void translateSearchData(String result){
        listener.onResult(result,sourceText);
    }

    public class TranslatedItem{
        String translatedText;

        public String getTranslatedText() {
            return translatedText;
        }
    }
}
