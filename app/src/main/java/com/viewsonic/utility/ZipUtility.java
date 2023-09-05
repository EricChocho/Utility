package com.viewsonic.utility;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import net.lingala.zip4j.util.Zip4jUtil;
import net.lingala.zip4j.util.Zip4jConstants;



import java.io.File;

public class ZipUtility {

    public static void zipAndEncryptFolder(String sourceFolderPath, String zipFilePath, String password) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword(password);

            zipFile.addFolder(new File(sourceFolderPath), parameters);
            System.out.println("Folder compressed and encrypted successfully.");
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void zipFolder(String sourceFolderPath, String zipFilePath) {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.DEFLATE_LEVEL_MAXIMUM);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
          //  parameters.setEncryptFiles(true);
         //   parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
          //  parameters.setPassword(password);

            zipFile.addFolder(new File(sourceFolderPath), parameters);
            System.out.println("Folder compressed and encrypted successfully.");
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
