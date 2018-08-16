package com.company.modules.common.service;

import com.company.modules.common.utils.databean.BasicServiceDataBean;

/**
 * 可持久化草稿
 * @author FHJ
 *
 */
public interface DraftPersistable {
	/**
	 * 持久化
	 * @param serviceDataBean
	 * @throws Exception
	 */
	void saveDraft(BasicServiceDataBean serviceDataBean) throws Exception;
}
