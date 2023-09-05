package com.viewsonic.utility;

import android.content.Context;

import java.util.Locale;

public class LocaleUtil {

    private Context context;

    public LocaleUtil(Context context) {
        this.context = context;
    }

    public  String getCurrentLocale() {
        Locale currentLocale = context.getResources().getConfiguration().locale;
        String currentLocaleStr = currentLocale.toString();
        // 使用地区信息

        return  currentLocaleStr;
    }


    public static String getCurrentlanguage()
    {
        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();
        return currentLanguage;
    }

}
