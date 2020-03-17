package com.journeyplanner.mail.domain.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Profile("email")
class SenderConfig {

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.auth.enable}")
    private Boolean authEnable;

    @Value("${mail.starttls.enable}")
    private Boolean starttlsEnable;

    @Value("${mail.debug.enable}")
    private Boolean debugEnable;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        if (authEnable) {
            mailSender.setUsername(username);
            mailSender.setPassword(password);
        }
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", authEnable);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.debug", debugEnable);
        return mailSender;
    }
}
