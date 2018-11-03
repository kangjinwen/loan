package com.company.modules.system.service;
import com.company.modules.system.domain.HousPropertyAssessment;

public interface MailService {
    void SendMailForAdd(HousPropertyAssessment housPropertyAssessment) throws Exception;
}
