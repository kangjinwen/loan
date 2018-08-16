package com.company.common.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.modules.contract.domain.PlContract;

/**
 * @author Administrator
 *
 */
public class RulesNumberUtil {

	/**
	 * 产生合同编号（营业部+日期+排重号）
	 * 
	 * @param pubMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String createContractNo(Map<String, Object> pubMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("BJFLD&WD");
		sb.append(DateUtil.dateStr(new Date(), DateUtil.DATE_PATTERN_2));
		List<PlContract> ls = (List<PlContract>) pubMap.get("contractList");
		if (null != ls && ls.size() == 0) {
			sb.append("001");
		} else {
			int b = ls.size();
			b++;
			if (b < 10) {
				sb.append("00" + b);
			} else if (b >= 10 && b < 100) {
				sb.append("0" + b);
			} else if (b >= 100 && b < 1000) {
				sb.append(b);
			}
		}
		return sb.toString();
	}
}
