package com.jianbing.www.wqdumpdex;

import android.content.Context;
import android.content.SharedPreferences;

import de.robv.android.xposed.XSharedPreferences;

public class SharedPrefUtils {

    private Context shareContext;
    private SharedPreferences sp;
    private static XSharedPreferences xsp;

    public SharedPrefUtils(Context context){
        shareContext = context;
        sp = shareContext.getSharedPreferences("app_config",shareContext.MODE_WORLD_READABLE);
    }
    public void setSharePref(String key,String value){
        sp.edit().putString(key,value).commit();
    }
    public static XSharedPreferences getXSharedPref(){
        if(xsp!=null){
            xsp.reload();
            return xsp;
        }

        xsp = new XSharedPreferences("com.jianbing.www.wqdumpdex","app_config");
        return xsp;
    }

    public static String getXvalue(String key){
        String value = "";
        value = getXSharedPref().getString(key,null);
        return value;
    }
}
