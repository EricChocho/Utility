package com.viewsonic.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public static Boolean StartUpdate=false;
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

    private Runnable runnablePoewr = new Runnable() {
        @Override
        public void run() {
            // 在这里执行定时任务，例如打印 Log

            functionFinish=false;

            Thread logPowerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    functionFinish= LogPower();
                 //   functionFinish= LogSystem();
                }
            });
            logPowerThread.start(); // 启动线程


        //    Log.d("Eric", "Eric 2023.08.02 Logging every 60 seconds:  !!!!"+powerCount+functionFinish);


            if(NeedUpdate && !StartUpdate)
            {
                StartUpdate=true;
                Thread UpdateThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("Eric","Eric 2023.09/04 call Update");
                        UpDate();
                    }
                });
                UpdateThread.start(); // 启动线程
            }

            handler.postDelayed(this, DELAY1M);

        }
    };

    private Runnable runnableSystem = new Runnable() {
        @Override
        public void run() {
            // 在这里执行定时任务，例如打印 Log

            functionFinish=false;

            Thread logSystemThread = new Thread(new Runnable() {
                @Override
                public void run() {
                     functionFinish= LogSystem();
                }
            });
            logSystemThread.start(); // 启动线程


         //   Log.d("Eric", "Eric 2023.08.02 Logging every 60 seconds:  !!!!"+powerCount+functionFinish);

            if(NeedUpdate && !StartUpdate)
            {
                StartUpdate=true;
                Thread UpdateThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("Eric","Eric 2023.09/04 call Update");
                        UpDate();
                    }
                });
                UpdateThread.start(); // 启动线程

            }
            handler.postDelayed(this, DELAY5M);

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


    //    Log.d("VsUtilService", "!!!! VsUtilService Record every 60 seconds:  !!!!:"+powerCount);
        if(powerCount%2==1)
        {

            try {
                FileUtil.writeLogToFile("PowerA_"+GroupID+".log",powerCount+":"+TimeUtil.getCurrentTimeMillisForPowerLog() ,false);
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
                FileUtil.writeLogToFile("PowerB_"+GroupID+".log",powerCount+":"+TimeUtil.getCurrentTimeMillisForPowerLog() ,false);
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



    public boolean LogSystem()
    {
        if(KeyPower!=true && KeyUpdate!=true) {
            KeyLog = true;
        }
        else
        {
            return  false;
        }

        try {

            LocaleUtil l =new LocaleUtil(VsUtilService.this);

            String RecordCurrentTime=TimeUtil.getCurrentTimeforFilename();
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","1:"+RecordCurrentTime ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","2:" ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","3:"+NetUtil.getWiFiMacAddress() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","4:"+ Build.MODEL ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","5:"+NetUtil.getLocalIPAddress() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","6:"+TimeUtil.getBootTimeString() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","7:"+l.getCurrentLocale() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","8:"+TimeUtil.getCurrentTimeZone() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","9:"+LocaleUtil.getCurrentlanguage() ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","10:"+"Android:44" ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","11:"+Build.VERSION.RELEASE ,true);
            FileUtil.writeLogToFile(GroupID+"_"+logCount+"_"+RecordCurrentTime+".log","12:xxx"+"Android:44" ,true);

            logCount++;
            KeyLog=false;
           // return true;

        } catch (IOException e) {
            //    throw new RuntimeException(e);
        }
        catch (Exception e) {
            //    throw new RuntimeException(e);
        }

        return true;
    }




    public  static void UpDate()
    {


      //  Log.i("Eric","2023.09.04 Enter Update");

      // Log.i("Eric","Eric 2023.09.04 Need Update");
      //  Log.i("Eric","Eric 2023.09.04 Need Update"+NeedUpdate);

      //  Log.i("Eric","Eric 2023.09.04 Need Update Current"+CurrentWorkFolder);
      //  Log.i("Eric","Eric 2023.09.04 Need Update L"+LastWorkFolder);





        try {

            //////ZIP
            String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/"+LastWorkFolder;
           String zipFilePath = "/storage/emulated/0/Android/data/com.viewsonic.utility/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())+"_"+LastWorkFolder+".zip";
            String password = "$viewsonic0722";
               ZipUtility.zipAndEncryptFolder(sourceFolderPath, zipFilePath, password);
           // ZipUtility.zipFolder(sourceFolderPath, zipFilePath);


            ///// FTP

            ///Need Check Server Folder



            //NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())

            //////

            File file=new File(zipFilePath);
            Log.i("Eric","2023 file exist"+file.exists());
            if (file == null)
            {
                Log.i("Eric","2023 file exist"+file.exists());

            }
            else{

                //Log.i("Eric","2023.0723 EE A!"+file.getPath());
                InputStream inputStream=new FileInputStream(file);

                Log.i("Eric","2023   Updated filename"+file.getName());
                //  if (!export(inputStream, "209111461311212110_23083113.zip", "/NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())", "172.21.8.244",
                //          "viewsonic","$viewsonic0722")) {

            //   Log.i("Eric","Eric 2023.09.04 folder"+"/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()));
                if (!FTPUtil.export(inputStream, file.getName(), "/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()), FTPUtil.address,
                      FTPUtil.username,FTPUtil.pw)) {


                    //                      Toast.makeText(this, "An error occurred while exporting " + file.getName(), Toast.LENGTH_SHORT).show();
                    //file.clear();
                    Log.i("Eric","Eric 2023.0904 FTP　error ");

                }
                else
                {

                    Log.i("Eric","Eric 2023.0904 FTP　OK ");

                }

                if (!FTPUtil.export(inputStream, file.getName(), "/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()), FTPUtil.address2,
                        FTPUtil.username,FTPUtil.pw2)) {


                    //                      Toast.makeText(this, "An error occurred while exporting " + file.getName(), Toast.LENGTH_SHORT).show();
                    //file.clear();
                    Log.i("Eric","Eric 2023.0904 FTP　error ");

                }
                else
                {

                    Log.i("Eric","Eric 2023.0904 FTP　OK ");

                }



            }
            Log.i("Eric","2023.0723 B"+file.getPath());


        } catch (Exception e) {
            e.printStackTrace();
        }





        //////
        NeedUpdate=false;
        StartUpdate=false;

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

NeedUpdate=false;
StartUpdate=false;

        handler.post(runnablePoewr);
      //  handler.post(runnableSystem);
        handler.postDelayed(runnableSystem,DELAY);
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
        handler.removeCallbacks(runnableSystem);
        handler.removeCallbacks(runnablePoewr);
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