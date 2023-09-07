package com.viewsonic.utility;

import static com.viewsonic.utility.TimeUtil.getCurrentHourInMillis;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VsUtilService extends Service {


    private long serviceStartTime;
    public static final String CHANNEL_ID_STRING = "service_01";
    private Notification notification;
    static Context context;

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
    public static  int logAppUsingCount=1;
    public static  int logAppCount=1;


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

        context = this;

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
                    Log.i("Eric","Enter runnableSystem");
                    Log.i("Eric","Eric Enter A");
                     functionFinish= LogSystem();
                    Log.i("Eric","Eric Enter B");
                    functionFinish= LogAppUsing();
                    Log.i("Eric","Eric Enter C");
                    functionFinish=LogApp();
                    Log.i("Eric","Eric Enter D");

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


    public  boolean LogAppUsing()
    {     Log.i("Eric","Eric Enter LogAppUsing()");
        try {
            if (AppUsingUtil.hasUsageStatsPermission(this)) {
                if (KeyPower != true && KeyUpdate != true) {
                    KeyLog = true;
                } else {
                    return false;
                }

                try {

                    // LocaleUtil l = new LocaleUtil(VsUtilService.this);

                    String RecordCurrentTime = TimeUtil.getCurrentTimeforFilename();

                    UsageStatsManager usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);


                    Long CheckStartTime = System.currentTimeMillis() - SystemClock.uptimeMillis();
                    Long CheckEndTime = System.currentTimeMillis();
                    Long CheckStartthishour = getCurrentHourInMillis();


                    List<UsageStats> statsList2 = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, CheckStartTime, CheckEndTime);
                    List<UsageStats> statsList3 = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, CheckStartthishour, CheckEndTime);


                    for (int i = 0; i < statsList2.size(); i++) {

                        Long D1;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {


                            D1 = statsList2.get(i).getTotalTimeVisible();

                        } else {

                            D1 = statsList2.get(i).getTotalTimeInForeground();
                        }
                        Log.i("Eric", "2023.09.06 !!!: i=" + i + ":" + statsList2.get(i).getPackageName() + ":" + Long.toString(D1) + ":" + Long.toString(CheckStartTime) + ":" + Long.toString(CheckEndTime));
                        FileUtil.writeLogToFile(GroupID + "_" + logAppUsingCount + "_AppUsing_" + RecordCurrentTime + ".log", i + 1 + ":" + statsList2.get(i).getPackageName() + ":" + Long.toString(D1) + ":" + TimeUtil.convertMillisToDateTime(CheckStartTime) + ":" + TimeUtil.convertMillisToDateTime(CheckEndTime), true);

                    }
                    for (int i = 0; i < statsList3.size(); i++) {

                        Long D1;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                            D1 = statsList3.get(i).getTotalTimeVisible();

                        } else {
                            D1 = statsList3.get(i).getTotalTimeInForeground();
                        }
                        Log.i("Eric", "2023.09.06 !!!: i=" + i + ":" + statsList3.get(i).getPackageName() + ":" + Long.toString(D1) + ":" + Long.toString(CheckStartthishour) + ":" + Long.toString(CheckEndTime));
                        FileUtil.writeLogToFile(GroupID + "_" + logAppUsingCount + "_AppUsing_" + RecordCurrentTime + ".log", i + 1 + ":" + statsList3.get(i).getPackageName() + ":" + Long.toString(D1) + ":" + TimeUtil.convertMillisToDateTime(CheckStartthishour) + ":" + TimeUtil.convertMillisToDateTime(CheckEndTime), true);

                    }


                    logAppUsingCount++;
                    KeyLog = false;
                    // return true;

                } catch (IOException e) {
                    //    throw new RuntimeException(e);
                } catch (Exception e) {
                    //    throw new RuntimeException(e);
                    Log.i("Eric","Eric Enter error 1"+e.toString());
                }

                return true;
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
            Log.i("Eric","Eric Enter error 2"+e.toString());
            return false;
        }
    }


    public  boolean LogApp()
    {
        Log.i("Eric","Eric Enter  LogApp()");

            if (KeyPower != true && KeyUpdate != true) {
                Log.i("Eric","Eric Enter  LogApp() A");
                KeyLog = true;
            } else {
                Log.i("Eric","Eric Enter  LogApp() B" );
                return false;
            }
//
            try {

                List<ApplicationInfo> installedApks = AppUtil.getInstalledApks(this);
                int system=1,update=1,install=1;

                Collections.sort(installedApks, new PackageNameComparator(this.getPackageManager()));

                String RecordCurrentTime=TimeUtil.getCurrentTimeforFilename();
               // for (ApplicationInfo apkInfo : installedApks) {
                for (int i=0;i< installedApks.size();i++) {
                    // 可以在这里处理 APK 信息，例如获取包名或应用名
                    String packageName = installedApks.get(i).packageName;


                    CharSequence applicationName = AppUtil.getApplicationLabel(this,installedApks.get(i));

                    String startClass=AppUtil.getLaunchClass(this,packageName);

                    long Installtime= AppUtil.getApkInstallTime(   this, packageName);
                    long LastUpdatetime= AppUtil.getApkLastUpdateTime(this, packageName);
                    String APKVersion= AppUtil.getApkVersionName(this, packageName);

                    Log.i("APK List", "Eric  Time "+Long.toString(Installtime)+"::::"+TimeUtil.convertMillisToDateTime(Installtime)+"  Version: " + APKVersion);
                    Log.i("APK List", "Eric  LUTime "+Long.toString(LastUpdatetime)+"::::"+TimeUtil.convertMillisToDateTime(LastUpdatetime)+"  Version: " + APKVersion);


                    if ((installedApks.get(i).flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        // 应用程序是系统应用程序
                        Log.i("APK List", "Eric  !!! System "+system+" : Package Name: " + packageName + ", Application Name: " + applicationName+" Start:"+startClass);
                        FileUtil.writeLogToFile(GroupID + "_" + logAppCount + "_AppList_" + RecordCurrentTime + ".log", i+1 + ":system:" +packageName+":"+applicationName+":"+startClass+":"+APKVersion+":"+TimeUtil.convertMillisToDateTime(Installtime)+":"+TimeUtil.convertMillisToDateTime(LastUpdatetime), true);

                        system+=1;
                    }
                    else if ((installedApks.get(i).flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        // 应用程序是一个已更新的系统应用程序
                        Log.i("APK List", "Eric  !!! System updated "+update+" : Package Name: " + packageName + ", Application Name: " + applicationName+" Start:"+startClass);
                        FileUtil.writeLogToFile(GroupID + "_" + logAppCount + "_AppList_" + RecordCurrentTime + ".log", i+1 + ":update:" +packageName+":"+applicationName+":"+startClass+":"+APKVersion+":"+TimeUtil.convertMillisToDateTime(Installtime)+":"+TimeUtil.convertMillisToDateTime(LastUpdatetime), true);
                        update++;
                    }
                    else{
                        Log.i("APK List", "Eric  !!! Installed "+install+" : Package Name: " + packageName + ", Application Name: " + applicationName+" Start:"+startClass);
                        FileUtil.writeLogToFile(GroupID + "_" + logAppCount + "_AppList_" + RecordCurrentTime + ".log", i+1 + ":installed:" +packageName+":"+applicationName+":"+startClass+":"+APKVersion+":"+TimeUtil.convertMillisToDateTime(Installtime)+":"+TimeUtil.convertMillisToDateTime(LastUpdatetime), true);

                        install++;
                    }
                }







                logAppCount++;
                KeyLog = false;
                // return true;

           } catch (IOException e)

    {
        //    throw new RuntimeException(e);
    }
            //catch (Exception e) {

         //       Log.i("Eric","Eric Enter  Error e:"+e.toString());
                //    throw new RuntimeException(e);
         //   }

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

            Calendar now = Calendar.getInstance();
            LastWorkFolder = FileUtil.FindLastWorkFolder(now);


            if (LastWorkFolder != "") {
                //////ZIP
                String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/" + LastWorkFolder;
                String zipFilePath = "/storage/emulated/0/Android/data/com.viewsonic.utility/" + NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()) + "_" + LastWorkFolder + ".zip";
                String password = "$viewsonic0722";
                ZipUtility.zipAndEncryptFolder(sourceFolderPath, zipFilePath, password);
                // ZipUtility.zipFolder(sourceFolderPath, zipFilePath);


                ///// FTP

                ///Need Check Server Folder


                //NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())

                //////

                File file = new File(zipFilePath);

                if (file == null) {
                    Log.i("VsUtilService", "VsUtilService ZIP Error:" + file.getPath());

                } else {

                    //Log.i("Eric","2023.0723 EE A!"+file.getPath());
                    InputStream inputStream = new FileInputStream(file);

               //     Log.i("Eric", "2023   Updated filename" + file.getName());
                    //  if (!export(inputStream, "209111461311212110_23083113.zip", "/NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())", "172.21.8.244",
                    //          "viewsonic","$viewsonic0722")) {

                    //   Log.i("Eric","Eric 2023.09.04 folder"+"/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()));
                    if (!FTPUtil.export(inputStream, file.getName(), "/" + NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()), FTPUtil.address,
                            FTPUtil.username, FTPUtil.pw)) {


                        //                      Toast.makeText(this, "An error occurred while exporting " + file.getName(), Toast.LENGTH_SHORT).show();
                        //file.clear();
                        Log.i("VsUtilService", "VsUtilService!!!!LOCAL FTP error ");

                    } else {

                        Log.i("VsUtilService", "VsUtilService!!!! LOCAL  FTP　 OK ");

                    }

                    if (!FTPUtil.export(inputStream, file.getName(), "/" + NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress()), FTPUtil.address2,
                            FTPUtil.username, FTPUtil.pw2)) {


                        //                      Toast.makeText(this, "An error occurred while exporting " + file.getName(), Toast.LENGTH_SHORT).show();
                        //file.clear();
                        Log.i("VsUtilService", "VsUtilService!!!! AWＳ　FTP　error ");

                    } else {

                        Log.i("VsUtilService", "VsUtilService!!!! AWS FTP　OK ");

                    }


                }
            //    Log.i("Eric", "2023.0723 B" + file.getPath());

            }
            else
            {
                Log.i("VsUtilService","VsUtilService!!!! No LogFolder in last 24 hours Update Next Time");

            }
            } catch(Exception e){
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
        logAppUsingCount=1;
        logAppCount=1;
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