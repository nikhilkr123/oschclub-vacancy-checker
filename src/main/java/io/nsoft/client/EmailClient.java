package io.nsoft.client;

import io.nsoft.config.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailClient {

    private static final Logger log = LoggerFactory.getLogger(EmailClient.class);

    @Autowired
    private ConfigurationManager configurationManager;

    public void sendEmail(List<String>dates){
        Properties props = new Properties();
        props.put("mail.smtp.host", configurationManager.getSmtpHost());
        props.put("mail.smtp.port", configurationManager.getSmtpPort());
        props.put("mail.smtp.starttls.enable",configurationManager.isSmtpStarttlsEnable());
        props.put("mail.smtp.auth", configurationManager.isSmptAuth());

        try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configurationManager.getSenderEmailAddress(),
                            configurationManager.getSenderEmailPassword());
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(configurationManager.getSenderEmailAddress(), false));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(configurationManager.getNotificationEmailAddress()));

            if(dates.size() > 0)
                msg.setSubject(dates.size() + " Vacancies available");
            else
                msg.setSubject("No Vacancies available");

            msg.setContent(dates.toString(), "text/html");
            msg.setSentDate(new Date());

            log.info("Sending email");
            Transport.send(msg);
            log.info("Email send successfully");
        }
        catch (Exception ex){
            log.error("Error while sending email : {}", ex.getMessage(), ex);
        }
    }
}
