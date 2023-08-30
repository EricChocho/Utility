package com.viewsonic.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VsUtilService extends Service {


    private long serviceStartTime;
    public static final String CHANNEL_ID_STRING = "service_01";
    private Notification notification;


    private final long DELAY = 10000; // 10 秒
    private final long DELAYM = 1*60*1000; // 5 mins

    private final long DELAY1M = 1*60*1000; // 5 mins
    private final long DELAY5M = 5*60*1000; // 5 mins
    private final long DELAY60M =60*60* 1000; // 一小時


    public static String GroupID="";
    public static String LastWorkFolder="";
    public static String CurrentWorkFolder="";

    public static Boolean NeedUpdate=false;
    public static Boolean KeyPower=false;
    public static Boolean KeyLog=false;

    public static Boolean KeyUpdate=false;


    public static  int powerCount=1;
    public static  int logCount=1;



    public VsUtilService() {
    }

   // public static int VsUtiltyCount=0;

    private NotificationManager getNotificationManager() {
        return (NotificationManager) VsUtilService.this.getSystemService(Context.NOTIFICATION_SERVICE);
    }




    public static  void setGroupID()
    {
        GroupID=Long.toString(System.currentTimeMillis()- SystemClock.uptimeMillis());

    }

    @Override
    public void onCreate() {
        serviceStartTime=System.currentTimeMillis();
        setGroupID();



        Log.i("VsUtilService", "Eric 2023.08.02 Service started onCreate .");

        Log.i("VsUtilService", "Eric 2023.08.02 Service started onCreate ."+serviceStartTime);

        Date date = new Date(serviceStartTime);

// 创建 SimpleDateFormat 实例以定义日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

// 格式化 Date 对象为所需的字符串格式
        String formattedTime = dateFormat.format(date);
        Log.i("VsUtilService", "Eric 2023.08.29 Service started onCreate ."+formattedTime);

        super.onCreate();
        // myapp = new MyAPP();

        NotificationManager notificationManager = (NotificationManager) VsUtilService.this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).setSmallIcon(R.drawable.ic_stat_name).build();
            startForeground(1, notification);
        }
    }

    private Handler handler = new Handler();
    public static boolean functionFinish=false;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 在这里执行定时任务，例如打印 Log

            functionFinish=false;

            Thread logPowerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    functionFinish= LogPower();
                }
            });
            logPowerThread.start(); // 启动线程


            Log.d("Eric", "Eric 2023.08.02 Logging every 60 seconds:  !!!!"+powerCount+functionFinish);
            handler.postDelayed(this, DELAY1M);

        }
    };


    private boolean LogPower()
    {
        if(KeyLog!=true && KeyUpdate!=true) {
            KeyPower = true;
        }
        else
        {
            return  false;
        }




        Log.d("VsUtilService", "!!!! VsUtilService Record every 60 seconds:  !!!!:"+powerCount);
        if(powerCount%2==1)
        {

            try {
                FileUtil.writeLogToFile("PowerA_"+GroupID,powerCount+":"+TimeUtil.getCurrentTimeMillisForPowerLog() ,false);
            } catch (IOException e) {
            //    throw new RuntimeException(e);
            }
            catch (Exception e) {
                //    throw new RuntimeException(e);
            }

        }
        else
        {
            try {
                FileUtil.writeLogToFile("PowerB_"+GroupID,powerCount+":"+TimeUtil.getCurrentTimeMillisForPowerLog() ,false);
            } catch (IOException e) {
                //    throw new RuntimeException(e);
            }
            catch (Exception e) {
                //    throw new RuntimeException(e);
            }


        }
          powerCount++;


        KeyPower=false;

        return true;


    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("VsUtilService", "!!!!! VsUtilService Sstarted onStartCommand ");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            startForeground(1, notification);
        }

        // 在这里执行您的逻辑
        Log.i("VsUtilService", "Eric 2023.08.02 Service started on boot.");


        powerCount=1;
        logCount=1;
        GroupID=Long.toString(TimeUtil.getBootTimeMillis());



        handler.post(runnable);
        return START_STICKY; // 可以根据需要返回不同的值
    }



    public String getStartTime()
    {
        Date date = new Date(serviceStartTime);

// 创建 SimpleDateFormat 实例以定义日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

// 格式化 Date 对象为所需的字符串格式
        String formattedTime = dateFormat.format(date);

        return  formattedTime;
    }






    @Override
    public void onDestroy() {
        Log.i("VsUtilService", "!!!!! VsUtilService Service onDestroy");
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        VsUtilService getService() {
            return VsUtilService.this;
        }
    }

    private final IBinder binder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return binder;
        // return  null;
    }






}