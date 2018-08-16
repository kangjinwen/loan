package com.company.common.utils.saver;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.company.modules.common.exception.ObjectNotFoundException;
import com.company.modules.common.utils.ApplicationContextHelperBean;
import com.company.modules.common.utils.factory.ObjectFactory;
import com.company.modules.common.utils.saver.ServiceFormDraftSaver;

public class ServiceDraftSaverFactory implements ObjectFactory<ServiceFormDraftSaver> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceDraftSaverFactory.class);

    private static Map<String, String> saverNameAndInstanceMapping = new HashMap<String, String>();

    private static ServiceDraftSaverFactory instance = new ServiceDraftSaverFactory();

    public static ServiceDraftSaverFactory getInstance() {
        return instance;
    }

    static {
        saverNameAndInstanceMapping.put("preliminary-evaluation-form", "preliminaryEvaluationFormSaver");
    }

    @Override
    public ServiceFormDraftSaver getObject(Object qualifier) throws ObjectNotFoundException {
        String instanceName = saverNameAndInstanceMapping.get((String) qualifier);
        if (StringUtils.isEmpty(instanceName)) {
            logger.error("没有找到" + qualifier + "所对应的草稿保存器。");
            throw new ObjectNotFoundException("没有找到" + qualifier + "所对应的草稿保存器,请联系管理员。");
        }
        return ApplicationContextHelperBean.getBean(instanceName, ServiceFormDraftSaver.class);
    }

}
