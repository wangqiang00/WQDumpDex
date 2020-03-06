package com.jianbing.www.wqdumpdex;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class DumpDexModule implements IXposedHookLoadPackage {


    public static Class cls_class;
    public static Method mid_getDex;
    public static Method mid_getBytes;
    public static Class cls_dex;
    public static String applicationName,activityName;
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        applicationName = SharedPrefUtils.getXvalue("applicationName");
        activityName = SharedPrefUtils.getXvalue("activityName");

//        Log.e("wq","!!loadPackname = " + loadPackageParam.packageName);
//        Log.e("wq","targetPackname = " + applicationName);
            if (applicationName!=null && loadPackageParam.packageName.equals(applicationName)){
                Log.e("wq","目标应用启动");
                reflect_init();
                XposedHelpers.findAndHookMethod("java.lang.ClassLoader", loadPackageParam.classLoader, "loadClass", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Class cls_activity = (Class) param.getResult();
                        if(cls_activity == null) return ;
//                       Log.e("wq","activityname = " + cls_activity.getName());
                        if(cls_activity.getName().equals(activityName)){
                            Log.e("wq","activity load!!!" );
                            Object obj_dex = mid_getDex.invoke(cls_activity);
                            byte[] dex_data = (byte[]) mid_getBytes.invoke(obj_dex);
                            Log.e("wq","dex data get success!!!!" + dex_data.length);
                            String dex_path = "/data/data/"+applicationName + "/"+dex_data.length+".dex";
                            Log.e("wq","filepath = " + dex_path);
                            File file = new File(dex_path);
                            if(file.exists()){
                                return;
                            }
                            writeByte(dex_data,file.getAbsolutePath());

                        }
                    }
                });
            }
        }


    public static void reflect_init(){
        Log.e("wq","start reflect");
        try {
            cls_class = Class.forName("java.lang.Class");
            cls_dex = Class.forName("com.android.dex.Dex");
            mid_getDex = cls_class.getDeclaredMethod("getDex");
            mid_getDex.setAccessible(true);
            mid_getBytes = cls_dex.getDeclaredMethod("getBytes");
            mid_getBytes.setAccessible(true);
        } catch (ClassNotFoundException e) {
            Log.e("wq","class not found" +e.getMessage() );
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.e("wq","no such method" +e.getMessage() );
            e.printStackTrace();
        }

    }

    public void writeByte(byte[] data,String filePath){
        try {
            OutputStream os = new FileOutputStream(filePath);
            os.write(data);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("wq","写文件失败");
        }
    }
}
