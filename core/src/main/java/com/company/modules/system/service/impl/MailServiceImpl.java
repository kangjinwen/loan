package com.company.modules.system.service.impl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.company.modules.system.domain.HousPropertyAssessment;
import com.company.modules.system.service.MailService;
import com.company.modules.system.utils.SendEmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    // 邮件接收地址
    @Value("${mail_address}")
    private String receiver;
    // 邮件接收主题的前缀
    @Value("${mail_title_prefix}")
    private String prefix;

    @Override
    public void SendMailForAdd(HousPropertyAssessment housPropertyAssessment) throws Exception {
        Logger log = Logger.getLogger("sendMailLog");
        try {
            // 发送邮件
            SendEmailUtils s = new SendEmailUtils(receiver, housPropertyAssessment, prefix);
            s.send();
            log.info("房产价值评估申请邮件发送成功。");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("邮件发送失败。");
        }
    }
}
