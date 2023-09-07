package com.viewsonic.utility;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.viewsonic.utility.MainActivity;

import java.util.List;

public class AppUsingUtil {


    public static boolean hasUsageStatsPermission(Context cc) {


        Log.i("Eric","Eric Enter hasUsageStatsPermission");

        try {

            AppOpsManager appOps = (AppOpsManager) cc.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), cc.getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED;
        }
        catch (Exception e)
        {
            Log.i("Eric","Eric Enter hasUsageStatsPermission error "+e.toString());
            return  false;
        }
    }
  public static final int REQUEST_USAGE_ACCESS = 1001;
    public static void  getStatsPermission() {

    Log.i("eric"," NO");
    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    int []result={0};
    MainActivity.act.startActivityForResult(intent,REQUEST_USAGE_ACCESS );

      //  Log.i("eric"," Eric !!!"+ Integer.toString(result));

}


    private List<UsageStats> getUsageStats() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) MainActivity.act.getSystemService(Context.USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long startTime = endTime - (24 * 60 * 60 * 1000); // 獲取最近一月的使用統計資訊
        List<UsageStats> statsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        return statsList;
    }
}
