package com.company.modules.common.utils;

import java.io.*;
import java.util.Collection;

import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class DirZipUtil {

    public static void createZip(String sourcePath, OutputStream out) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(out));
            zos.setEncoding("gbk");//此处修改字节码方式。
            writeZip(new File(sourcePath), "", zos);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {

        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                if(files.length != 0)
                {
                    for(File f:files){
                        writeZip(f, parentPath, zos);
                    }
                }
                else
                {       //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
