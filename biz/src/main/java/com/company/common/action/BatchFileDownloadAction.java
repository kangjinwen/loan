package com.company.common.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.common.utils.JsonUtil;
import com.company.modules.common.domain.PubAttachment;
import com.company.modules.common.domain.PubBizAttachment;
import com.company.modules.common.service.PubAttachmentService;
import com.company.modules.common.service.PubBizAttachmentService;
import com.company.modules.system.domain.SysDictDetail;
import com.company.modules.system.service.SysDictDetailService;
import com.company.modules.system.service.SysDictService;

/**
 * 流程中上传附件按照目录批量导出
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/modules/common/BatchFileDownloadAction")
public class BatchFileDownloadAction {
	@Autowired
	private PubAttachmentService pubAttachmentService;
	@Autowired
	private PubBizAttachmentService pubBizAttachmentService;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private SysDictDetailService sysDictDetailService;
	
	@RequestMapping(value = "/downloadZip.htm")
	public void downloadZip(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "search") String search
			) throws Exception {

	//	String search="{\"projectId\":299,\"processInstanceId\":\"1520008\",\"type\":\"FIRST_INSTANCE_FILE\"}";
		 
		Map<String, Object> param = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(search))
			param = JsonUtil.parse(search, Map.class);
		String type = (String) param.get("type");
		String typeName=sysDictService.getDicNameByType(type);//根据类型获取附件类型名称
		
		List<SysDictDetail> sddList = sysDictDetailService.getBizFileTypes(type); // 获取业务类型对应的文件类型
		
		Map<String, String> typeMap=new HashMap<String,String>();
		List<String> fileTypes=new ArrayList<String>();
		for(SysDictDetail s: sddList){
			typeMap.put(s.getItemCode(),s.getItemValue());
			fileTypes.add(s.getItemCode());
		}
		if(fileTypes.size()<=0)//若字典未配置则设置为查询不到值
		fileTypes.add(type);
		
		param.put("bizFileTypes",fileTypes);
		param.put("state", "1");
		
		BufferedInputStream   bufferedInputStream=null;
		BufferedOutputStream bufferedOutputStream=null;
		
		List<PubAttachment> list = pubAttachmentService.select(param);
		//第一步房产资料是保存pub_biz_attachment表，要单独处理。
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("relationId", (String)param.get("processInstanceId"));
		List<PubBizAttachment> pubBizAttachmentList = pubBizAttachmentService.queryAll(mapParam);
		
		String appDir = request.getSession().getServletContext().getRealPath("/");// 文件根目录

		Path rootDir = Paths.get(appDir); // 获取路径path
		final Path tempDir = Files.createTempDirectory(rootDir, ""); //创建一层临时文件夹
		
	try {
		Path tempFile = Files.createTempDirectory(tempDir, ""); // 创建二层临时文件夹
		for (PubAttachment rec : list) {
			  for (String btype : typeMap.keySet()) {     //将文件类型跟中文名映射
				  
			if (rec.getBtype().equals(btype)) { // 判断类型
				File file = new File(appDir, rec.getFilePath());
				if (file.exists()) {
					File fa = new File(tempFile + "/"+typeMap.get(btype)); //创建文件类型名文件夹
					if (!fa.exists()) {
						fa.mkdirs();
					}
					File fi = new File(fa, file.getName()); // 复制生成的新文件
					copyFileUsingJava7Files(file, fi);
				}
			}
			  }
		}
		//把房本信息附件也加到第二层目录中来
		for (PubBizAttachment rec : pubBizAttachmentList) {
			  for (String btype : typeMap.keySet()) {     //将文件类型跟中文名映射
				  
			if (rec.getBizType().equals(btype)) { // 判断类型
				File file = new File(appDir, rec.getFilePath());
				if (file.exists()) {
					File fa = new File(tempFile + "/"+typeMap.get(btype)); //创建文件类型名文件夹
					if (!fa.exists()) {
						fa.mkdirs();
					}
					File fi = new File(fa, file.getName()); // 复制生成的新文件
					copyFileUsingJava7Files(file, fi);
				}
			}
			  }
		}
		String newfile = tempFile.toString();
		String zipFile = tempDir.toString()+ "/result.zip"; //压缩文件名
		
		zipMultiFile(newfile, zipFile, false);  //打包
		//rsponse返回信息
			String zipName = new String((typeName+".zip").getBytes("gbk"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + zipName);
			
			File downLoadFile = new File(zipFile);
		    bufferedInputStream = new BufferedInputStream(new FileInputStream(downLoadFile));
		    bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
		    byte[] buff = new byte[4096];
            int bytesRead;
            while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
                bufferedOutputStream.write(buff, 0, bytesRead);
            }
		} catch (Exception e) {
			// TODO: handle exception
			  e.printStackTrace();
			  deleteAllFilesOfDir(tempDir.toFile()); 
		}
		finally {
			if (bufferedInputStream != null)
                bufferedInputStream.close();
            if (bufferedOutputStream != null)
                bufferedOutputStream.close();
        	//定时任务
        	new Timer().schedule(new TimerTask() {
        			@Override
        			public void run() {
        				// TODO Auto-generated method stub
        			deleteAllFilesOfDir(tempDir.toFile());  //5秒延时删除临时文件夹
        			}
        		}, 1000*5);
		}
		
	}

	/**
	 * java7复制文件方法
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath());
	}

	/**
	 * 压缩整个文件夹中的所有文件，生成指定名称的zip压缩包
	 * 
	 * @param filepath
	 *            文件所在目录
	 * @param zippath
	 *            压缩后zip文件名称
	 * @param dirFlag
	 *       	  zip文件中第一层是否包含一级目录，true包含；false没有 
	 */
	public static void zipMultiFile(String filepath, String zippath, boolean dirFlag) {
		try {
			File file = new File(filepath);// 要被压缩的文件夹
			File zipFile = new File(zippath);
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File fileSec : files) {
					if (dirFlag) {
						recursionZip(zipOut, fileSec, file.getName() + File.separator);
					} else {
						recursionZip(zipOut, fileSec, "");
					}
				}
			}
			zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileSec : files) {
				recursionZip(zipOut, fileSec, baseDir + file.getName() + File.separator);
			}
		} else {
			byte[] buf = new byte[1024];
			InputStream input = new FileInputStream(file);
			zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
			int len;
			while ((len = input.read(buf)) != -1) {
				zipOut.write(buf, 0, len);
			}
			input.close();
		}
	}
	
	/**
	 * 删除文件夹(包含文件)
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    path.delete();  
	}  
	
	  
	

}
