package com.company.modules.finance.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.company.modules.common.domain.PubLoan;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.finance.service.PubLoanService;
import com.company.modules.system.domain.SysUser;

/**
 * User:    fzc
 * DateTime:2016-08-26 05:51:23
 * details: 放款,Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/finance/PubLoanAction")
@Controller
public class PubLoanAction extends BaseAction {

    /**
     * 放款的Service
     */
	@Autowired
	private PubLoanService pubLoanService;

    /**
     * 放款表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param pubLoan	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdatePubLoan.htm")
    public void saveOrUpdatePubLoan(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "pubLoan", required = false) String pubLoan, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		PubLoan pubLoanInfo = new PubLoan();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(pubLoan)) {
			pubLoanInfo = JsonUtil.parse(pubLoan, PubLoan.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			pubLoanInfo.setCreator(sysUser.getId());
			pubLoanInfo.setCreateTime(new Date());
			influence = pubLoanService.insert(pubLoanInfo);
		} else {
			//如果表中有修改者   取当前登录人
			pubLoanInfo.setModifier(sysUser.getId());
			pubLoanInfo.setModifyTime(new Date());
			influence = pubLoanService.update(pubLoanInfo);
		}
		if (influence > 0) {
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		} else {
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
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
    @RequestMapping("/getPubLoanList.htm")
    public void getPubLoanList(HttpServletResponse response, HttpServletRequest request,
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
		List<PubLoan> pubLoans = pubLoanService.getPageListByMap(paramap);
		PageInfo<PubLoan> page = new PageInfo<PubLoan>(pubLoans);
		returnMap.put(Constant.RESPONSE_DATA, page.getList());
		returnMap.put(Constant.RESPONSE_DATA_TOTALCOUNT, page.getTotal());
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		// 返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
    * 根据id删除数据
    * @param response    页面的response
    * @param id          主键
    * @throws ServiceException
    */
    @RequestMapping("/deleteById.htm")
    public void deleteById(HttpServletResponse response, @RequestParam(value = "id") long id) throws Exception{
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = pubLoanService.deleteById(id);
		if(influence > 0){
			returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		}else{
			returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
		}
		//返回给页面
		ServletUtils.writeToResponse(response, returnMap);
    }
}
