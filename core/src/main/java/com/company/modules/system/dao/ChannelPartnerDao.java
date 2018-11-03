package com.company.modules.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.system.domain.ChannelPartner;

@RDBatisDao
public interface ChannelPartnerDao extends BaseDao<T, Long> {

	public long insert(ChannelPartner channelPartner);

	public boolean update(ChannelPartner channelPartner);

	public boolean deleteById(long Id);

	public ChannelPartner queryInfoByPartnerName(String PartnerName);

	public ChannelPartner getInfoByID(long Id);

	public List<ChannelPartner> getPartnerList(Map<String, Object> map);

	public List<Map<String, Object>> getPubPartnerlistForUser(Map<String, Object> map);

	public Map<String, Object> getLogoInfoByName(String PartnerName);
}
