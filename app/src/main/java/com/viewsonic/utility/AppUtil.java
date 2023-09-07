package com.viewsonic.utility;



import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

public class AppUtil {


    public static List<ApplicationInfo> getInstalledApks(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApks1 = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        Log.i("Eric","Eric :"+installedApks1.size());

        List<ApplicationInfo> installedApks = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        //  List<ApplicationInfo> installedApks = packageManager.getInstalledApplications();
        Log.i("Eric","Eric : "+installedApks.size());

        //  List<PackageManager.PackageInfoFlags> installedPackages = packageManager.getInstalledPackages()

        return installedApks;
    }
    static String launchClassName = "";
    public static   String getLaunchClass(Context cc,String Packagename) {

        try {
            PackageManager packageManager = cc.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(Packagename);

            if (intent != null) {
                launchClassName = intent.getComponent().getClassName();
                // 现在你有了启动类名，可以在代码中使用它
            } else {
                launchClassName = "";

            }

            return launchClassName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long getApkInstallTime(Context context,String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.firstInstallTime; // 安装时间
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getApkLastUpdateTime(Context context,String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.lastUpdateTime; // 安装时间
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getApkVersionName(Context context,String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }



    public static CharSequence getApplicationLabel(Context context,ApplicationInfo info) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getApplicationLabel(info);
    }

}
