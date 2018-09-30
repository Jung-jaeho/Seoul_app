package uis.DataBase;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

import uis.Manage.AppManager;

/*
 * 서울열린데이터광장에서 주는 서울시문화행사정보 API를 파싱해오는 Class
 * 내부클래스로 AsyncTask를 상속받은 파서가 있다.
 * @author : 조재영
 */
public class ManageFestivalData {
    private int dataSize;

    private FestivalInformationVO festivalInformationVO; //파싱할 데이터가 임시로 저장되는 공간
    public ArrayList<FestivalInformationVO> festivalInformationVOArrayList; //파싱할 데이터가 저장될 공간 (데이터베이스)
    public ParseFestivalInformation parseFestivalInformation; //XML파싱
    String tag_name = null; // 파싱할 데이터 tag이름
    boolean bSet = false; //파싱할 데이터 bool 값

    //Holder
    private static class ManageFestivalDataHolder {
        public static final ManageFestivalData instance = new ManageFestivalData();
    }

    //인스턴스 가져오기
    public static ManageFestivalData getInstance() {
        return ManageFestivalDataHolder.instance;
    }

    //생성자
    public ManageFestivalData() {
        festivalInformationVO = new FestivalInformationVO();
        festivalInformationVOArrayList = new ArrayList<FestivalInformationVO>();

        parseFestivalInformation = new ParseFestivalInformation();
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB) {
            parseFestivalInformation.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            parseFestivalInformation.execute();
        }
    }
    public void cancel(){
        parseFestivalInformation.cancel(false);
    }

    private ArrayList<FestivalInformationVO> getFestivalInformationVOArrayList() {
        return festivalInformationVOArrayList;
    }

    private void setFestivalInformationVOArrayList(ArrayList<FestivalInformationVO> festivalInformationVOArrayList) {
        this.festivalInformationVOArrayList = festivalInformationVOArrayList;
    }

    public int getDataSize() {
        return dataSize;
    }

    /*
     * 데이터를 파싱해오는 내부클래스 AsyncTask를 상속하여 쓰레드같이 백그라운드에서 동작한다
     * @author : 조재영
     */
    private class ParseFestivalInformation extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dataSize = festivalInformationVOArrayList.size();
            Log.d("dataSize"," "+dataSize);
            // 진 추가: AppManager에 내용 복사
            AppManager.getInstance().setKoreanData(festivalInformationVOArrayList);
            AppManager.getInstance().setParseFlag(true);
            parseFestivalInformation =null;

        }
        FestivalInformationVO festivalInformationVO = new FestivalInformationVO();

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 1; i < 5000; i += 1000) {
                try {
                    if(isCancelled()){
                        parseFestivalInformation=null;
                        break;
                    }
                    String url = "http://openapi.seoul.go.kr:8088/4f444c4f797a6f6a34345764534a51/xml/SearchConcertDetailService/" + i + "/" + (i + 999) + "/";
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    URL xmlUrl = new URL(url);
                    xmlUrl.openConnection().getInputStream();
                    parser.setInput(xmlUrl.openStream(), "utf-8");
                    int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if(isCancelled()){
                            break;
                        }
                        //파싱한거 한묶음
                        if (eventType == XmlPullParser.START_TAG) { //START_TAG일 시 parser를 이용하여 이름을 가져온다  xml  api내용중에 큰제목부분 안드로이드 풀파서에 스타트태그가 무엇인가?
                            tag_name = parser.getName();
                            if (tag_name.equals("CULTCODE") || tag_name.equals("CODENAME") || tag_name.equals("TITLE") || tag_name.equals("PLACE") || tag_name.equals("MAIN_IMG")
                                    || tag_name.equals("TIME") || tag_name.equals("AGELIMIT") || tag_name.equals("SUBJCODE") || tag_name.equals("END_DATE") || tag_name.equals("STRTDATE")
                                    || tag_name.equals("ORG_LINK") || tag_name.equals("INQUIRY") || tag_name.equals("IS_FREE") || tag_name.equals("USE_TRGT") || tag_name.equals("GCODE"))
                                bSet = true;

                        } else if (eventType == XmlPullParser.TEXT) {//START_TAG가 아니라 TEXT일시 데이터 넣기
                            if (bSet) {
                                String data = parser.getText();
                                switch (tag_name) {

                                    case "CULTCODE":
                                        festivalInformationVO = new FestivalInformationVO();
                                        festivalInformationVO.setCultCode(data);
                                        break;
                                    case "CODENAME":
                                        festivalInformationVO.setCodeName(data);
                                        break;
                                    case "TITLE":
                                        String realTitle=data;
                                        realTitle= title(realTitle);
                                        festivalInformationVO.setTitle(realTitle);
                                        break;

                                    case "PLACE":
                                        festivalInformationVO.setPlace(data);
                                        break;
                                    case "MAIN_IMG":
                                        festivalInformationVO.setMain_Img(data);
                                        break;
                                    case "TIME":
                                        festivalInformationVO.setTime(data);
                                        break;
                                    case "AGELIMIT":
                                        festivalInformationVO.setAgeLimit(data);
                                        break;
                                    case "SUBJCODE":
                                        festivalInformationVO.setSubjCode(data);
                                        break;
                                    case "END_DATE":
                                        festivalInformationVO.setEnd_Date(data);
                                        break;
                                    case "STRTDATE":
                                        festivalInformationVO.setStart_Date(data);
                                        break;
                                    case "ORG_LINK":
                                        festivalInformationVO.setOrg_Link(data);
                                        break;
                                    case "INQUIRY":
                                        festivalInformationVO.setInquiry(data);
                                        break;
                                    case "IS_FREE":
                                        festivalInformationVO.setIs_Free(data);
                                        break;
                                    case "USE_TRGT":
                                        festivalInformationVO.setUse_Trgt(data);
                                        break;
                                    case "GCODE":
                                        festivalInformationVO.setGCode(data);
                                        festivalInformationVOArrayList.add(festivalInformationVO);
                                        break;

                                }
                                bSet = false;
                            }
                        }
                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
    }
    public String title(String replace) {
        replace = replace.replaceAll("&nbsp;", " ");
        replace = replace.replaceAll("&lt;", "<");
        replace = replace.replaceAll("&gt;", ">");
        replace = replace.replaceAll("&amp;", "&");
        replace = replace.replaceAll("&quot;", "\"");
        replace = replace.replaceAll("&#35;", "#");
        replace =  replace.replaceAll("&#39;" , "'") ;
        return replace;
    }

}