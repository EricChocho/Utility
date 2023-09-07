package com.viewsonic.utility;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Comparator;

public class PackageNameComparator implements Comparator<ApplicationInfo> {
    private PackageManager packageManager;

    public PackageNameComparator(PackageManager pm) {
        packageManager = pm;
    }

    @Override
    public int compare(ApplicationInfo app1, ApplicationInfo app2) {
        String packageName1 = app1.packageName;
        String packageName2 = app2.packageName;

        return packageName1.compareTo(packageName2);
    }

}
