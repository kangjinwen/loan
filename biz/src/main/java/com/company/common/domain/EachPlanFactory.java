package com.company.common.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.modules.common.exception.RDRuntimeException;
import com.company.modules.common.utils.EachPlan;


public abstract class EachPlanFactory {
    private static final Logger logger=LoggerFactory.getLogger(EachPlanFactory.class);
    public static enum PLANTYPE{
        common,
        henhaodai
    }
    private static Map<PLANTYPE,String> CLASS_MAPPER=new HashMap<PLANTYPE,String>();
    static{
        CLASS_MAPPER.put(PLANTYPE.common, "com.company.common.domain.CommonEachPlan");
    }
    public static EachPlan createEachPlan(PLANTYPE plantype,Object... args){
        EachPlan obj;
        Class<EachPlan> clazz;
        String className=CLASS_MAPPER.get(plantype);
        if(className==null){
            throw new IllegalArgumentException("plantype is Illegal!PLANTYPE:"+plantype);
        }
        
        try {
            clazz=(Class<EachPlan>) Class.forName(className);
            obj=ConstructorUtils.invokeExactConstructor(clazz,args);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new RDRuntimeException(e.getMessage(), e);
        } 
        return obj;
    }
}
