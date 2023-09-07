package com.viewsonic.utility;



import static com.viewsonic.utility.TimeUtil.getCurrentHourInMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.lingala.zip4j.core.ZipFile;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button1,button2,button3;
    TextView tv1;
    int CurrentDeviceW,CurrentDeviceH;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int DEFAULT_FTP_PORT = 21;

    static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act=MainActivity.this;

        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ) {
            // 如果沒有權限，就向使用者要求權限
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
        }
        else if(!AppUsingUtil.hasUsageStatsPermission(MainActivity.this)) {
            AppUsingUtil.getStatsPermission();
        }
        if (AppUsingUtil.hasUsageStatsPermission(MainActivity.this))
        {
            executeAppLogic();
        }

        else {
            // 已經有權限，執行程式邏輯


            this.finish();

        }






    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            boolean allPermissionsGranted = true;

            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                // 使用者授權了權限，執行程式邏輯
                executeAppLogic();
            }
            else {
                // 使用者拒絕了權限，這裡你可以顯示一個訊息給使用者，然後退出應用程式
                Toast.makeText(this, "需要讀取和寫入外部儲存權限來執行應用程式", Toast.LENGTH_SHORT).show();
                finish(); // 退出應用程式
            }
        }/*
        else if (requestCode == AppUsingUtil.REQUEST_USAGE_ACCESS) {

            Log.i("Eric","Eric A");
                // 用户返回了使用访问设置页面
                if (AppUsingUtil.hasUsageStatsPermission()) {
                    // 用户已授予使用访问权限
                    // 在这里处理相应的逻辑
                } else {
                    Log.i("Eric","Eric B");
                    // 用户未授予使用访问权限
                    // 在这里处理未授予权限的情况
                }
        }*/
    }

    private void executeAppLogic() {
        button1=(Button) findViewById(R.id.button);
        button2=(Button) findViewById(R.id.button2);
        button3=(Button) findViewById(R.id.button3);

        tv1=(TextView)findViewById(R.id.textview1) ;

        GetDevice();

        Log.i("Eric","IP!!!"+NetUtil.getLocalIPAddress());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String s2="IP:"+NetUtil.getLocalIPAddress()+"\n MAc:"+NetUtil.getWiFiMacAddress();
                tv1.setText(s2);

               */

                try {

                    long bootTimeMillis = System.currentTimeMillis() - SystemClock.uptimeMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());

// 将时间戳转换为指定格式的日期时间字符串
                    String formattedDate = sdf.format(new Date(bootTimeMillis));
                   VsUtilService.setGroupID();

                    Log.i("Eric","Eric 0830:"+"PowerA_G"+ VsUtilService.GroupID+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());


                    Log.i("Eric","Eric 0830:"+"PowerA_"+ formattedDate+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());

                    Log.i("Eric","Eric 0830:"+"PowerA_"+ System.currentTimeMillis()+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());
                    Log.i("Eric","Eric 0830:"+"PowerA_"+ SystemClock.uptimeMillis() +"   :E"+TimeUtil.getCurrentTimeMillisFormatted());
                    Log.i("Eric","Eric 0830:"+"PowerA_"+ SystemClock.elapsedRealtime() +"   :E"+TimeUtil.getCurrentTimeMillisFormatted());

                    Log.i("Eric","Eric 0830:"+"PowerA_"+TimeUtil.getBootTimeMillis()+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());
                    Log.i("Eric","Eric 0830:"+"PowerA_"+TimeUtil.getFormattedBootTime()+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());

                    Log.i("Eric","Eric 0830:"+"PowerA_"+TimeUtil.getFormattedBootTime2()+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());
                    Log.i("Eric","Eric 0830:"+"PowerA_G"+ VsUtilService.GroupID+"   :E"+TimeUtil.getCurrentTimeMillisFormatted());

                    //  FileUtil.writeLogToFile("PowerA_"+TimeUtil.getBootTimeMillis(),"1_"+TimeUtil.getCurrentTimeMillisFormatted() ,false);
                }/* catch (IOException e) {
                    throw new RuntimeException(e);
                  //  e.toString();
                }*/
                catch (Exception e)
                {

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
              Boolean exist= FileUtil.checkFile("/storage/emulated/0/Android/data",FileUtil.rootfolfer);
                String s2="//storage/emulated/0/Android/data/"+FileUtil.rootfolfer;
                tv1.setText(s2+":"+exist.toString());
                */
                /*
                Boolean exist= FileUtil.checkFile("/storage/emulated/0/Android/data/"+FileUtil.rootfolfer,"23082415");
                String s2="//storage/emulated/0/Android/data/"+FileUtil.rootfolfer+"23082415";
                tv1.setText(s2+":"+exist.toString());
               */

              //  Logger.show("click");
                Log.i("ERic","Eric !!!  Bt1");

                Intent intent = new Intent(MainActivity.this, VsUtilService.class);
                //startService(                                                                                                                                                                                                                                                                                                                                                                             intent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i("ERic","Eric !!!  Bt1 2");
                    startForegroundService(intent);
                    //  startService(intent);
                }
                else
                    startService(intent);




            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date=Calendar.getInstance();
                FileUtil.FindLastWorkFolder(date);
                /*
               // VsUtilService.LogApp();

              //  File B=new File(FileUtil.fullrootfolder+"/"+WorkFolder);
                Calendar date=Calendar.getInstance();
         //      date.add(Calendar.HOUR_OF_DAY, -1);
                Date date2 = date.getTime();

                Log.i("Eric","Eric time!!"+TimeUtil.getYearDateTimeforFolder(date2));

                VsUtilService.CurrentWorkFolder=TimeUtil.getYearDateTimeforFolder(date2);
                VsUtilService.LastWorkFolder=TimeUtil.getYearDateTimeforFolder(date2);
                VsUtilService.StartUpdate=false;
                VsUtilService.NeedUpdate=true;

*/

// 使用DateTimeFormatter格式化LocalDateTime对象
                //String formattedDateTime = date.format(formatter);

            //    getYearDateTimeforFolder(Date date )
                /*
                VsUtilService.CurrentWorkFolder="23090515";
                VsUtilService.LastWorkFolder="23090516";
                VsUtilService.StartUpdate=false;
                VsUtilService.NeedUpdate=true;

                Log.i("Eric","Eric 2023.09.04 Need Update");
                Log.i("Eric","Eric 2023.09.04 Need Update"+VsUtilService.NeedUpdate);

                Log.i("Eric","Eric 2023.09.04 Need Update Current"+VsUtilService.CurrentWorkFolder);
                Log.i("Eric","Eric 2023.09.04 Need Update L"+VsUtilService.LastWorkFolder);
*/
                /*
                Thread logPowerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Eric","Eric !!! Bt1 .click");

                        //1  File file=new File("/storage/emulated/0/Android/com.viewsonic.utility/1.pdf");
                        // /storage/emulated/0/android/data/com.viewsonic.utility
                        //  File file2=new File("/storage/emulated/0/Movies/1.zip");
                        File file=new File("/storage/emulated/0/android/data/com.viewsonic.utility/209111461311212110_23083113.zip");
                        //     File file=new File("/storage/emulated/0/Movies/232123423_23083113.zip");
                        //    File file=new File("/storage/emulated/0/Movies/1.zip");

                        Log.i("Eric","2023.0723 EE"+file.exists());
                        Log.i("Eric","2023.0723 EE"+file.getName());

                        Log.i("Eric","2023.0723 EE"+file.getPath());

                        try {

                            if (file == null)
                            {
                            }
                            else{


                                        Log.i("Eric","2023.0723 EE A!"+file.getPath());
                                InputStream inputStream=new FileInputStream(file);
                                Log.i("Eric","2023.0723 A"+file.getPath());
                                //  if (!export(inputStream, "209111461311212110_23083113.zip", "/", "172.21.8.244",
                                //          "viewsonic","$viewsonic0722")) {
                                if (!FTPUtil.export(inputStream, "209111461311212110_23083113.zip", "/2356/2346", "172.21.8.244",
                                        "viewsonic","$viewsonic0722")) {


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

                        }
                        catch (Exception e)
                        {
                            Log.i("Eric","Eric error sesrr e"+e.toString());

                        }
                    }
                });
                logPowerThread.start(); // 启动线程

*/

                /*  ZIP
                 try {
                    String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/23083113";
                    String zipFilePath = "/storage/emulated/0/Android/data/com.viewsonic.utility/"+NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())+"_23083113.zip";
                    String password = "$viewsonic0722";
                    ZipUtility.zipAndEncryptFolder(sourceFolderPath, zipFilePath, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                /*
                Log.i("VS Player", "Android API Version: " + Build.VERSION.SDK_INT);
                Log.i("VS Player", "Android OS Version " + Build.VERSION.RELEASE);
*/
                /*
                LocaleUtil localeUtil=new LocaleUtil(MainActivity.this);
                Log.i("Eric","Eric 20230832 !!! "+localeUtil.getCurrentLocale());
*/
                /*
                Log.i("Eric","Eric 20230832 !!! "+TimeUtil.getBootTimeString());

                */

                /*
                Boolean succeed = FileUtil.MakeDir("/storage/emulated/0/Android/data",FileUtil.rootfolfer);

                String s2="/storage/0/emulator/Android/data"+FileUtil.rootfolfer;
                tv1.setText(s2+":"+succeed.toString());
               */
                /*
                Boolean succeed = FileUtil.MakeDir("/storage/emulated/0/Android/data/"+FileUtil.rootfolfer,"23082415");
                 String s2="/storage/0/emulator/Android/data"+FileUtil.rootfolfer+"23082415";
                tv1.setText(s2+":"+succeed.toString());
                */
                /*
                Boolean succeed = FileUtil.MakeDir("/storage/emulated/0/Android/data/"+FileUtil.rootfolfer,TimeUtil.getCurrentTimeforFolder());
                String s2="/storage/0/emulator/Android/data"+FileUtil.rootfolfer+"23082415";
                tv1.setText(s2+":"+succeed.toString());


                 */
             //   String macadd=NetUtil.getWiFiMacAddress();

/* Try ZIP
                try {
                    String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/";
                    String zipFilePath = "/storage/emulated/0/Android/data/com.viewsonic.utility.zip";
                    String password = "your_password_here";
                    ZipUtility.zipAndEncryptFolder(sourceFolderPath, zipFilePath, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
  /*
                String Filename=FileUtil.CreateLogFileName(4);

                try {
                    FileUtil.writeLogToFile(Filename,"Test");
                    FileUtil.writeLogToFile(Filename,"444");
                    FileUtil.writeLogToFile(Filename,TimeUtil.getCurrentTimeMillisFormatted());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
*/
                //   FileUtil.writeLogToFile();
/*
                File A=new File(FileUtil.fullrootfolder);
                File B=new File(FileUtil.fullrootfolder+"/444");
                Log.i("Eric","AAA:"+B.getPath());
                Log.i("Eric","AAA:"+B.exists());

                if(B.exists())
                {
                    Log.i("Eric","AAA:"+B.getPath());
                }
                else{
                    Log.i("Eric","BBB:mkdir");
                    B.mkdir();
                    Log.i("Eric","ccc"+A.exists());

                }
                // if()
                //if(File)

                //File file = new File(filePath);

                //tv1.setText(":"+mad2);
*/
/*
                String s2=TimeUtil.getCurrentTimeforFolder();
                tv1.setText(s2);
*/

            }
        });

    }


    public void GetDevice()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //    getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);


           Log.i("VS Player", "Board " + Build.BOARD);
           Log.i("VS Player", "Brand  " + Build.BRAND);
           Log.i("VS Player", "Device " + Build.DEVICE);
        CurrentDeviceW=metrics.widthPixels;
        CurrentDeviceH=metrics.heightPixels;
        Log.i("VS Download", "UI " + CurrentDeviceW + " X " +CurrentDeviceH);
        String s1= "Board " + Build.BOARD+"\nBrand  " + Build.BRAND+"\nDevice " + Build.DEVICE
                    +"\nUI " + CurrentDeviceW + " X " +CurrentDeviceH;
        tv1.setText(s1);

    }

    private boolean export(InputStream stream, String name, String directory, String address, String username, String password) {
        try {


            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

            final FTPClient client = new FTPClient();
            client.connect(address, DEFAULT_FTP_PORT);
            if (client.login(username, password)) {
                client.makeDirectory(address);
                client.setFileType(FTP.ASCII_FILE_TYPE);
                client.enterLocalPassiveMode();
                client.storeFile("./" + directory + "/" + name, stream);

                client.logout();
                client.disconnect();
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }


}