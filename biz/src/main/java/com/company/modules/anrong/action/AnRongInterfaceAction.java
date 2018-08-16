package com.company.modules.anrong.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.anrong.domain.PubAnrongAttachment;
import com.company.modules.anrong.domain.PubArRiskResult;
import com.company.modules.anrong.service.AnRongService;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;

/**
 * User:    mcwang
 * DateTime:2016-08-10 03:59:46
 * details: 安融征信,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/anrong/anrongInterface")
@Controller
public class AnRongInterfaceAction extends BaseAction{
	@Autowired
	private AnRongService anRongService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getCreditReport.htm")
	public void getCreditReport(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "params", required = true) String params) throws Exception {

		Map<String, Object> requestParam = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(params))
			requestParam = JsonUtil.parse(params, Map.class);

		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String rootDir = request.getRealPath("/");// 文件根目录
		requestParam.put("rootDir", rootDir);
		SysUser loginUser = getLoginUser(request);

		requestParam.put("userid", loginUser.getId());
		Boolean isChecked = (Boolean) requestParam.get("isChecked");
		HttpSession session = request.getSession();

		String requestType = ((Integer) requestParam.get("riskType")).toString();

		if ("searching".equals(session.getAttribute(requestType))) {
			// 返回正在查询的提示
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, "该类型接口正在查询中，请稍后");
		}
		// //判断是否有未完成查询
		// List<PubArRiskResult>
		// checkStatus=anRongService.getCheckStatus(requestParam);
		// if(checkStatus.size()>0){
		// returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
		// returnMap.put(Constant.RESPONSE_CODE_MSG,"该类型接口正在查询中，请稍后");
		// }
		else {
			try {
				session.setAttribute(requestType, "searching");
//				if (isChecked) {// 再次确认请求
//					String result = anRongService.createCreditReport(requestParam);
//					if (!result.equals("")) {
//						returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
//						returnMap.put(Constant.RESPONSE_CODE_MSG, result);
//					} else {
//						returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//						returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
//					}
//
//				} else {
					int type = (Integer) requestParam.get("riskType");// 请求类型

					List<PubArRiskResult> arResultList = anRongService.getQueryCountItems(requestParam);
					String tmp = "";
//					if (arResultList.size() > 0) { // 已经查询过
//						if (type == 3) {
//							List<String> enterNames = new ArrayList<String>();
//							for (PubArRiskResult parr : arResultList) {
//								enterNames.add(parr.getRiskParam());
//							}
//							HashSet<String> hs = new HashSet<String>(enterNames); // 去除重复
//							tmp = hs.toString();
//						}
//						returnMap.put("status", "1");
//						returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//						returnMap.put(Constant.RESPONSE_CODE_MSG, tmp + "已发起过该接口查询，再次发起会重复扣费，是否继续？");
//					} else { // 未请求过

						String result = anRongService.createCreditReport(requestParam);
						if (!result.equals("")) {
							returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
							returnMap.put(Constant.RESPONSE_CODE_MSG, result);
						} else {
							returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
							returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
						}
				//	}
			//	}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(e.getMessage(), e, Constant.FAIL_CODE_VALUE);
			} finally {
				session.removeAttribute(requestType);
			}
		}
		ServletUtils.writeToResponse(response, returnMap);
	}
    
    /**
     * 分页查询数据
     * @param request      页面的request
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParams   查询条件
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/getPubAnrongAttachmentList.htm")
    public void getPubAnrongAttachmentList(HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value = "currentPage") Integer currentPage,
		@RequestParam(value = "pageSize") Integer pageSize,
  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception{
        // 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        
        PageHelper.startPage(currentPage, pageSize);
		List<PubAnrongAttachment> pubAnrongAttachments = anRongService.getPageListByMap(paramap);
		PageInfo<PubAnrongAttachment> page = new PageInfo<PubAnrongAttachment>(pubAnrongAttachments);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    
    
    /**
     * 下载安融附件附件
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
	@RequestMapping(value = "/downloadFile.htm")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id") long id
			) throws Exception {
		
		PubAnrongAttachment attachment= anRongService.getFileById(id);  //根据附件id下载附件
		String appDir = request.getRealPath("/");
	
			BufferedInputStream   bufferedInputStream=null;
			BufferedOutputStream bufferedOutputStream=null;
			try {
				File file = new File(appDir, attachment.getFilePath());
				
				String name = new String(file.getName().getBytes("gbk"), "iso8859-1");
				response.setContentType("octets/stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + name);
				
				bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
				bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			    byte[] buff = new byte[4096];
		        int bytesRead;
		        while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
		                bufferedOutputStream.write(buff, 0, bytesRead);
		            }
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (bufferedInputStream != null)
	                bufferedInputStream.close();
	            if (bufferedOutputStream != null)
	                bufferedOutputStream.close();
			}
	}

    
    

}
