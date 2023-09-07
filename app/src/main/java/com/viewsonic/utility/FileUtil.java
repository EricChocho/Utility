package com.viewsonic.utility;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class FileUtil {


    public static  String rootfolder="com.viewsonic.utility";
    public static  String fullrootfolder="/storage/emulated/0/Android/data/com.viewsonic.utility";

    static public Boolean MakeDir(String parent,String child)
    {
        Boolean success=false;

        File d=new File(parent,child);
        success=d.mkdir();

        return  success;

    }
    static String  findfolder;
    public static String FindLastWorkFolder(Calendar now){
        findfolder="";

     //   String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/"+LastWorkFolder;
        String sourceFolderPath = "/storage/emulated/0/Android/data/com.viewsonic.utility/";

       // Calendar date=Calendar.getInstance();
        //      date.add(Calendar.HOUR_OF_DAY, -1);
        for (int i=1;i<25;i++)
        {
            now.add(Calendar.HOUR_OF_DAY,-1);
            Date date2 = now.getTime();
            findfolder= TimeUtil.getYearDateTimeforFolder(date2);
            Log.i("Eric","Eric "+i+" :"+findfolder);
            File b=new File(sourceFolderPath+findfolder);
            Log.i("Eric","Eric "+i+" :"+b.getPath());
            Log.i("Eric","Eric "+i+" :"+b.exists());
            if( b.exists()){

                break;
            }
            else {
                findfolder="";
            }




        }

        Log.i("Eric"," Eric  Find :"+findfolder);


        return findfolder;
    }

    public static void writeLogToFile(String fileName, String logLine,Boolean Append) throws IOException {

        File A=new File(FileUtil.fullrootfolder);
        if(!A.exists()) A.mkdir();

        String WorkFolder=TimeUtil.getCurrentTimeforFolder();

        File B=new File(FileUtil.fullrootfolder+"/"+WorkFolder);
        Log.i("Eric","AAA:"+B.getPath());
        Log.i("Eric","AAA:"+B.exists());

        if(!B.exists()){

            if(VsUtilService.CurrentWorkFolder!="")
            {
                VsUtilService.LastWorkFolder = VsUtilService.CurrentWorkFolder;
                VsUtilService.CurrentWorkFolder=WorkFolder;
                VsUtilService.NeedUpdate =true;


                Log.i("Eric","20230904 Update "+VsUtilService.LastWorkFolder);

            }
            else
            {

                VsUtilService.LastWorkFolder ="" ;
                VsUtilService.CurrentWorkFolder=WorkFolder;
                VsUtilService.NeedUpdate =false;

            }
            B.mkdir();
        }
        else {
                VsUtilService.CurrentWorkFolder = WorkFolder;






        }

        File file = new File(B.getPath()+"/"+fileName);

        Log.i("Eric","DDD:"+file.getPath());


        try {
            // 如果檔案不存在，則創建新檔案
            if (!file.exists()) {
                file.createNewFile();
            }

            // 使用 FileOutputStream 寫入檔案
            FileOutputStream fos = new FileOutputStream(file, Append); // true 表示追加模式
            fos.write(logLine.getBytes());
            fos.write(System.getProperty("line.separator").getBytes()); // 換行符號
            fos.close();
        } catch (FileNotFoundException e) {
            Log.i("Eric","Error:"+e.toString());
        } catch (IOException e) {
            Log.i("Eric","Error:"+e.toString());
        }
        catch(Exception e) {
           Log.i("Eric","Error:"+e.toString());

        }
    }


    static public String CreateLogFileName(int Count )
    {
        String FileName="";
        FileName=NetUtil.getWiFiMacAddressforFilename(NetUtil.getWiFiMacAddress())+"_"+Integer.toString(Count)+"_"+TimeUtil.getCurrentTimeforFilename()+".log";


        return  FileName;

    }

    static public Boolean checkFile(String parent,String child )
    {
        Boolean exist=false;
        File f=new File(parent,child);

        if(f.exists()) exist=true;
        else  exist=false;



        return  exist;

    }
}
