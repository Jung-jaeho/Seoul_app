package uis.Manage;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.ArrayList;

import uis.Communication.ResultAdapter;
import uis.DataBase.FestivalInformationVO;

/*
 * 어플리케이션의 전체적인 정보를 담당하는 SingleTon Pattern의 AppManager class
 * @author : 조재영
 */
public class AppManager {
    private Context context;
    private Point displaySize;
    private int language;   // 0: ko, 1: en, 2: ja, 3: zh

    private static AppManager instance;

    private AppData appData;
    private AppNotice appNotice;

    // dateset
    private ArrayList<String> sttData;
    private ArrayList<String> translateData;
    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private TextView translatedView;

    private ArrayList<FestivalInformationVO> KoreanData, EnglishData, ChineseData, JapaneseData;

    private static boolean parseFlag = false;

    private String searchText;
    private boolean asyncTaskEnd = false;

    //Random 추천 변수
    private int random_Recommend ;

    /*
    Class안에 ClassHolder를 두어 JVM의 Class Loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
     holder안에 선언된 Instance가 static이기 때문에 클래스 로딩시점에 한번만 호출되며 final을 이용하여
     다시 값이 할당되지 않도록 만든 방법
     */
    private static class AppManagerHolder {
        public static final AppManager instance = new AppManager();
    }

    public static AppManager getInstance() {
        return AppManagerHolder.instance;
    }

    private AppManager() {

    }
    public void setDisplaySize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        displaySize = new Point();
        displaySize.x = dm.widthPixels;
        displaySize.y = dm.heightPixels;
    }

    public Point getDisplaySize() {
        return displaySize;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getLanguage() {
        return language;
    }

    public TextView getTranslatedView() {
        return translatedView;
    }

    public void setTranslatedView(TextView translatedView) {
        this.translatedView = translatedView;
    }

    public AppData getAppData() {
        return appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
    }

    public AppNotice getAppNotice() {
        return appNotice;
    }

    public void setAppNotice(AppNotice appNotice) {
        this.appNotice = appNotice;
    }

    // Communication
    public ArrayList<String> getSttData() {
        return sttData;
    }

    public void setSttData(ArrayList<String> sttData) {
        this.sttData = sttData;
    }

    public ArrayList<String> getTranslateData() {
        return translateData;
    }

    public void setTranslateData(ArrayList<String> translateData) {
        this.translateData = translateData;
    }

    public ResultAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(ResultAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    // ManageFestivalData
    public ArrayList<FestivalInformationVO> getKoreanData() {
        return KoreanData;
    }

    public void setKoreanData(ArrayList<FestivalInformationVO> koreanData) {
        KoreanData = koreanData;
    }

    public ArrayList<FestivalInformationVO> getEnglishData() {
        return EnglishData;
    }

    public void setEnglishData(ArrayList<FestivalInformationVO> englishData) {
        EnglishData = englishData;
    }


    public ArrayList<FestivalInformationVO> getChineseData() {
        return ChineseData;
    }

    public void setChineseData(ArrayList<FestivalInformationVO> chineseData) {
        ChineseData = chineseData;
    }

    public ArrayList<FestivalInformationVO> getJapaneseData() {
        return JapaneseData;
    }

    public void setJapaneseData(ArrayList<FestivalInformationVO> japaneseData) {
        JapaneseData = japaneseData;
    }

    public void setParseFlag(boolean flag){
        parseFlag = flag;
    }
    public boolean getPasreFlag(){
        return parseFlag;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isAsyncTaskEnd() {
        return asyncTaskEnd;
    }

    public void setAsyncTaskEnd(boolean asyncTaskEnd) {
        this.asyncTaskEnd = asyncTaskEnd;
    }

    public void setRandom_Recommend(int random_Recommend) {
        this.random_Recommend = random_Recommend;
    }

    public int getRandom_Recommend() {
        return random_Recommend;
    }
}

