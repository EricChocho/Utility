package com.viewsonic.utility;

import android.content.ContentResolver;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static long getBootTimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    public static String getCurrentTimeforFolder() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHH", Locale.getDefault());
        String formattedCurrentTime = sdf.format(new Date(System.currentTimeMillis()));
        return formattedCurrentTime;
    }

    public static String getCurrentTimeforFilename() {

        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        String formattedCurrentTime = sdf.format(new Date(System.currentTimeMillis()));
        return formattedCurrentTime;
    }

    public static String getCurrentTimeMillisFormatted() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedCurrentTime = sdf.format(new Date(System.currentTimeMillis()));
        return formattedCurrentTime;
    }


    public static String getFormattedBootTime() {
        long bootTimeMillis = getBootTimeMillis();
        long bootTimeSeconds = bootTimeMillis / 1000;

        long days = bootTimeSeconds / (60 * 60 * 24);
        long hours = (bootTimeSeconds % (60 * 60 * 24)) / (60 * 60);
        long minutes = (bootTimeSeconds % (60 * 60)) / 60;
        long seconds = bootTimeSeconds % 60;

        return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    }

    public static String getFormattedBootTime2() {
        long bootTimeMillis = getBootTimeMillis();
        long currentTimeMillis = getCurrentTimeMillis();
        long bootTime = currentTimeMillis - bootTimeMillis;

        long bootTimeSeconds = bootTime / 1000;

        long days = bootTimeSeconds / (60 * 60 * 24);
        long hours = (bootTimeSeconds % (60 * 60 * 24)) / (60 * 60);
        long minutes = (bootTimeSeconds % (60 * 60)) / 60;
        long seconds = bootTimeSeconds % 60;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedBootTime = sdf.format(new Date(bootTime));
        return formattedBootTime;

        //return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    }


    public static boolean isAutoDateTimeEnabled(ContentResolver contentResolver) {
        int autoDateTimeEnabled;

        // Android 17 (Build.VERSION_CODES.JELLY_BEAN_MR1) 之後的版本使用 Settings.Global
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            autoDateTimeEnabled = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0);
        } else {
            // 在 Android 17 之前的版本使用 Settings.   System
            autoDateTimeEnabled = Settings.System.getInt(contentResolver, Settings.System.AUTO_TIME, 0);
        }

        return autoDateTimeEnabled == 1;
    }

    public static String getCurrentTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();
    }

    public static String getUTCOffset() {
        TimeZone timeZone = TimeZone.getDefault();
        int offsetInMillis = timeZone.getRawOffset();

        // 計算 UTC 偏移值的時鐘部分和分鐘部分
        int hours = offsetInMillis / (60 * 60 * 1000);
        int minutes = Math.abs((offsetInMillis / (60 * 1000)) % 60);

        // 根據正負號來格式化偏移值
        String sign = (offsetInMillis >= 0) ? "+" : "-";
        String formattedOffset = String.format(Locale.getDefault(), "UTC %s%02d:%02d", sign, hours, minutes);

        return formattedOffset;
    }


}
