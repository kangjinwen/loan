package com.company.modules.paramconfig.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.company.common.context.Constant;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ServletUtils;
import com.company.common.web.action.BaseAction;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.paramconfig.domain.ProductParam;
import com.company.modules.paramconfig.service.ProductParamService;
import com.company.modules.system.domain.SysUser;

/**
 * User:    wangmc
 * DateTime:2016-07-13 03:59:04
 * details: 产品类型表,Action请求层
 * source:  代码生成器
 */
@Controller
@RequestMapping("/modules/paramconfig/ProductParamAction")
public class ProductParamAction extends BaseAction {

    /**
     * 产品类型表的Service
     */
	@Autowired
	private ProductParamService productParamService;

    /**
     * 产品类型表表,插入数据
     * @param response      页面的response
     * @param json          页面参数
     * @throws ServiceException
     */
    @RequestMapping("/saveOrUpdateProductParam.htm")
    public void saveOrUpdateProductParam(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "status" ,required = false)String status,  //执行的动作
    	@RequestParam(value = "json" ,required = false)String json
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag=0;
	     SysUser sysUser = this.getLoginUser(request);
	     ProductParam productParam = new ProductParam();
	        //对json对象进行转换
	        if (!StringUtils.isEmpty(json))
	        	productParam  = JsonUtil.parse(json, ProductParam.class);
	        
		if(status.equals("create")){
		//动态插入数据
			productParam.setCreator(sysUser.getName());
			productParam.setCreateDate(new Date());
			flag=productParamService.insert(productParam);
		}else{
		 //修改数据
			productParam.setModifier(sysUser.getName());
			productParam.setModifyDate(new Date());
			flag=productParamService.update(productParam);
		}
		
		if(flag>0){//判断操作是否成功
        	returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        }else{
        	returnMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
        	returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        //返回给页面
        ServletUtils.writeToResponse(response, returnMap);
    }

    

    /**
     * 抵押物表,查询数据
     * @param response      页面的response
     * @param currentPage   当前页数
     * @param pageSize      每页限制
     * @param searchParam   查询条件
     * @throws ServiceException
     */
    @RequestMapping("/getProductParamList.htm")
    public void getProductParamList ( HttpServletRequest request,HttpServletResponse response,
                        @RequestParam(value = "currentPage") Integer currentPage,
                        @RequestParam(value = "pageSize") Integer pageSize,
                        @RequestParam(value = "searchParam",required = false)String searchParam)
    throws Exception{
    Map<String, Object> paramap=new HashMap<String, Object>();
        //对json对象进行转换
        if (!StringUtils.isEmpty(searchParam))
         paramap = JsonUtil.parse(searchParam, Map.class);
        
        paramap.put("is_delete", "0");  //未删除的数据
        PageBounds pageBounds = new PageBounds(currentPage, pageSize);
        //返回页面的json参数
      List<ProductParam> pageList=productParamService.getProductParamList(paramap,pageBounds);
      int totalCount=productParamService.getTotalCount(paramap);
      Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put("data", pageList);
		returnMap.put("totalCount", totalCount);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
	 * 
	 * @description 检验公式结果
	 * @param response
	 * @param request
	 * @param product
	 * @param id
	 * @throws Exception
	 * @author liyc
	 * @return void
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/inspect.htm")
	public void inspect(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "json", required = true) String product) throws Exception {
		Map<String, Object> param = JsonUtil.parse(product, Map.class);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> inspectDate = productParamService.inspect(param);
		
		returnMap.put(Constant.RESPONSE_DATA, inspectDate);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		ServletUtils.writeToResponse(response, returnMap);
	}
	
	  /**
     * 机构下拉框
     * @throws ServiceException
	   * 0:冠润；1：渠道；2：机构
     */
    @RequestMapping("/getOfficeCombo.htm")
    public void getOfficeCombo ( HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "type", required = false) String type)
    throws Exception{
    Map<String, Object> paramap=new HashMap<String, Object>();
		if (type != null &type != "") {
			paramap.put("type",type);
		}
        //返回页面的json参数
      List<Map<String, Object>> comboList=productParamService.getOfficeCombo(paramap);
      Map<String,Object> returnMap=new HashMap<String,Object>();
      
        //返回给页面
        returnMap.put("data", comboList);
		returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
		
        ServletUtils.writeToResponse(response, returnMap);
    }
    
    /**
     * 系统参数表表,逻辑删除
     * @param response      页面的response
     * @param json          页面参数
     * @throws ServiceException
     */
    @RequestMapping("/deleteProductParam.htm")
    public void deleteSysConfig(HttpServletRequest request,HttpServletResponse response,
    	@RequestParam(value = "id" ,required = false)String id
    	)throws Exception {
	     Map<String,Object> returnMap=new HashMap<String,Object>();
	     long flag=0;
	     ProductParam productParam = new ProductParam();
	     
	        productParam.setId(Long.valueOf(id));
		 //修改数据
	        productParam.setIsDelete(Long.valueOf("1"));
			flag=productParamService.update(productParam);
		
		if(flag>0){//判断操作是否成功
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

