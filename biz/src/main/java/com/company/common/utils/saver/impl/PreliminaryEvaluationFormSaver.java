package com.company.common.utils.saver.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.company.modules.common.utils.databean.BasicServiceDataBean;
import com.company.modules.common.utils.parser.impl.DefaultClassTypeParser;
import com.company.modules.common.utils.saver.AbstractServiceFormDraftSaverTemplate;
import com.company.modules.instance.service.CustomerEvaluationService;
import com.company.modules.instance.utils.databean.PreliminaryEvaluationDataBean;

/**
 * 客服初审保存器
 * @author wulb
 *
 */
@Service("preliminaryEvaluationFormSaver")
public class PreliminaryEvaluationFormSaver extends AbstractServiceFormDraftSaverTemplate {

	@Resource(name="preliminaryEvaluationServiceImpl")
	private CustomerEvaluationService customerEvaluationService;

	@Override
	protected BasicServiceDataBean parseDataBean(String serviceVariables, DefaultClassTypeParser classTypeParser) {
		return classTypeParser.parse(serviceVariables, PreliminaryEvaluationDataBean.class);
	}

    @Override
    protected void doSave(BasicServiceDataBean bean) throws Exception {
    	customerEvaluationService.saveDraft((PreliminaryEvaluationDataBean)bean);
    }
}
