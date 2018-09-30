package uis.DataBase;

import android.content.Context;
import android.content.SharedPreferences;

public class mShared  {

    private final static String SHARED_KEY = "FESTIVAL_INIT";
    private final static String SHARED_NAME = "FESTIVAL";

    public static boolean isFirstIn(Context mCon){
        SharedPreferences sharedPreferences = getShared(mCon);
        return sharedPreferences.getBoolean(SHARED_KEY,true);
    }

    public static void setFirstIn(Context mCon){
        SharedPreferences.Editor editor = getShared(mCon).edit();
        editor.putBoolean(SHARED_KEY,false);
        editor.apply();

    }

    private static SharedPreferences getShared(Context mCon){
        return mCon.getSharedPreferences(SHARED_NAME , Context.MODE_PRIVATE);

    }

}
