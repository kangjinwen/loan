package com.company.modules.system.dao;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.system.domain.ChannelPartner;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

@RDBatisDao
public interface ChannelPartnerDao extends BaseDao<T,Long> {

    public long insert(ChannelPartner channelPartner);

    public boolean update(ChannelPartner channelPartner);

    public boolean deleteById(long Id);

    public ChannelPartner queryInfoByPartnerName(String PartnerName);

    public ChannelPartner getInfoByID(long Id);

    public List<ChannelPartner> getPartnerList();

    public Map<String, Object> getLogoInfoByName(String PartnerName);
}
