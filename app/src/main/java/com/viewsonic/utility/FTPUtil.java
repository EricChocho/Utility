package com.viewsonic.utility;

import android.os.StrictMode;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPUtil {

    private FTPClient pFtpClient;
    public static final int DEFAULT_FTP_PORT = 21;


    public FTPUtil()
    {
        pFtpClient=new FTPClient();
    }

    public FTPClient getpFtpClient() {
        return pFtpClient;
    }

    /////////////////////////  Eric PC Local
final static  String username="viewsonic";
    final static  String address=    "172.21.8.244";
    final static  String pw="$viewsonic0722";
//////////////////////////////////

    final static  String address2=    "3.24.106.158";
    final static  String pw2="e4rH$UX2";
//////////////////////////////////




    public static boolean export(InputStream stream, String name, String directory, String address, String username, String password) {
        try {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

            final FTPClient client = new FTPClient();
            client.connect(address, DEFAULT_FTP_PORT);
            if (client.login(username, password)) {
             //   client.makeDirectory(address);
                client.makeDirectory(directory);
                client.setFileType(FTP.BINARY_FILE_TYPE);  // ZIP
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


    public void ftpconnect()
    {
        if(pFtpClient!=null)
        {
            new Thread(){
                @Override
                public void run() {

                    try {
                        Log.i("Eric", "Eric  AAA!!! 2023.08.23 ###");

                        pFtpClient.connect("172.21.8.244");

                   //     pFtpClient.connect("172.21.10.171");

                        Log.i("Eric", "Eric  AAA!!! 2023.08.28 ###" + pFtpClient.isConnected());

                         pFtpClient.login("viewsonic", "$viewsonic0722");
                    //    pFtpClient.login("viewsonic", "11111");


                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###" + pFtpClient.isConnected());
                        pFtpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        pFtpClient.changeWorkingDirectory("/");
                       pFtpClient.makeDirectory("Folder");



                   //1     String localFilePath="/storage/emulated/0/Android/data/com.viewsonic.utility/232123423_23083113.zip";
                        String localFilePath="/storage/emulated/0/Android/data/com.viewsonic.utility/1.txt";

                        java.io.File localFile = new java.io.File(localFilePath);
                    //    java.io.FileInputStream inputStream = new java.io.FileInputStream(localFile);

                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###" +localFile.exists());
                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###" +localFile.getPath());

                        pFtpClient.setBufferSize(1024);

                        FileInputStream fis = new FileInputStream(localFile);
                        pFtpClient.enterLocalPassiveMode();
                        pFtpClient.setControlKeepAliveReplyTimeout(60*1000);

                        boolean success  = pFtpClient
                                .storeFile("/", fis);




                        if (success ) {
                         //   if (retrieveListener != null) {
                            Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###22" + success );
                          //  }
                        } else {
                            //if (retrieveListener != null) {
                            //    retrieveListener.onError(ERROR.UPLOAD_ERROR,
                            Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###22" + success );
                           // }
                        }







                   //     boolean success = pFtpClient.storeFile("/", inputStream);

                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28 ###22" + success );
                        fis.close();

/*  pFtpClient.makeDirectory(Folder)
                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28  work PATH###" + pFtpClient.printWorkingDirectory());


                        pFtpClient.setFileType(FTP.LOCAL_FILE_TYPE);
                        FTPFile[] fs = pFtpClient.listDirectories();
                        FTPFile[] fs2 = pFtpClient.listFiles();
                        Log.i("Eric", "!!! 2023.08.28 ###" + fs2.length);
                        Log.i("Eric", "!!! 2023.08.28  33 ###" + fs.length);
                        for (int i = 0; i < fs2.length; i++) {
                            //fs[i].getName();
                            Log.i("Eric", "!!! 2023.08.28 i=" + i + ": name :" + fs2[i].getName());
                            Log.i("Eric", "!!! 2023.08.28 i=" + i + ": name :" + fs2[i].getType());


                        }
*/

                        //  ftpclient.makeDirectory("test");

                        //  ftpclient.makeDirectory()


                    } catch (IOException e) {

                        Log.i("Eric","EEEEDD:"+ e.toString());
                        throw new RuntimeException(e);
                    }
                    catch (Exception ee)
                    {


                        Log.i("Eric","EEEE:"+ ee.toString());
                    }

                }

            }.start();


        }
    }


    public void ftpMakeFolder(String Folder)
    {
        if(pFtpClient!=null)
        {
            new Thread(){
                @Override
                public void run() {

                    try {
                        Log.i("Eric", "Eric  AAA!!! 2023.08.23 ###"+Folder);

                    //    if (pFtpClient.isConnected()) {
                            //  pFtpClient.changeWorkingDirectory("/");

                            Log.i("Eric", "Eric  AAA!!! 2023.08.23 ### 2::"+Folder);
                            pFtpClient.makeDirectory(Folder);
/*
                        pFtpClient.changeWorkingDirectory("/");

                        Log.i("Eric", "Eric  AAA!!! 2023.08@@@.28  work PATH###" + pFtpClient.printWorkingDirectory());

}

                        pFtpClient.setFileType(FTP.LOCAL_FILE_TYPE);
                        FTPFile[] fs = pFtpClient.listDirectories();
                        FTPFile[] fs2 = pFtpClient.listFiles();
                        Log.i("Eric", "!!! 2023.08.28 ###" + fs2.length);
                        Log.i("Eric", "!!! 2023.08.28  33 ###" + fs.length);
                        for (int i = 0; i < fs2.length; i++) {
                            //fs[i].getName();
                            Log.i("Eric", "!!! 2023.08.28 i=" + i + ": name :" + fs2[i].getName());
                            Log.i("Eric", "!!! 2023.08.28 i=" + i + ": name :" + fs2[i].getType());


                        }
*/

                            //  ftpclient.makeDirectory("test");

                            //  ftpclient.makeDirectory()

                     //   }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        Log.i("Eric", "20230831 EEE" +e.toString());
                    }


                }
            }.start();


        }



    }


}
