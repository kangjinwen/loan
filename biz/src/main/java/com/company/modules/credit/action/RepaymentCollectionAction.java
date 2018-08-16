/**
 * @title RepaymentCollectionAction.java
 * @package com.company.modules.credit.action
 * @author 吴国成
 * @version V1.0
 * created 2014年11月19日
 */
package com.company.modules.credit.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.company.modules.credit.domain.PubDundetail;
import com.company.modules.credit.service.PubDundetailService;
import com.company.modules.credit.service.RepaymentdetailService;
import com.company.modules.system.domain.SysUser;

/**
 * 催收业务
 * @version 1.0
 * @author 吴国成
 * @created 2014年11月19日 下午5:14:40
 */
@Controller
@RequestMapping("/modules/credit/RepaymentCollectionAction")
public class RepaymentCollectionAction  extends BaseAction{
	private static final Logger log=LoggerFactory.getLogger(RepaymentCollectionAction.class);
	@Autowired
	private RepaymentdetailService repaymentdetailService;
	@Autowired
	private PubDundetailService dundetailService;
	
	/**
	 * 需要还款催收列表
	 * 1、取还款状态为2 逾期的
	 * 2、且状态为0的3天后到期的期次。
	 * @param request
	 * @param response
	 * @param data 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年11月21日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRepaymentList.htm")
	public void getRepaymentList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "currentPage") Integer currentPage,
			@RequestParam(value = "pageSize") Integer pageSize,
	  		@RequestParam(value = "searchParams", required = false) String searchParams) throws Exception {
		log.info("#####################RepaymentCollectionAction-getRepaymentList 催收");
		// 返回给页面的Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> paramap = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		//对json对象进行转换
        if (!StringUtils.isEmpty(searchParams)){
         	paramap = JsonUtil.parse(searchParams, Map.class);
        }
        SysUser user = getSysUser();
		String officeId = user.getOfficeId();
		paramap.put("officeId", officeId);
        PageHelper.startPage(currentPage, pageSize);
		result = dundetailService.getDundetailAndRepaymentList(paramap);
        PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(result);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
	}
	
	
	/**
	 * 增加催收记录
	 * @param request
	 * @param response
	 * @param data 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年11月20日
	 */
	@RequestMapping("/save.htm")
	public void saveRepaymentCollectionContent(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="pubDundetail",required=false) String pubDundetail){
		log.info("###################RepaymentCollectionAction-saveRepaymentCollectionContent()#####################增加催收");
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			SysUser sysUser = this.getLoginUser(request);
			PubDundetail dundetail = new PubDundetail();
			// 对json对象进行转换
			if (!StringUtils.isEmpty(pubDundetail)) {
				dundetail = JsonUtil.parse(pubDundetail, PubDundetail.class);
			}
			dundetail.setCreateTime(new Date());
			dundetail.setCreator(sysUser.getId());
			dundetail.setUserName(sysUser.getName());
			dundetail.setIsDelete((byte)0);
			boolean isSuccess = dundetailService.addDundetail(dundetail);
			if(isSuccess){
				res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "操作成功");
			}else{
				res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				res.put(Constant.RESPONSE_CODE_MSG, "操作失败");
			}
			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据还款ID获得催收列表
	 * @param request
	 * @param response
	 * @param id 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年11月20日
	 */
	@RequestMapping("/list.htm")
	public void getRepaymentCollectionContentList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=false) String id){
		Map<String, Object> res=new HashMap<String, Object>();
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("repaymentId", id);
			List<PubDundetail> dunList = dundetailService.getDundetailList(paraMap);
			res.put("data", dunList);
			res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
			ServletUtils.writeToResponse(response, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
