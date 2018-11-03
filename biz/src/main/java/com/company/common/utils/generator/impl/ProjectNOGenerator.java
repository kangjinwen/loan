package com.company.common.utils.generator.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.company.common.service.PubProjectService;
import com.company.common.utils.generator.NOGenerator;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;
import com.company.modules.system.domain.SysOffice;
import com.company.modules.system.service.SysOfficeService;

/**
 * Created by FHJ on 2015/4/1.
 */
public class ProjectNOGenerator extends NOGenerator {
	static class ProjectNOGeneratorMapper {
		static Map<String, String> PROJECT_CODE_MAPPER = new HashMap<String, String>();
		static volatile boolean isinit = false;

		private ProjectNOGeneratorMapper() {
		}

		public static synchronized String createProjectNO(String subProjectCode, PubProjectService pubProjectService)
				throws ServiceException {
			String value = null;
			if (value == null) {
				long obj;
				try {
					obj = pubProjectService.queryCountOfApplicationRecord();
					if (obj == 0) {
						value = "00001";
					} else {
						value = changeFormart(obj);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return subProjectCode + value;
		}

		// 将数字转换为五位数
		public static String changeFormart(long data) {
			String value = null;
			long initial = 100000;
			if (data >= 0) {
				initial += data + 1;
				String str = String.valueOf(initial);
				value = str.substring(1);
			} else {
			}
			return value;
		}

		private static String fillZero(int code, int length) {
			String codeStr = String.valueOf(code);
			if (codeStr.length() < length) {
				String zeros = zeroStr(length - codeStr.length());
				codeStr = zeros + codeStr;
			}
			return codeStr;
		}

		private static String zeroStr(int length) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				sb.append("0");
			}
			return sb.toString();
		}
	}

	/**
	 * 生成项目编号 规则：机构区号 + 机构序号 + 5位自增长数字
	 * 
	 * @param dataSource
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public String generateName(Map<String, Object> data) throws ServiceException {
		PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = (PreliminaryEvaluationDataBean) data
				.get("preliminaryEvaluationDataBean");
		// 项目编号生成规则:机构序号 +年月日+本次申请在系统中的次序（转化五位数格式）
		StringBuilder projectCode = new StringBuilder();
		SysOfficeService sysOfficeService = (SysOfficeService) data.get("sysOfficeService");
		SysOffice sysOffice = sysOfficeService.getOfficeById(Long.valueOf(preliminaryEvaluationDataBean.getOfficeId()));
		// 拼接机构序号
		if (sysOffice.getOfficeNumber() == null || sysOffice.getOfficeNumber().length() == 0) {
			projectCode.append("");
		} else {
			projectCode.append(sysOffice.getOfficeNumber());
		}

		// 拼接年月日
		projectCode.append(getDateString());
		PubProjectService pubProjectService = (PubProjectService) data.get("pubProjectService");
		return ProjectNOGeneratorMapper.createProjectNO(projectCode.toString(), pubProjectService);
	}

	public String getDateString() throws ServiceException {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String s = format.format(date);
		return s;

	}
}
