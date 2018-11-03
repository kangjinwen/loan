package com.company.modules.common.utils;

import java.io.*;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Created by lsk on 2015/7/22.
 */
public class ZipUtil {
    public static void zipFiles(Collection<File> flist,OutputStream out){
    	String dir="test";
        try {
            ZipOutputStream zout=new ZipOutputStream(new BufferedOutputStream(out));
            int len=0;
            byte[] buf=new byte[4096];
            for (File file : flist) {
                zout.putNextEntry(new ZipEntry(file.getName()+"//"));
                BufferedInputStream bin=new BufferedInputStream(new FileInputStream(file));
                while((len=bin.read(buf))>0){
                    zout.write(buf,0,len);
                }
                zout.closeEntry();
                bin.close();
            }
            zout.finish();
            zout.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void zipFiles4j(Collection<File> flist,OutputStream out){
    	try {  
            net.lingala.zip4j.io.ZipOutputStream zipFile  = new net.lingala.zip4j.io.ZipOutputStream(out);
            
            ZipParameters parameters = new ZipParameters(); // 设置zip包的一些参数集合  
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式(默认值)  
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别（参数很多）  
            
            int len=0;
            byte[] buf=new byte[4096];
            for (File file : flist) {
            	zipFile.putNextEntry(file,parameters); 
            	 BufferedInputStream bin=new BufferedInputStream(new FileInputStream(file));
                 while((len=bin.read(buf))>0){
                	 zipFile.write(buf,0,len);
                 }
                 zipFile.closeEntry();
                 bin.close();
            }
            zipFile.finish();
            zipFile.close();
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
}
