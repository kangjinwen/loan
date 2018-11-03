package com.company.modules.system.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import com.company.modules.system.domain.HousPropertyAssessment;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmailUtils {

    private static String account = EmailConfig.ACCOUNT;// 登录用户名
    private static String pass = EmailConfig.PASS; // 登录密码
    private static String from = EmailConfig.FROM; // 发件地址
    private static String host = EmailConfig.HOST; // 服务器地址
    private static String port = EmailConfig.PORT; // 端口
    private static String protocol = EmailConfig.PROTOCOL; // 协议

    private String to; // 收件人
    private String prefix; // 收件人主题前缀
    HousPropertyAssessment housPropertyAssessment;

    public SendEmailUtils(String to, HousPropertyAssessment housPropertyAssessment, String prefix) {
        this.to = to;
        this.housPropertyAssessment = housPropertyAssessment;
        this.prefix = prefix;
    }

    public void send() {
        Properties prop = new Properties();
        Date data = new Date();
        String customerName = null;// 有无房产
        String houseName = null;// 申请人姓名
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");// 格式化日期格式
        String time = format1.format(data.getTime());
        // System.out.println("格式化结果1：" + time);
        // 协议
        prop.setProperty("mail.transport.protocol", protocol);
        // 服务器
        prop.setProperty("mail.smtp.host", host);
        // 端口
        prop.setProperty("mail.smtp.port", port);
        // 使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true"); // 使用SSL，企业邮箱必需！
        // 开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        Session session = Session.getInstance(prop, new MyAuthenricator(account, pass));
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            if (JudgeValue.isNullOfString(housPropertyAssessment.getCustomerName())) {

            } else {
                customerName = housPropertyAssessment.getCustomerName();
            }
            if (JudgeValue.isNullOfString(housPropertyAssessment.getHouseName())){
            } else {
                houseName = housPropertyAssessment.getHouseName();
            }
            // 设置邮件标题
            if (prefix != null && prefix.length() != 0) {
                mimeMessage.setSubject(prefix + "房产价值评估申请新增");
            } else {
                mimeMessage.setSubject("房产价值评估申请新增");
            }
            mimeMessage.setFrom(new InternetAddress(from, "op.alert@goodzoomtech.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            mimeMessage.setSentDate(data);
            mimeMessage.setText( "\n\n客户姓名：" + customerName
                    + "\n房产名称：" + houseName  + "\n申请时间：" + time+ "\n\n请进行相应的业务处理。" + "\n本邮件由系统自动发出，请勿回复。");

            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}