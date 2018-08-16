package com.company.modules.system.service;

import java.util.Map;
import java.util.List;
import com.company.modules.system.domain.ChannelPartner;

public interface ChannelPartnerService {

    public long insert(ChannelPartner channelPartner) throws Exception;

    public boolean update(ChannelPartner channelPartner) throws Exception;

    public boolean deleteById(long id) throws Exception;

    public ChannelPartner queryInfoByPartnerName(String partnerName) throws Exception;

    public ChannelPartner getInfoByID(long id) throws Exception;

    public List<ChannelPartner> getPartnerList() throws Exception;

    public Map<String, Object> getLogoInfoByName(String partnerName) throws Exception;
}
