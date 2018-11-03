package com.company.common.utils.upload.packageUpload;

import java.io.File;
import java.util.*;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PackTemplateCfgReader {

    private static String rootFilePath;

    private static Map<String, String> templatePlans;

    private static void readTemplateCfg(String filePath){

        String planName = "";
        String planXMLPath = "";
        templatePlans.clear();
        File file = new File(filePath);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Iterator rootIterator = root.elementIterator();

            while(rootIterator.hasNext()){
                Element planElement = (Element) rootIterator.next();
                //遍历plan的属性
                List<Attribute> planAttri = planElement.attributes();
                for(Attribute attribute : planAttri){
                    if(attribute.getName().equals("name")){
                        planName = attribute.getValue();
                    }else if(attribute.getName().equals("path")) {
                        planXMLPath = attribute.getValue();
                    }
                    if ((planName != null) && (planXMLPath != null)) {
                        templatePlans.put(planName, planXMLPath);}
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, Object>> getPlanByCfg(String filePath){

        String groupName = "";
        String nodeName = "";
        String nodeKey = "";

        File file = new File(filePath);
        Map<String, String> nodeData =  new HashMap<String,String>();
        Map<String, Object> groupData = new HashMap<String, Object>();
        List<Map<String, Object>> data = new ArrayList<>();

        SAXReader reader = new SAXReader();
        List<String> planList = new ArrayList<String>();
        try {
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Iterator rootIterator = root.elementIterator();

            while(rootIterator.hasNext()){

                Element groupElement = (Element) rootIterator.next();
                //遍历plan的属性
                data.clear();
                List<Attribute> groupAttri = groupElement.attributes();
                for(Attribute attribute : groupAttri){
                    if(attribute.getName().equals("name")){
                        groupName = attribute.getValue();
                    }
                }

                nodeData.clear();
                Iterator groupIterator = groupElement.elementIterator();
                while(groupIterator.hasNext()){
                    Element nodeElement = (Element) groupIterator.next();
                    List<Attribute> nodeAttri = nodeElement.attributes();
                    for(Attribute attribute : nodeAttri){
                        if(attribute.getName().equals("name")){
                            nodeName = attribute.getValue();
                        } else if (attribute.getName().equals("id")){
                            nodeKey = attribute.getValue();
                        }
                    }
                    nodeData.put(nodeName, nodeKey);
                }
                groupData.put(groupName, nodeData);
                data.add(groupData);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String> getTemplateList(String XMLPath, String fileName) throws Exception {

        List<String> list = new ArrayList<>();
        rootFilePath = XMLPath;
        StringBuilder stringBuilder = new StringBuilder().append(XMLPath).append(fileName);
        String filePath = stringBuilder.toString();
        if (templatePlans == null) {
            templatePlans = new HashMap<String, String>();

        }
        if (templatePlans.size() == 0) {
            readTemplateCfg(filePath);
        }

        Iterator iterator = templatePlans.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            list.add(name);
        }
        return list;
    }

    public static List<Map<String, Object>> getPackagePlan(String templateName) throws Exception {

        String fileName = "";
        Iterator iterator = templatePlans.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String name = (String) entry.getKey();
            if (name.equals(templateName)) {
                fileName = (String) entry.getValue();
            }
        }
        StringBuilder stringBuilder = new StringBuilder().append(rootFilePath).append(fileName);
        String filePath = stringBuilder.toString();

        return getPlanByCfg(filePath);
    }

}
