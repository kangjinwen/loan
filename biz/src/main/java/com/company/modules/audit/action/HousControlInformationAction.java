package com.company.modules.audit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUser;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.audit.domain.HousControlInformation;
import com.company.modules.audit.service.HousControlInformationService;

/**
 * User:    fzc
 * DateTime:2016-08-14 01:29:02
 * details: 风控信息表(面审),Action请求层
 * source:  代码生成器
 */
@RequestMapping("/modules/audit/HousControlInformationAction")
@Controller
public class HousControlInformationAction extends BaseAction {

    /**
     * 风控信息表(面审)的Service
     */
	@Autowired
	private HousControlInformationService housControlInformationService;

    /**
     * 风控信息表(面审)表,插入数据
     * @param request	页面的request
     * @param response	页面的response
     * @param housControlInformation	页面参数
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdateHousControlInformation.htm")
    public void saveOrUpdateHousControlInformation(HttpServletRequest request, HttpServletResponse response, 
    	@RequestParam(value = "housControlInformation", required = false) String housControlInformation, @RequestParam(value = "status", required = false) String status) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		long influence = 0;
		SysUser sysUser = this.getLoginUser(request);
		HousControlInformation housControlInformationInfo = new HousControlInformation();
		// 对json对象进行转换
		if (!StringUtils.isEmpty(housControlInformation)) {
			housControlInformationInfo = JsonUtil.parse(housControlInformation, HousControlInformation.class);
		}
		if (status.equals("create")) {
			//如果表中有创建者   取当前登录人
			housControlInformationInfo.setCreator(sysUser.getId());
			housControlInformationInfo.setCreateTime(new Date());
			influence = housControlInformationService.insert(housControlInformationInfo);
		} else {
			//如果表中有修改者   取当前登录人
			housControlInformationInfo.setModifier(sysUser.getId());
			housControlInformationInfo.setModifyTime(new Date());
			influence = housControlInformationService.update(housControlInformationInfo);
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
    @RequestMapping("/getHousControlInformationList.htm")
    public void getHousControlInformationList(HttpServletResponse response, HttpServletRequest request,
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
		List<HousControlInformation> housControlInformations = housControlInformationService.getPageListByMap(paramap);
		PageInfo<HousControlInformation> page = new PageInfo<HousControlInformation>(housControlInformations);
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
		long influence = housControlInformationService.deleteById(id);
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
