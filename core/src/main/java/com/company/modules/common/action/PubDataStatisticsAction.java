package com.company.modules.common.action;

import com.company.common.context.Constant;
import com.company.common.utils.ServletUtils;

import com.company.modules.common.service.ComboDataSourceService;
import com.company.modules.common.service.DataStatisticsService;

import com.company.common.web.action.BaseAction;
import com.company.modules.system.domain.SysOffice;
import com.company.modules.system.domain.SysUser;
import com.company.modules.system.service.SysOfficeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/modules/common/PubDataStatisticsAction")
public class PubDataStatisticsAction extends BaseAction {

    @Autowired
    private DataStatisticsService dataStatisticsService;
    @Autowired
    private SysOfficeService sysOfficeService;
    @Autowired
    private ComboDataSourceService comboDataSourceService;

    private List<String> getChildIds(String officeId, List<SysOffice> list) throws Exception {

        List<String> ids = new ArrayList<>();
        for (SysOffice rec: list) {
            if (rec.getParentId().equals(officeId)) {
                ids.add(rec.getId());
            }
        }
        return ids;
    }

    private List<String> getOfficeIds(String officeId) throws Exception {

        List<String> ids = new ArrayList<>();
        List<String> tempIds = new ArrayList<>();
        List<String> currIds = new ArrayList<>();
        List<String> queryIds = new ArrayList<>();
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("parentId", 0);
        List<SysOffice> list = sysOfficeService.getListByMap(temp);
        ids.add(officeId);
        currIds.add(officeId);
        while (currIds.size() != 0 ) {
            queryIds.clear();
            for (String id: currIds) {
                tempIds = getChildIds(id, list);
                queryIds.addAll(tempIds);
                tempIds.clear();
            }
            currIds.clear();
            currIds.addAll(queryIds);
            ids.addAll(queryIds);
        }
        return ids;
    }


    @RequestMapping(value = "/queryDataStatistics.htm")
    public void queryDataStatistics(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "officeId") String officeId,
        @RequestParam(value = "productId") String productId,
        @RequestParam(value = "year") Integer year,
        @RequestParam(value = "containDisable") Boolean containDisable) throws Exception {

        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map<String,Object>>data = new ArrayList<>();

        List<String> coveredOffices = getOfficeIds(officeId);
        if (coveredOffices.size() == 1) {
            param.put("officeId", coveredOffices.get(0));
        } else {
            param.put("coveredOffices", coveredOffices);
        }
        param.put("year", year);
        /**判断用户所有的产品 如果不传产品id则默认取用户所拥有的所有产品 冠润用户的partnerid为null 全部产品 **/
        if ((productId == null) || (productId.length() == 0)) {
            SysUser sysUser = this.getLoginUser(request);
            if (sysUser.getChannelPartnerId() != null) {
                if (!containDisable) {
                    param.put("isOpen", 1);
                }
                Map<String, Object> params = new HashMap<>();
                params.put("channelPartnerId", sysUser.getChannelPartnerId());
                List<Map<String, Object>> list = comboDataSourceService.getAllProductSimpleInfos(params);
                if (list != null) {
                    if (list.size() == 1) {
                        param.put("productId", list.get(0).get("value").toString());
                    } else {
                        List<String> productIds = new ArrayList<>();
                        for (Map<String, Object> rec : list) {
                            productIds.add(rec.get("value").toString());
                        }
                        param.put("productIds", productIds);
                    }
                }
            }
        } else {
            if (!productId.equals("all")) {
                param.put("productId", productId);
            } else if ((productId.equals("all")) && (!containDisable)) {
                param.put("isOpen", 1);
            }
        }
        List<Map<String,Object>>loanInfo = dataStatisticsService.queryBorrowInfo(param);
        param.put("isPass", true);
        List<Map<String,Object>>loanPassInfo = dataStatisticsService.queryBorrowInfo(param);

        for (int i=1; i<13; i++) {
            Map<String, Object> monthData = new HashMap<String, Object>();
            String month = String.valueOf(i);
            StringBuilder stringBuilder = new StringBuilder().append(month).append("月");
            monthData.put("month", stringBuilder.toString());
            for (Map<String,Object> rec: loanInfo) {
                if (month.equals(rec.get("month").toString())) {
                    monthData.put("loanApplyCount", rec.get("count").toString());
                    monthData.put("loanApplyAccount", rec.get("account").toString());
                }
            }
            for (Map<String,Object> rec: loanPassInfo) {
                if (month.equals(rec.get("month").toString())) {
                    monthData.put("loanPassCount", rec.get("count").toString());
                    monthData.put("loanPassAccount", rec.get("account").toString());
                }
            }
            data.add(monthData);
        }
        if (data != null) {
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
            res.put(Constant.RESPONSE_DATA, data);
            ServletUtils.writeToResponse(response, res);
        } else {
            res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "获取模板列表失败");
            ServletUtils.writeToResponse(response, res);
        }
    }

    @RequestMapping(value = "/queryHouseAssessmentStatistics.htm")
    public void queryHouseAssessmentStatistics(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "channelId") String channelId,
                                    @RequestParam(value = "year") Integer year) throws Exception {

        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        List<Map<String,Object>>data = new ArrayList<>();

        if ((channelId != null) && (channelId.length() != 0) && (!channelId.equals("0"))) {
            param.put("channelId", channelId);
        }
        param.put("year", year);
        List<Map<String,Object>>applyInfo = dataStatisticsService.queryHouseAssessmentInfo(param);
        param.put("isComplete", true);
        List<Map<String,Object>>completeInfo = dataStatisticsService.queryHouseAssessmentInfo(param);

        for (int i=1; i<13; i++) {
            Map<String, Object> monthData = new HashMap<String, Object>();
            String month = String.valueOf(i);
            StringBuilder stringBuilder = new StringBuilder().append(month).append("月");
            monthData.put("month", stringBuilder.toString());
            for (Map<String,Object> rec: applyInfo) {
                if (month.equals(rec.get("month").toString())) {
                    monthData.put("applyCount", rec.get("count").toString());
                }
            }
            for (Map<String,Object> rec: completeInfo) {
                if (month.equals(rec.get("month").toString())) {
                    monthData.put("completeCount", rec.get("count").toString());
                }
            }
            data.add(monthData);
        }
        if (data != null) {
            res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
            res.put(Constant.RESPONSE_DATA, data);
            ServletUtils.writeToResponse(response, res);
        } else {
            res.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            res.put(Constant.RESPONSE_CODE_MSG, "获取模板列表失败");
            ServletUtils.writeToResponse(response, res);
        }
    }

}
