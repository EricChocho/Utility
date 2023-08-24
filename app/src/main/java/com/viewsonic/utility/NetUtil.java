package com.viewsonic.utility;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtil {

    protected static InetAddress getLocalIPAddress() {
        InetAddress ip = null;
        try {

            Enumeration en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();
                Enumeration en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = (InetAddress) en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ip;
    }

    public static String getWiFiMacAddress(){

        String macAddress = "Not able to read the MAC address";
        BufferedReader br = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                br = new BufferedReader(new FileReader("/sys/class/net/wlan0/address"));
                ///sys/class/net/wlan0/address
                macAddress = br.readLine().toUpperCase();
            }
            else
            {
                InetAddress ip = getLocalIPAddress();

                byte[] b = NetworkInterface.getByInetAddress(ip)
                        .getHardwareAddress();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < b.length; i++) {
                    if (i != 0) {
                        buffer.append(':');
                    }

                    String str = Integer.toHexString(b[i]&0xFF);
                    buffer.append(str.length() == 1 ? 0 + str : str);
                }
                macAddress = buffer.toString().toLowerCase();


            }
        } catch (Exception e) {
            Log.i("Eric","!!!! Error+"+e.toString());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return macAddress;

    }

    public static String getWiFiMacAddressforFilename(String macAddress){

        String convertedMac="";

        try {
                // 去除冒号并将小写字母替换为数字
                convertedMac = macAddress.replace(":", "")
                        .replace("a", "10")
                        .replace("b", "11")
                        .replace("c", "12")
                        .replace("d", "13")
                        .replace("e", "14")
                        .replace("f", "15")
                        .replace("A", "10")
                        .replace("B", "11")
                        .replace("C", "12")
                        .replace("D", "13")
                        .replace("E", "14")
                        .replace("F", "15");

                return  convertedMac;


        } catch (Exception e) {
            Log.i("Eric","!!!! Error+"+e.toString());
        }
        return  convertedMac;

    }


}
