package io.nsoft.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Data
public class ConfigurationManager {

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private int smtpPort;

    @Value("${mail.smtp.starttls.enable}")
    private boolean smtpStarttlsEnable;

    @Value("${mail.smtp.auth}")
    private boolean smptAuth;

    @Value("${notification.email.address}")
    private String notificationEmailAddress;

    @Value("${sender.email.address}")
    private String senderEmailAddress;

    @Value("${sender.email.password}")
    private String senderEmailPassword;

    @Value("${kidsoft.login.username}")
    private String kidsoftUserName;

    @Value("${kidsoft.login.password}")
    private String kidsoftPassword;

    @Value("${kidsoft.child.id}")
    private String kidsoftChildId;

    @Value("${kidsoft.apikey}")
    private String kidsoftApiKey;

    @Value("${notify.always}")
    private boolean notifyAlways;
}
