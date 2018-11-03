package com.company.common.utils.upload;

import com.company.common.context.Constant;
import com.alibaba.fastjson.JSONObject;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.service.ChannelPartnerService;
import com.oreilly.servlet.MultipartRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.util.List;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import com.company.modules.system.domain.SysUploadInfo;


/**
 * 文件上传公共方法
 *
 */
@Service
public class CustomFileUpload {

	private Map<String, Object> uploadReqData;

	private String getSimplePath(String dirPath) {
		String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
		StringBuilder stringBuilder = new StringBuilder().
				append(dirPath).append(dirName).append(File.separator);
		return stringBuilder.toString();
	}

	public  boolean isPicture(File file){
		try {
			Image image = ImageIO.read(file);
			return image != null;
		} catch(IOException ex) {
			return false;
		}
	}

	public Boolean mkDirPath(String dirPath) {

		Boolean res = false;
		File dirSave = new File(dirPath);
		if (dirSave.exists()) {
			res = dirSave.isDirectory();
		} else {
			res =dirSave.mkdir();
			dirSave.setReadable(true,false);
			dirSave.setWritable(true, false);
			dirSave.setExecutable(true, false);
		}
		return  res;
	}

	public Map<String, Object> getUploadReqData() { return uploadReqData;}


	public byte[] getFileByteArray(File srcFile, File tarFile) throws Exception {

		Thumbnails.of(srcFile.getPath()).size(146, 146).toFile(tarFile.getPath());
		FileInputStream fis = new FileInputStream(tarFile);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 10];
		int len = -1;
		while((len = fis.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		byte[] byteArray = bos.toByteArray();
		fis.close();
		return byteArray;
	}

	public byte[] doTransBlobData(Blob blob, String fileName) throws Exception {

		// 查看blob,可以通过流的形式取出来。  注意一定要是用流的方式读取出来
		InputStream is = blob.getBinaryStream();
		BufferedInputStream buffis = new BufferedInputStream(is);
		// 保存到buffout
		BufferedOutputStream buffout = new BufferedOutputStream(new FileOutputStream(fileName));
		byte[] buf = new byte[1024];
		int len = buffis.read(buf, 0, 1024);
		while (len > 0)
		{
			buffout.write(buf);
			len = buffis.read(buf, 0, 1024);
		}
		buffout.flush();
		buffout.close();
		buffis.close();
		return buf;
	}

	public List<SysUploadInfo> doFileUpload(HttpServletRequest request, String savePath, String urlPath, Boolean isCompress) throws Exception  {

		int inmaxPostSize = 10 * 1024 * 1024;
		List<String> urls = new ArrayList<String>();

		List<SysUploadInfo> lists = new ArrayList<SysUploadInfo>();

		MultipartRequest multirequest = null;
		RenamePolicyCos myRenamePolicyCos = new RenamePolicyCos();
		HttpServletRequestProxy srp = new HttpServletRequestProxy(request);

		StringBuilder bakStringBuilder = new StringBuilder().append(savePath).append("bak");
		String bakDirPath = bakStringBuilder.toString();
		mkDirPath(bakDirPath);

        try {

			if ((mkDirPath(savePath)) &&(mkDirPath(getSimplePath(savePath)))) {
				multirequest = new MultipartRequest(srp, getSimplePath(savePath), inmaxPostSize, "utf8", myRenamePolicyCos);
				if (uploadReqData != null) {uploadReqData.clear();}
				uploadReqData = JSONObject.parseObject(multirequest.getParameter("data"), Map.class);
				Enumeration<String> filedFileNames = multirequest.getFileNames();
				Map<String, Object> result = new HashMap<String, Object>();
				if (null != filedFileNames && filedFileNames.hasMoreElements()) {
					byte[] fileByteArray = new byte[1024];
					String fieldName = filedFileNames.nextElement();
					File uploadFile = multirequest.getFile(fieldName);
					if (isPicture(uploadFile)) {
						//形成缩略图并保存字节数组
						StringBuilder bakFilePathBuilder = new StringBuilder().append(bakDirPath).append(File.separator).append(uploadFile.getName());
						String bakFilePath = bakFilePathBuilder.toString();
						File newFile = new File(bakFilePath);
						fileByteArray = getFileByteArray(uploadFile, newFile);
						newFile.delete();
					}
					if ((isCompress) && (uploadFile.length() > 1024 * 1024) && (isPicture(uploadFile))) {
						Thumbnails.of(uploadFile.getPath()).scale(0.8f).outputQuality(0.8f).toFile(uploadFile.getPath());
					}
					uploadFile.setReadable(true, false);

					String dirName = new SimpleDateFormat("yyyy-MM").format(new Date());
					StringBuilder stringBuilder = new StringBuilder().append(urlPath).append(dirName).append(File.separator).append(uploadFile.getName());
					String url = stringBuilder.toString();

					SysUploadInfo sysUploadInfo = new SysUploadInfo();
					sysUploadInfo.setName(uploadFile.getName());
					sysUploadInfo.setSaveFilePath(uploadFile.getPath());
					sysUploadInfo.setOriginaName(multirequest.getOriginalFileName(fieldName));
					sysUploadInfo.setUrl(url);
					sysUploadInfo.setFileSize(new BigDecimal(uploadFile.length()).divide(new BigDecimal(1024))
							.setScale(0, RoundingMode.CEILING).longValue());
					if (isPicture(uploadFile)) {
						sysUploadInfo.setZipFileBytes(fileByteArray);
					}
					lists.add(sysUploadInfo);
				} else {
					throw new ServiceException("文件目录异常,请检查");
				}
			}
		} catch (IOException e) {
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		return lists;
	}
}
