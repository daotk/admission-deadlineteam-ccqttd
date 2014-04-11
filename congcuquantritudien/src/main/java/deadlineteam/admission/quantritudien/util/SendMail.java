/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlineteam.admission.quantritudien.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author quanghv
 */
public class SendMail {

    private String to;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private String subject;
    private String text;

    //phần khởi tạo, chưa các thông tin cần thiết trong code
    public SendMail(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public void send() {
        String host = "smtp.gmail.com"; //mặc định
        String userid = "csk16tdeadlineteam@gmail.com";
        String password = "vanlang12345"; // mat khau cua gmail của bạn
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.setProperty("mail.transport.protocol", "smtps");
            props.put("mail.smtp.user", userid);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "465");
            props.put("mail.smtps.auth", "true");
            Session session = Session.getDefaultInstance(props, null);

            MimeMessage message = new MimeMessage(session);

            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;

            try {
                fromAddress = new InternetAddress(userid);
                toAddress = new InternetAddress(to);
            } catch (AddressException ex) {
                Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
            }
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject,"UTF-8");
            message.setContent(text, "text/html; charset=UTF-8");

            javax.mail.Transport transport = session.getTransport("smtps");
            transport.connect(host, userid, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (javax.mail.MessagingException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
 