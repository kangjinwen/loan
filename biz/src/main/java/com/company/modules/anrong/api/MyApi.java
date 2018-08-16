package com.company.modules.anrong.api;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.company.modules.anrong.utils.ApiAccessUtil;
import com.company.modules.anrong.utils.ApiAccessUtil.ApiHttpMethod;
import com.company.modules.anrong.utils.AssertUtil;


public class MyApi {
	
	private final String member;
	private final String sign;
	private final String url;

	/**
	 * 构造实例
	 * 
	 * @param member
	 *            安融为您分配的member号码
	 * @param sign
	 *            安融为您分配的接口密码
	 * @param url
	 *            接口访问地址
	 */
	public MyApi(String member, String sign, String url) {
		super();
		this.member = member;
		this.sign = sign;
		this.url = url;
	}
	
	/**
	 * 先设置一些msp接口共有的参数
	 * @param method
	 * @return
	 */
	private LinkedHashMap<String, String> createBasicParam(String method) {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		AssertUtil.hasText(member, "会员机构号不为空!");
		params.put("member", member);
		AssertUtil.hasText(sign, "会员密码不为空!");
		params.put("sign", sign);
		params.put("method", method);
		return params;
	}
	
	
	/**
	 * msp会员反欺诈
	 * @return
	 * @throws IOException
	 */
	public String createApply(Map<String, Object> map) throws IOException {
		
		LinkedHashMap<String, String> params=mspParam(map);
		
		return ApiAccessUtil.request(url, params, ApiHttpMethod.GET);
	}
	
	/**
	 * 验证msp会员参数
	 * @param map
	 * @return
	 */
	public LinkedHashMap<String, String> mspParam(Map<String, Object> map){
		
		LinkedHashMap<String, String> params = createBasicParam("createApply");
		
		String customerName=(String)map.get("customerName");
		String paperNumber=(String)map.get("paperNumber");
		String applyId=(String)map.get("applyId");
		String loanType=(String)map.get("loanType");
		String loanMoney=(String)map.get("loanMoney");
		String applyDate=(String)map.get("applyDate");
		String creditAddress=(String)map.get("creditAddress");
		String loanTimeLimit=(String)map.get("loanTimeLimit");
		String assureType=(String)map.get("assureType");
		String currency=(String)map.get("currency");
		String phones=(String)map.get("phones"); 
		String emails=(String)map.get("emails");
		String homeAddress=(String)map.get("homeAddress");
		String homeTel=(String)map.get("homeTel");
		String qq=(String)map.get("qq");
		String workUnit=(String)map.get("workUnit");
		
		AssertUtil.hasText(customerName, "姓名必填");
		params.put("customerName", customerName);
		AssertUtil.hasText(paperNumber, "身份证号必填");
		params.put("paperNumber", paperNumber);
		AssertUtil.hasText(applyId, "申请编号必填");
		params.put("applyId", applyId);
		AssertUtil.hasText(loanType, "借款类型必填");
		params.put("loanType", loanType);
		AssertUtil.hasText(loanMoney, "借款借款金额必填");
		params.put("loanMoney", loanMoney);
		AssertUtil.hasText(applyDate, "申请日期必填");
		AssertUtil.verifyDate(applyDate);
		params.put("applyDate", applyDate);
		AssertUtil.hasText(creditAddress, "借款地址必填");
		params.put("creditAddress", creditAddress);
		AssertUtil.hasText(loanTimeLimit, "借款期限必填");
		params.put("loanTimeLimit", loanTimeLimit);
		
		if (assureType != null && assureType.trim().length() > 0)
			params.put("assureType", assureType);
		if (currency != null && currency.trim().length() > 0)
			params.put("currency", currency);
		if (phones != null && phones.trim().length() > 0)
			params.put("phones", phones);
		if (emails != null && emails.trim().length() > 0)
			params.put("emails", emails);
		if (homeAddress != null && homeAddress.trim().length() > 0)
			params.put("homeAddress", homeAddress);
		if (homeTel != null && homeTel.trim().length() > 0)
			params.put("homeTel", homeTel);
		if (qq != null && qq.trim().length() > 0)
			params.put("qq", qq);
		if (workUnit != null && workUnit.trim().length() > 0)
			params.put("workUnit", workUnit);
		
		return params;
		
		
		
	}
	
	
	/**
	 * 查询个人关联企业信息
	 * @param n
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public String queryPerAndEn(String cid) throws IOException {
		LinkedHashMap<String, String> params = createBasicParam("");
		
		AssertUtil.hasText(cid, "身份证号必填");
		params.put("cid", cid);
		
		return ApiAccessUtil.request(url, params, ApiHttpMethod.GET);
	}
	
	/**
	 * 查询企业司法信息
	 * @param n
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public String queryEnterprise(String n, String id) throws IOException {
		LinkedHashMap<String, String> params = createBasicParam("");
		
		if (n != null && n.trim().length() > 0)
			params.put("n", n);
		if (id != null && id.trim().length() > 0)
			params.put("id", id);
		
		if((n==null||n.trim().length()<=0)&&(id==null||id.trim().length()<=0)){
			throw new IllegalArgumentException("企业名称或组织机构代码必须填一项！");
		}
		
		return ApiAccessUtil.request(url, params, ApiHttpMethod.GET);
	}



}
