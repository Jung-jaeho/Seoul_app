package uis.Manage;

public class AppData {

    /* 앱 전체의 메뉴 내용 저장 */
    private int langCode;

    //Recommend_popup
    private String gotoInfo;

    // Home Framgemnt
    private String theme, map;

    // Theme Fragement
    private String[] themeName = new String[6];

    // Map Fragement
    private String[] localName = new String[5];
    private String selectPlace;

    // Information Activity
    private String top;
    private String[] info = new String[3];
    private String clickMap;
    private String homepageBtn;
    private String[] pay = new String[2];
    private String useTarget, inquery;
    private String time;

    // Favorite Fragment
    private String favorite;

    // Setting Fragment
    private String setting;
    private String[] settingName = new String[3];

    // Search Dialog
    private String searchFestival;

    // App Information
    private String version, developer;

    //Recommend_popupActivity
    private String todaysRecommed;
    private String checkBoxText;

    //Hompace Error
    private String HomepageErrorMsg;

    public AppData(int langCode) {
        switch (langCode){
            case 0: // Korean
                theme = "테마"; map = "지도";
                themeName[0] = "공연"; themeName[1] = "교육/체험"; themeName[2] = "영화";
                themeName[3] = "전시"; themeName[4] = "축제"; themeName[5] = "기타";
                localName[0] = "서북권"; localName[1] = "동북권"; localName[2] = "동남권";
                localName[3] = "서남권"; localName[4] = "중구";
                selectPlace = "관심있는 지역을 선택해주세요.";
                top = "축제 정보";  homepageBtn = "▶ 홈페이지";
                info[0] = "상세정보"; info[1] = "위치정보"; info[2] = "기타정보";
                pay[0] = "요금 정보 : 무료\n";
                pay[1] = "요금 정보 : 홈페이지 참조\n";
                useTarget = "이용 대상 : ";
                time = "이용 시간 : ";
                inquery = "문의 : TEL) ";
                clickMap = "(자세히 보려면 지도를 클릭하세요.)";
                favorite = "관심있는 축제";
                setting = "설정";
                settingName[0] = "언어"; settingName[1] = "어플리케이션 정보"; settingName[2] = "도움말";
                searchFestival = "원하는 축제를 검색해보세요.";
                version = " ● 버전 정보"; developer = " ● 개발자 정보";
                todaysRecommed = "오늘의 추천 행사";
                checkBoxText = "오늘은 그만 볼래요.";
                gotoInfo = "바로가기";
                HomepageErrorMsg = "홈페이지를 찾을 수 없습니다.";
                break;
            case 1: // English
                theme = "Theme"; map = "Map";
                themeName[0] = "Show"; themeName[1] = "Education/Experience"; themeName[2] = "Movie";
                themeName[3] = "Exhibition"; themeName[4] = "Festival"; themeName[5] = "Etc";
                localName[0] = "North West"; localName[1] = "North East"; localName[2] = "East South";
                localName[3] = "South West"; localName[4] = "Central";
                selectPlace = "Please select the area you are interested in.";
                top = "Festival Information";  homepageBtn = "▶ Homepage";
                info[0] = "Details"; info[1] = "Location"; info[2] = "Etc";
                pay[0] = "Fare : Free\n";
                pay[1] = "Fare : See homepage\n";
                useTarget = "Use Target : ";
                time = "Time : ";
                inquery = "Inquery : TEL) ";
                clickMap = "(Click on the map for details.)";
                favorite = "Interested Festivals";
                setting = "Settings";
                settingName[0] = "Language"; settingName[1] = "Application Information"; settingName[2] = "Help";
                searchFestival = "Search for the festival you want.";
                version = " ● Version"; developer = " ● Developers";
                todaysRecommed = "Today's Featured Events";
                checkBoxText = "I will stop today.";
                gotoInfo = "Shortcuts";
                HomepageErrorMsg="Homepage not found.";
                break;
            case 2: // Japanese
                theme = "テーマ"; map = "地図";
                themeName[0] = "公演"; themeName[1] = "教育/体験"; themeName[2] = "映画";
                themeName[3] = "展示"; themeName[4] = "祭り"; themeName[5] = "その他";
                localName[0]="西北権";localName[1]="北東圏の";localName[2]="東南圏";
                localName[3]="西南圏";localName[4]="中区";
                selectPlace = "興味のある地域を選択してください。";
                top = "祭り情報";  homepageBtn = "▶ ホームページ";
                info[0] = "詳細"; info[1] = "ロケーション"; info[2] = "等";
                pay[0] = "料金：無料\n";
                pay[1] = "料金：ホームページ参照\n";
                useTarget = "ターゲットを使用する : ";
                time = "利用時間 : ";
                inquery = "問い合わせ : TEL) ";
                clickMap = "（詳細については、マップをクリックしてください。）";
                favorite = "興味のある祭り";
                setting = "設定";
                settingName[0] = "言語"; settingName[1] = "アプリケーション 情報"; settingName[2] = "ヘルプ";
                searchFestival = "希望祭りを検索しましょう。";
                version = " ● バージョン"; developer = " ● 開発者";
                todaysRecommed = "今日のお勧めイベント";
                checkBoxText = "今日はやめ燮。";
                gotoInfo = "ショートカット";
                HomepageErrorMsg="ホームページを見つけることができません。";
                break;
            case 3: // Chinese
                theme = "主题"; map = "地图";
                themeName[0] = "性能"; themeName[1] = "教育/经验"; themeName[2] = "电影";
                themeName[3] = "显示"; themeName[4] = "欢宴"; themeName[5] = "等等";
                localName[0] = "西北圈"; localName[1] = "东北亚圈"; localName[2] = "东南圈";
                localName[3] = "西南圈"; localName[4] = "中区";
                selectPlace = "请选择您感兴趣的区域。";
                top = "欢宴 信息";  homepageBtn = "▶ 网站";
                info[0] = "细节"; info[1] = "位置"; info[2] = "等等";
                pay[0] = "费用：免费\n";
                pay[1] = "费用：见主页\n";
                useTarget = "使用目标 : ";
                time = "使用时间 : ";
                inquery = "查询 : TEL) ";
                clickMap = "（点击地图了解详情。)";
                favorite = "有兴趣的节日";
                setting = "设置";
                settingName[0] = "语言"; settingName[1] = "申请信息"; settingName[2] = "帮助";
                searchFestival = "搜索你想要的节日。";
                version = " ● 版"; developer = " ● 开发商";
                todaysRecommed = "今天的特色活动";
                checkBoxText = "我今天会停下来.";
                gotoInfo = "快捷键";
                HomepageErrorMsg = "找不到主页。";
                break;
        }
    }

    public String getTheme() {
        return theme;
    }

    public String getMap() {
        return map;
    }

    public String[] getThemeName() {
        return themeName;
    }

    public String[] getLocalName() {
        return localName;
    }

    public String getSelectPlace() {
        return selectPlace;
    }

    public String getTop() {
        return top;
    }

    public String[] getInfo() {
        return info;
    }

    public String getHomepageBtn() {
        return homepageBtn;
    }

    public int getLangCode() {
        return langCode;
    }

    public String getClickMap() {
        return clickMap;
    }

    public String[] getPay() {
        return pay;
    }

    public String getUseTarget() {
        return useTarget;
    }

    public String getInquery() {
        return inquery;
    }

    public String getFavorite() {
        return favorite;
    }

    public String getSetting() {
        return setting;
    }

    public String[] getSettingName() {
        return settingName;
    }

    public String getTime(){
        return time;
    }

    public String getSearchFestival() {
        return searchFestival;
    }

    public String getVersion() {
        return version;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getTodaysRecommed(){ return todaysRecommed; }

    public String getCheckBoxText() { return checkBoxText; }

    public String getGotoInfo() { return gotoInfo; }

    public String getHomepageErrorMsg() { return HomepageErrorMsg; }
}