package uis.DataBase;

import java.io.Serializable;

/*
 * 파싱된 데이터를 보관하게 될 ValueObject Class
 * 데이터베이스의 한 테이블이라고 생각하면 된다.
 * @author : 조재영
 */
//홈페이지,주최,주관및 후원,기타내용,이용요금 제외
public class FestivalInformationVO implements Serializable {
    private String Total_Count; //총데이터 개수
    private String Result_Code; //요청결과코드
    private String Result_Message;//요청결과 메시지

    private String CultCode;//문화행사 코드
    private String SubjCode;//장르분류코드
    private String CodeName;//장르명
    private String Title;//제목
    private String Start_Date;//시작날
    private String End_Date;//종료날
    private String Time;//시간
    private String Place; //장소
    private String Org_Link;//원문링크주소
    private String Main_Img;//대표이미지
    private String Use_Trgt;//이용대상
    private String Inquiry;//문의
    private String AgeLimit;//연령
    private String Is_Free;//무료구분
    private String Ticket;//할인_티켓_예매정보
    private String Program;//프로그램 소개
    private String Player; //출연자 정보
    private String Contents;//본문
    private String GCode;//자치구
    private String Fac_Code;//문화공간코드
    private String Org_Code;//정보제공기관코드
    private String ThemeCode; //테마분류코드

        public FestivalInformationVO(String CultCode,String CodeName,String Title,String Place,String Main_img,String Time,String AgeLimit,
                                     String SubjCode,String End_Date,String Start_Date,String Org_Link,String Inquiry,
                                     String Is_Free,String Use_Trgt, String GCode ){
        this.CultCode = CultCode;
        this.CodeName = CodeName;
        this.Title = Title;
        this.Place = Place;
        this.Main_Img=Main_img;
        this.Time=Time;
        this.AgeLimit=AgeLimit;
        this.SubjCode=SubjCode;
        this.End_Date=End_Date;
        this.Start_Date=Start_Date;
        this.Org_Link=Org_Link;
        this.Inquiry=Inquiry;
        this.Is_Free=Is_Free;
        this.Use_Trgt=Use_Trgt;
        this.GCode = GCode;
    }
    public FestivalInformationVO(){

    }

    public String getResult_Code() {
        return Result_Code;
    }

    public String getInquiry() {
        return Inquiry;
    }

    public String getOrg_Link() {
        return Org_Link;
    }

    public String getResult_Message() {
        return Result_Message;
    }

    public String getAgeLimit() {
        return AgeLimit;
    }

    public String getSubjCode() {
        return SubjCode;
    }

    public String getTime() {
        return Time;
    }

    public String getTotal_Count() {
        return Total_Count;
    }

    public String getUse_Trgt() {
        return Use_Trgt;
    }

    public void setResult_Code(String result_Code) {
        Result_Code = result_Code;
    }

    public void setInquiry(String inquiry) {
        Inquiry = inquiry;
    }

    public void setAgeLimit(String ageLimit) {
        AgeLimit = ageLimit;
    }

    public void setOrg_Link(String org_Link) {
        Org_Link = org_Link;
    }

    public void setResult_Message(String result_Message) {
        Result_Message = result_Message;
    }

    public void setSubjCode(String subjCode) {
        SubjCode = subjCode;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setTotal_Count(String total_Count) {
        Total_Count = total_Count;
    }

    public void setUse_Trgt(String use_Trgt) {
        Use_Trgt = use_Trgt;
    }

    public String getContents() {
        return Contents;
    }

    public String getFac_Code() {
        return Fac_Code;
    }

    public String getIs_Free() {
        return Is_Free;
    }

    public String getOrg_Code() {
        return Org_Code;
    }

    public String getPlayer() {
        return Player;
    }

    public String getProgram() {
        return Program;
    }

    public String getThemeCode() {
        return ThemeCode;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setIs_Free(String is_Free) {
        Is_Free = is_Free;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public void setFac_Code(String fac_Code) {
        Fac_Code = fac_Code;
    }

    public void setOrg_Code(String org_Code) {
        Org_Code = org_Code;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public void setProgram(String program) {
        Program = program;
    }

    public void setThemeCode(String themeCode) {
        ThemeCode = themeCode;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }

    public void setCultCode(String cultCode) {
        CultCode = cultCode;
    }

    public String getCultCode() {
        return CultCode;
    }
    public void setCodeName(String codeName) {
        CodeName = codeName;
    }

    public String getCodeName() {
        return CodeName;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public void setPlace(String place){
        Place = place;
    }

    public String getPlace() {
        return Place;
    }

    public void setGCode(String GCode) {
        this.GCode = GCode;
    }

    public String getGCode() {
        return GCode;
    }

    public String getMain_Img(){
        return Main_Img;
    }

    public void setMain_Img(String img){
        Main_Img = img;
    }

}
