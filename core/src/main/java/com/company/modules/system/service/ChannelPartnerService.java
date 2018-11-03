package com.company.modules.system.service;

import java.util.List;
import java.util.Map;

import com.company.modules.system.domain.ChannelPartner;

public interface ChannelPartnerService {

	public long insert(ChannelPartner channelPartner) throws Exception;

	public boolean update(ChannelPartner channelPartner) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public ChannelPartner queryInfoByPartnerName(String partnerName) throws Exception;

	public ChannelPartner getInfoByID(long id) throws Exception;

	public List<ChannelPartner> getPartnerList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> getPubPartnerlistForUser(Map<String, Object> map) throws Exception;

	public Map<String, Object> getLogoInfoByName(String partnerName) throws Exception;

	public String getUploadPath();

	public String getUploadFileURL();

	public String getLogoPath();

	public String getLogoFileURL();

	public String getDocPath();

	public String getDocFileURL();

	public String getTemplateFilePath();

	public String getTemplateFileName();

}
