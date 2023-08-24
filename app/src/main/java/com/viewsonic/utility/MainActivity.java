package com.viewsonic.utility;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button1,button2,button3;
    TextView tv1;
    int CurrentDeviceW,CurrentDeviceH;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // 如果沒有權限，就向使用者要求權限
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            // 已經有權限，執行程式邏輯
            executeAppLogic();
        }






    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
            } else {
                // 使用者拒絕了權限，這裡你可以顯示一個訊息給使用者，然後退出應用程式
                Toast.makeText(this, "需要讀取和寫入外部儲存權限來執行應用程式",    Toast.LENGTH_SHORT).show();
                finish(); // 退出應用程式
            }
        }
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
                String s2="IP:"+NetUtil.getLocalIPAddress()+"\n MAc:"+NetUtil.getWiFiMacAddress();
                tv1.setText(s2);
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


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String Filename=FileUtil.CreateLogFileName(4);

                try {
             //       FileUtil.writeLogToFile(Filename,"Test");
             //       FileUtil.writeLogToFile(Filename,"444");
             //       FileUtil.writeLogToFile(Filename,TimeUtil.getCurrentTimeMillisFormatted());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

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
        tv1.setText(s1























































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































        );

    }
}