package com.viewsonic.utility;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
