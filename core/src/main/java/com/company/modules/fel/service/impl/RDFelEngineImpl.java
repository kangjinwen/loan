/**Copyright (c) 杭州融都科技股份有限公司*/
package com.company.modules.fel.service.impl;

import com.greenpineyu.fel.FelEngineImpl;
import com.company.modules.fel.extension.Max;
import com.company.modules.fel.extension.Min;
import com.company.modules.fel.extension.Power;
import com.company.modules.fel.extension.Sqrt;

/**
 *	@Description
 *  @author liyc,lyc1@erongdu.com
 *  @CreatTime 2016年8月11日 上午10:49:07
 *  @since version 1.0.0
 */
public class RDFelEngineImpl extends FelEngineImpl{
	
	public RDFelEngineImpl() {
		super();
		this.initFunction();
	}
	
	public void initFunction(){
		this.addFun(new Power());
		this.addFun(new Sqrt());
		this.addFun(new Min());
		this.addFun(new Max());
	}
	
}
