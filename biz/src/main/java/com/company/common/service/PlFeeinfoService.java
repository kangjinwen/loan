package com.company.common.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.domain.PlFeeinfo;

/**
* User:    wulb
* DateTime:2016-08-18 17:01:44
* details: 费用信息,Service接口层
* source:  代码生成器
*/
public interface PlFeeinfoService {

    /**
     * 费用信息表,插入数据
     * @param plFeeinfo 费用信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long insert(PlFeeinfo plFeeinfo) throws Exception;

    /**
     * 费用信息表,修改数据
     * @param plFeeinfo 费用信息类
     * @return           返回页面map
     * @throws Exception
     */
    public long update(PlFeeinfo plFeeinfo) throws Exception;

	/**
     * 费用信息表,分页查询数据
     * @param data
     * @return
     * @throws Exception
     */
    public List<PlFeeinfo> getPageListByMap(Map<String , Object> data) throws Exception;
    
	/**
     * 费用信息表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public PlFeeinfo getItemInfoById(long id) throws Exception;
    
	/**
     * 费用信息表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public PlFeeinfo getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 费用信息表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(long id) throws Exception;

	public PlFeeinfo getItemInfoByConsultId(Long consultId);
}
