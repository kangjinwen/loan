package com.company.common.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.common.utils.JsonUtil;
import com.company.common.utils.ListUtil;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.utils.QueryPameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

/**
 * Created by Administrator on 2014/10/23.
 */
public abstract class AbsActionWrapper {
    private static Logger logger= LoggerFactory.getLogger(AbsActionWrapper.class);
    protected Map<String,Object> params=new HashMap<String, Object>();
    protected boolean writeJson=true;

    protected String dataRoot;
    protected boolean debug=false;

    protected AbsActionWrapper(HttpServletResponse response)  {
        this(null,response);
    }
    protected AbsActionWrapper(HttpServletRequest request, HttpServletResponse response)  {
        if(request!=null){
            this.params=QueryPameterUtils.getParametersMap(request, "where");
            findPageInfo(request);
            if("true".equalsIgnoreCase(request.getParameter("debug"))){
                debug=true;
            }
        }
        Object actionResult=null;
        JSONObject o=new JSONObject();
        try {
            String errMsg=validate();
            if(errMsg!=null){
                JSONObject result=new JSONObject();
                result.put("success",false);
                result.put("msg",errMsg);
                actionResult=result;
            }else{
                actionResult=doAction();
                if(actionResult==null){
                    o.put("success",true);
                    o.put("msg","操作成功");
                    actionResult=o;
                }else if(!isEmpty(dataRoot)){
                    o.put("success",true);
                    o.put(dataRoot,actionResult);
                    actionResult=o;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{}",e);
            o.put("success",false);
            try {
                String errorMsg=getErrorMsg();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

//            o.put("msg","操作失败,失败原因:"+(errorMsg==null?e.getMessage():errorMsg));
            o.put("msg",!debug?"系统异常，请稍后再试，或联系系统管理员":e.getMessage());
            actionResult=o;
        }finally {
            /*try {
                ServletUtils.writeToResponse(response, actionResult);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            if(writeJson)JsonUtil.writeJson(actionResult, response);
        }

    }

    public String validate() throws PersistentDataException {
        return null;
    }

    protected String getErrorMsg(){
        return null;
    }

    public abstract Object doAction() throws Exception;

    protected boolean findPageInfo(HttpServletRequest request){
        if(isNumber(request.getParameter("pageNum")) && isNumber(request.getParameter("pageSize")) ){
            Integer pageNum=Integer.valueOf(request.getParameter("pageNum"));
            Integer pageSize=Integer.valueOf(request.getParameter("pageSize"));
            params.put("start",(pageNum-1)*pageSize);
            params.put("length",pageSize);
            return true;
        }
        return false;
    }

    public List<String> getWhere(){
        List<String> where;
        if(params==null){
            where=new ArrayList<String>();
        }else{
            where=(List<String>) params.get("where");
            if(where==null)where=new ArrayList<String>();
        }
        return where;
    }

    public boolean whereContains(String key){
        boolean exists=false;
        for (String value : getWhere()) {
            if(value.contains(key))return true;
        }
        return exists;
    }
    public List<String> getSort(){
        List<String> list= (List<String>) params.get("sort");
        if(list==null){
            list=new ArrayList<String>();
            params.put("sort",list);
        }
        return list;
    }

    public Map<String,Object> parseJson(String json){
        Map rec= JSON.parseObject(json, Map.class);
        QueryPameterUtils.convertEmptyStr2Null(rec);
        return rec;
    }

    public static boolean isEmpty(Object value){
        return value==null||"".equals(value);
    }
    public boolean isNumber(Object value){
        if(value==null)return false;
        return value.toString().matches("\\d+(?:\\.\\d+)?");
    }

    public static List<Map<String,Object>> forDb(List<Map<String,Object>> recs) {
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        for (Map<String, Object> rec : recs) {
            list.add(forDb(rec));
        }
        return list;
    }
    public static Map<String,Object> forDb(Map<String,Object> rec) {
        Map<String,Object> newRec=new LinkedHashMap<String, Object>();
        for (String key : rec.keySet()) {
            Object value=rec.get(key);
            newRec.put(key.replaceAll("(?=[A-Z])","_"),value);
        }
        return newRec;
    }

    public static List<Map<String,Object>> fromDb(List<Map<String,Object>> list){
        List<Map<String,Object>> rlist=new ArrayList<Map<String, Object>>();
        for (Map<String, Object> rec : list) {
            rlist.add(fromDb(rec));
        }
        return rlist;
    }

    public static Map<String,Object> fromDb(Map<String,Object> rec){
        Map<String,Object> nRec=new LinkedHashMap<String, Object>();
        for (String key : rec.keySet()) {
            nRec.put(word2Hump(key),rec.get(key));
        }
        return nRec;
    }

    private static String word2Hump(String str){
        StringBuilder sb=new StringBuilder(str);
        int pos=-1,from=0;
        while((pos=sb.indexOf("_",from))!=-1){
            String value=sb.substring(pos+1,pos+2);
            sb.replace(pos,pos+2,value.toUpperCase());
            from=pos+1;
        }
        return sb.toString();
    }

    protected static String validateEmptyFields(Map<String,Object> rec,String[][] fields){
        List<String> elist=new ArrayList<String>();
        for (String[] field : fields) {
            String key=field[0];
            String msg=field[1];
            if(isEmpty(rec.get(key))){
                elist.add(msg);
            }
        }

        return elist.size()==0?null:"[<span style='color:red'>"+ListUtil.join(elist,"、")+"</span>]不能为空";
    }

}
