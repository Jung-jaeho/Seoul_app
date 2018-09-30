package uis.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
 * 즐겨찾기에 저장되는부분을 관리하는 내부 DataBase
 * 바뀌는 현재정보는 Parse를 이용하고 즐겨찾기에 저장된 정보만 내부데이터베이스에 저장한다.
 * @author : 조재영
 */
public class Model extends SQLiteOpenHelper{

    private static Model instance;

    public static Model getInstance(Context context){
        if(instance==null){
            instance = new Model(context.getApplicationContext(),"favorites",null,1);
        }
        return instance;
    }

    private Model(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="Create table if not exists favorites(cultcode Text not null primary key,codename text,title text,place text,main_Img text,time text,agelimit text, subjcode text,end_date text,start_date text,org_link text,inquiry text,is_free text, use_trgt text,gcode text)";
        db.execSQL(sql);
    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insert(FestivalInformationVO data){
        String sql = "insert into favorites values('"+data.getCultCode()+"','"+data.getCodeName()+"','"+data.getTitle()+"','"
                +data.getPlace()+"','"+data.getMain_Img()+"','"+data.getTime()+"','"
                +data.getAgeLimit()+"','"+data.getSubjCode()+"','"+data.getEnd_Date()+"','"
                +data.getStart_Date()+"','"+data.getOrg_Link()+"','"+data.getInquiry()+"','"
                +data.getIs_Free()+"','"+data.getUse_Trgt()+"','"+data.getGCode()+"')";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }


    public ArrayList<FestivalInformationVO> select(){
        ArrayList<FestivalInformationVO> data = new ArrayList<>();

        String sql = "select * from favorites";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(sql,null);
        while(cur.moveToNext()){
            FestivalInformationVO tmp = new FestivalInformationVO();
            int cnt=0;
            tmp.setCultCode(cur.getString(cnt++));
            tmp.setCodeName(cur.getString(cnt++));
            tmp.setTitle(cur.getString(cnt++));
            tmp.setPlace(cur.getString(cnt++));
            tmp.setMain_Img(cur.getString(cnt++));
            tmp.setTime(cur.getString(cnt++));
            tmp.setAgeLimit(cur.getString(cnt++));
            tmp.setSubjCode(cur.getString(cnt++));
            tmp.setEnd_Date(cur.getString(cnt++));
            tmp.setStart_Date(cur.getString(cnt++));
            tmp.setOrg_Link(cur.getString(cnt++));
            tmp.setInquiry(cur.getString(cnt++));
            tmp.setIs_Free(cur.getString(cnt++));
            tmp.setUse_Trgt(cur.getString(cnt++));
            tmp.setGCode(cur.getString(cnt++));
            data.add(tmp);
        }
        db.close();
        return data;
    }

    public void delete(String cultcode){
        String sql = "delete from favorites where cultcode = ?";
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL(sql,new String[] {cultcode});
        db.close();
    }



}
