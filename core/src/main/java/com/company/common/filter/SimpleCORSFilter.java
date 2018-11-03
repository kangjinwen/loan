package com.company.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



public class SimpleCORSFilter implements Filter {

	private static final Logger logger = Logger.getLogger(SimpleCORSFilter.class);
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");//将入口的数据都进行utf-8编码
		HttpServletRequest request = (HttpServletRequest) req;
		//访问日志开始
		String params = MyUtils.queryParamsFromRequst(request);
		logger.info("\n\n ip："+MyUtils.getRemoteIp(request)+"\n addr:"+request.getRequestURI()+"\n params:["+(null == params ? "无参数":params)+"]\n");
		//访问日志结束
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization, Accept, Accept-Encoding, Accept-Language, Cache-Control, Connection, Content-Length, Host, Referer, User-Agent");
		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}