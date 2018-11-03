package com.company.common.spring.security.authentication.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;

public class AccessAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
		context.put(Constant.RESPONSE_CODE_MSG, "登录失败");
		ServletUtils.writeToResponse(response, context);

	}

}
