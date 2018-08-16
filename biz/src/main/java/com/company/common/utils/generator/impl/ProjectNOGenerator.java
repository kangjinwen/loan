package com.company.common.utils.generator.impl;

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
public class ProjectNOGenerator extends NOGenerator{
    static class ProjectNOGeneratorMapper{
        static Map<String,String> PROJECT_CODE_MAPPER=new HashMap<String,String>();
        static volatile boolean isinit=false;
        private ProjectNOGeneratorMapper(){}
        public static synchronized String createProjectNO(String subProjectCode,PubProjectService pubProjectService) throws ServiceException{
            String value=PROJECT_CODE_MAPPER.get(subProjectCode);
            if(value==null){
            	Map<String, Object> obj;
				try {
					obj = pubProjectService.getMaxProjectCode(subProjectCode);
					if(obj==null){
	                    value="00000";
	                }else{
	                	String projectCode = obj.get("projectCode").toString();
	                    value=projectCode.substring(projectCode.length()-5);
	                }
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            int code=Integer.parseInt(value);
            code++;
            value=fillZero(code, 5);
            PROJECT_CODE_MAPPER.put(subProjectCode, value);
            return subProjectCode+value;
        }
        private static String fillZero(int code,int length){
            String codeStr=String.valueOf(code);
            if(codeStr.length()<length){
                String zeros=zeroStr(length-codeStr.length());
                codeStr=zeros+codeStr;
            }
            return codeStr;
        }
        private static String zeroStr(int length){
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<length;i++){
                sb.append("0");
            }
            return sb.toString();
        }
    }
	
    /**
     * 生成项目编号
     * 规则：机构区号 + 机构序号 + 5位自增长数字
     * @param dataSource
     * @return
     * @throws ServiceException 
     */
    @Override
    public  String generateName(Map<String,Object> data) throws ServiceException {
    	PreliminaryEvaluationDataBean preliminaryEvaluationDataBean = (PreliminaryEvaluationDataBean) data.get("preliminaryEvaluationDataBean");
        //项目编号生成规则:机构区号 + 机构序号 + 5位自增长数字
        StringBuilder projectCode = new StringBuilder();
        SysOfficeService sysOfficeService = (SysOfficeService)data.get("sysOfficeService");
        SysOffice sysOffice = sysOfficeService.getOfficeById(Long.valueOf(preliminaryEvaluationDataBean.getOfficeId()));
        //拼接区号
        if(sysOffice.getArea().length() == 3){
        	projectCode.append("0"+sysOffice.getArea());
        }else{
        	projectCode.append(sysOffice.getArea());
        }
        //拼接机构序号
        projectCode.append(sysOffice.getOfficeNumber());
        PubProjectService pubProjectService = (PubProjectService)data.get("pubProjectService");
        return ProjectNOGeneratorMapper.createProjectNO(projectCode.toString(),pubProjectService);
    }
}
