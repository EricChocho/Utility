package com.viewsonic.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class Bootcomplited extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        //Logger.setShowlog(true);
      //  Logger.setTag("InBootReciver");

       Log.i("VsUtilService","Eric 2023.08.29  Receive Boot Complieted");
               //Logger.show("2023.08.02 In Complieter");
        Intent serviceLauncher = new Intent(context, VsUtilService.class);
        //    Log.i("Eric","BootFinish44");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceLauncher);
        }
        else
            context.startService(intent);

        Log.i("VsUtilService","Eric 2023.08.29  Receive Boot Complieted Finished");

    }
}